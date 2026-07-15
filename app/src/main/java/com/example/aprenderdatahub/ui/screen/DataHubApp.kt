package com.example.aprenderdatahub.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aprenderdatahub.models.Article
import com.example.aprenderdatahub.viewmodel.DataHubUiState
import com.example.aprenderdatahub.viewmodel.DataHubViewModel

@Composable
fun DataHubApp(viewModel: DataHubViewModel = viewModel()){

}





@Composable
fun ArticleDetailScreen(
    article: Article,
    modifier:Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(article.title, fontWeight = FontWeight.Bold)
        Text("Categoria: ${article.category}")
        Text("Origem: ${article.source}")
        Text(article.summary)
    }
}

@Composable
fun ArticleList(
    articles: List<Article>,
    onSelect:(Article) -> Unit,

){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(articles, key = {it.id}){article->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{(onSelect(article))}
            ){
                ListItem(
                    headlineContent = {Text(article.title)},
                    supportingContent = {Text(article.summary)},
                    overlineContent = {Text(article.category)},
                    trailingContent = {Text(article.source)}
                )
            }

        }
    }
}
@Composable
fun HubListScreen(
    state: DataHubUiState,
    onSearch: (String) -> Unit,
    onRetry: () -> Unit,
    onForceFallback: () -> Unit,
    onEmpty: () -> Unit,
    onSelect: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.query,
            onValueChange = onSearch,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Busca") },
            singleLine = true
        )

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistChip(
                onClick = {},
                label = { Text("Fonte: ${state.source.ifBlank { "aguardando" }}") }
            )
            FilledTonalButton(onClick = onForceFallback) {
                Text("Simular fallback")
            }
            FilledTonalButton(onClick = onEmpty) {
                Text("Simular vazio")
            }
        }

        Spacer(Modifier.height(12.dp))

        when {
            state.isLoading -> LoadingState()
            state.error != null -> ErrorState(message = state.error, onRetry = onRetry)
            state.articlesFiltrados.isEmpty() -> EmptyState()
            else -> ArticleList(
                articles = state.articlesFiltrados,
                onSelect = onSelect
            )
        }
    }
}


@Composable
fun LoadingState() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Carregando dados...")
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Erro ao carregar dados", fontWeight = FontWeight.Bold)
        Text(message)
        Button(onClick = onRetry) {
            Text("Tentar novamente")
        }
    }
}

@Composable
fun EmptyState() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Nenhum conteúdo encontrado", fontWeight = FontWeight.Bold)
        Text("Ajuste a busca ou tente recarregar os dados.")
    }
}
