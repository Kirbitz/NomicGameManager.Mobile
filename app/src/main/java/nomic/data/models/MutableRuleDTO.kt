package nomic.data.models

/**
 * Object to wrap a boolean value so that sending JSON data out is easier
 *
 * @property mutableInput the boolean value to be wrapped
 */
data class MutableRuleDTO(
    val mutableInput: Boolean
)
