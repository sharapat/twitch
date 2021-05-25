package uz.arview.test.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.arview.test.data.remote.model.Game

@Entity(tableName = "games")
data class GameDbModel(
    @PrimaryKey
    val id: Long,
    val name: String,
    val boxLarge: String,
    val boxMedium: String,
    val boxSmall: String,
    val boxTemplate: String,
    val giantBombId: Long,
    val logoLarge: String,
    val logoMedium: String,
    val logoSmall: String,
    val logoTemplate: String,
    val localizedName: String,
    val locale: String
) {
    companion object {
        fun convertToDbModel(game: Game) : GameDbModel {
            return GameDbModel(
                id = game.id,
                name = game.name,
                boxLarge = game.box.large,
                boxMedium = game.box.medium,
                boxSmall = game.box.small,
                boxTemplate = game.box.template,
                logoLarge = game.logo.large,
                logoMedium = game.logo.medium,
                logoSmall = game.logo.small,
                logoTemplate = game.logo.template,
                giantBombId = game.giantBombId,
                localizedName = game.localizedName,
                locale = game.locale
            )
        }
    }
}