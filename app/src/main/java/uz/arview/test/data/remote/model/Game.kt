package uz.arview.test.data.remote.model

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("_id")
    val id: Long,
    val box: Box,
    @SerializedName("giantbomb_id")
    val giantBombId: Long,
    @SerializedName("localized_name")
    val localizedName: String,
    val locale: String,
    val logo: Logo,
    val name: String
)