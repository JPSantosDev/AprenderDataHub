package com.example.aprenderdatahub.repository

import android.content.Context
import com.example.aprenderdatahub.models.Article
import com.example.aprenderdatahub.models.ArticleDto
import com.example.aprenderdatahub.models.LoadResult
import com.example.aprenderdatahub.models.toArticle
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json


class Repository(val context: Context) {


    val json = Json { ignoreUnknownKeys = true }

    val client: HttpClient = HttpClient(CIO) {

        install(ContentNegotiation) {
            json(json = json)
        }
        install(HttpRequestRetry) {
            retryIf(maxRetries = 2) { request, response ->
                !response.status.isSuccess()

            }
            exponentialDelay()
            retryOnException(maxRetries = 2)
        }

        install(HttpTimeout) {
            socketTimeoutMillis = 7000
            connectTimeoutMillis = 5000
            requestTimeoutMillis = 7000
        }
    }

         suspend fun loadArticles(forceError:Boolean = false) : LoadResult {

            try{
                if (forceError) error("Simulação de erro")
                return LoadResult(
                    articles = remoteArticles()   ,
                    source = "Remoto",
                    usedFallBack = false
                )

            } catch (e: Exception){
                return LoadResult(
                    articles = localArticles() ,
                    source = "JSON Local",
                    usedFallBack = true
                )

            }

        }

        suspend fun localArticles():List<Article> = withContext(Dispatchers.IO){
            val text = context.assets.open("datahub_local.json").bufferedReader().use { it.readText() }
            Json.decodeFromString<List<ArticleDto>>(text).map { it.toArticle("JSON Local") }

        }

         suspend fun remoteArticles(): List<Article> = withContext(Dispatchers.IO) {
            val url = "https://jsonplaceholder.typicode.com/posts"
            val dtoList: List<ArticleDto> = client.get(url).body()
            dtoList.map { it.toArticle("Remoto") }
        }


}