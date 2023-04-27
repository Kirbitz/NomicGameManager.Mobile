package nomic.data.models

import java.time.LocalDate

/**
 * Data class for game
 *
 * @property gameId the unique id of the game
 * @property title the display title of a game
 * @property createDate the date when the game was created
 * @property currentPlayer the indicator of who's turn it is
 * @property userId the user the game is attached to
 */
data class GameDTO(
    val gameId: Int? = null,
    val title: String,
    val createDate: LocalDate? = null,
    val currentPlayer: Int? = null,
    val userId: Int
)
