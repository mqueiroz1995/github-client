package me.mqueiroz.github.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.mqueiroz.github.model.data.Repo

@JsonClass(generateAdapter = true)
data class GetReposResponse(

    @Json(name = "total_count")
    val totalCount: Int,

    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,

    @Json(name = "items")
    val items: List<Repo>
)