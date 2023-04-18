package nomic.data.models

import java.time.LocalDate

data class GameDTO (
    val gameId: Int,
    val title: String,
    val createDate: LocalDate,
    val currentPlayer: Int,
    val userId: Int
        )

