package nomic.data.models

import java.time.LocalDate

data class GameDTO (
    val gameId: Int? = null,
    val title: String,
    val createDate: LocalDate? = null,
    val currentPlayer: Int? = null,
    val userId: Int
)