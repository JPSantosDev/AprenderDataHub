package com.example.aprenderdatahub.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aprenderdatahub.models.Article
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