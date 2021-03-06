package me.mqueiroz.github.model.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class License(

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "spdx_id")
    val spdxId: String? = null,

    @Json(name = "key")
    val key: String? = null,

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "node_id")
    val nodeId: String? = null
) : Parcelable