package nomic.data.models

/**
 * Object to wrap a boolean value so that sending JSON data out is easier
 *
 * @property isMutable the boolean value to be wrapped
 */
data class ModifyRuleMutabilityDTO(
    val isMutable: Boolean
)
