package me.rvbiljouw.geservice.model

import com.fasterxml.jackson.annotation.JsonProperty


/**
 * @author rvbiljouw
 */
data class GEItemSummaryWrapper(@JsonProperty("item") val item: GEItemSummary)

data class GEItemSummary(
        @JsonProperty("icon") val icon: String,
        @JsonProperty("icon_large") val iconLarge: String,
        @JsonProperty("id") val id: Short,
        @JsonProperty("type") val type: String,
        @JsonProperty("typeIcon") val typeIcon: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("description") val description: String,
        @JsonProperty("members") val members: Boolean,
        @JsonProperty("current") val current: GEItemPrice,
        @JsonProperty("today") val today: GEItemPrice,
        @JsonProperty("day30") val day30: GEItemPrice,
        @JsonProperty("day90") val day90: GEItemPrice,
        @JsonProperty("day180") val day180: GEItemPrice
)

data class GEItemPrice(
        @JsonProperty("trend") val trend: String,
        @JsonProperty("price") val formattedPrice: String?,
        @JsonProperty("change") val formattedChange: String?
) {
    val rawPrice: Int
        get() {
            val sanePrice = formattedPrice
                    ?.replace(",", "")
                    ?.replace(" ", "")
                    ?.trim()
                    ?.toLowerCase()
            val factor = when (sanePrice?.last()) {
                'b' -> 1000000000
                'm' -> 1000000
                'k' -> 1000
                else -> 1
            }

            return when (factor) {
                1 -> (sanePrice?.toDouble() ?: 0.0) * factor
                else -> (sanePrice?.dropLast(1)?.toDouble() ?: 0.0) * factor
            }.toInt()
        }
}
