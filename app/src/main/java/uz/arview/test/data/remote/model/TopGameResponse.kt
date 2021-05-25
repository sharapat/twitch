package uz.arview.test.data.remote.model

import com.google.gson.annotations.SerializedName

data class TopGameResponse(
    @SerializedName("_total")
    val total: Int,
    val top: List<Top>
)