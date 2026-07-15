package com.example.aprenderdatahub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aprenderdatahub.models.Article
import com.example.aprenderdatahub.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class DataHubUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null,
    val source: String = "",
    val fallBack: Boolean = false,
    val query:String = ""

){

    val articlesFiltrados
        get() = if (query.isBlank()) articles
    else{
        articles.filter { it.title.contains(query,true) || it.summary.contains(query,true) }
        }
}



class DataHubViewModel(application: Application): AndroidViewModel(application){

    private val _uiState = MutableStateFlow(DataHubUiState())
    private val repository = Repository(application)
    val uiState = _uiState.asStateFlow()


    init {
        refresh()

    }

    fun refresh(forceError: Boolean = false) {
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null

                )
            }

            val result = repository.loadArticles()

            try {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        articles = result.articles,
                        fallBack = result.usedFallBack,
                        source = result.source

                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }

        }
    }

    fun search(text:String){
        _uiState.update {
            it.copy(
                query = text
            )
        }
    }




}