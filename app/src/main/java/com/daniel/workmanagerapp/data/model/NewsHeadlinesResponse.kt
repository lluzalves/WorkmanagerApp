package com.daniel.workmanagerapp.data.model

import com.daniel.workmanagerapp.data.model.Headline
import com.google.gson.annotations.SerializedName


data class NewsHeadlinesResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    var totalResults: Int,

    @SerializedName("articles")
    var headlines: List<Headline>
)