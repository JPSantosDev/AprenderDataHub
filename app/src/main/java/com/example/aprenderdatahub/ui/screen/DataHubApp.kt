package com.example.aprenderdatahub.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
