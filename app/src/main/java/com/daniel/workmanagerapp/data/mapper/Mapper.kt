package com.daniel.workmanagerapp.data.mapper

import com.daniel.workmanagerapp.data.model.Headline
import com.daniel.workmanagerapp.data.model.HeadlineSource
import com.daniel.workmanagerapp.data.model.NewsHeadlinesResponse
import com.daniel.workmanagerapp.domain.entity.Article
import com.daniel.workmanagerapp.domain.entity.ArticleItems
import com.daniel.workmanagerapp.domain.entity.Source

fun toArticleItems(headlinesResponse: NewsHeadlinesResponse) = ArticleItems(
    status = headlinesResponse.status,
    totalResults = headlinesResponse.totalResults,
    headlines = toArticles(headlinesResponse.headlines)
)

private fun toArticles(headlineItems: List<Headline>) = headlineItems.map { headlineItem ->
    Article(
        source = toSource(headlineItem.source),
        author = headlineItem.author ?: "N/A",
        title = headlineItem.title,
        description = headlineItem.description ?: "N/A",
        url = headlineItem.url,
        urlToImage = headlineItem.urlToImage ?: "",
        publishedAt = headlineItem.publishedAt,
        content = headlineItem.content ?: ""
    )
}

private fun toSource(source: HeadlineSource) = Source(id = source.id ?: "0", name = source.name ?: "N/A")

