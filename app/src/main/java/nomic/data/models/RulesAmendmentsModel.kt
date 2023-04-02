package nomic.data.models

import com.google.gson.annotations.SerializedName

/**
 * The rules and amendments object to be passed back to the caller of the endpoint
 *
 * @property ruleId the unique if for the rule
 * @property index the position of the rule within the context of a game
 * @property title the name of this rule
 * @property description the clarifying text for the rule
 * @property mutable flag for mutable/immutable rules
 * @property amendments the list of amendments that are attached to a rule
 * @see[nomic.data.models.AmendmentModel]
 */
data class RulesAmendmentsModel(
    @SerializedName("ruleId")
    val ruleId: Int,
    @SerializedName("index")
    val index: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("mutable")
    val mutable: Boolean,
    @SerializedName("amendments")
    var amendments: MutableList<AmendmentModel> = mutableListOf()
)

/**
 * The amendments object to be passed back to the caller of the endpoint
 *
 * @property amendId the primary key for an amendment
 * @property index the position of an amendment under a rule
 * @property description the context of an amendment
 * @property title what an amendment is called
 */
data class AmendmentModel(
    val amendId: Int,
    val index: Int,
    val description: String,
    val title: String
)
