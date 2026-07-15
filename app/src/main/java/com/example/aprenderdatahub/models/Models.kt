package com.example.aprenderdatahub.models

import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

data class Article(
    val title: String,
    val summary: String,
    val source: String,
    val id: Int,
    val category: String
)


data class LoadResult(
    val articles: List<Article>,
    val source: String,
    val usedFallBack: Boolean
)

fun ArticleDto.toArticle(source: String): Article{
    return Article(
        id = id,
        title = title.replaceFirstChar { it.uppercase() },
        summary = body.take(140),
        category = "Conteúdo Aprender+",
        source = source
    )

}