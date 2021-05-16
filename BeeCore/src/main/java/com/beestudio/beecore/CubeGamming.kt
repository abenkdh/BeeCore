package com.beestudio.beecore


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CubeGamming(
    @Json(name = "amount")
    var amount: String?,
    @Json(name = "buyer")
    var buyer: String?,
    @Json(name = "item")
    var item: Item?,
    @Json(name = "license")
    var license: String?,
    @Json(name = "purchase_count")
    var purchaseCount: Int?,
    @Json(name = "sold_at")
    var soldAt: String?,
    @Json(name = "support_amount")
    var supportAmount: String?,
    @Json(name = "supported_until")
    var supportedUntil: Any?
) {
    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "attributes")
        var attributes: List<Attribute?>?,
        @Json(name = "author_image")
        var authorImage: String?,
        @Json(name = "author_url")
        var authorUrl: String?,
        @Json(name = "author_username")
        var authorUsername: String?,
        @Json(name = "classification")
        var classification: String?,
        @Json(name = "classification_url")
        var classificationUrl: String?,
        @Json(name = "description")
        var description: String?,
        @Json(name = "id")
        var id: Int?,
        @Json(name = "name")
        var name: String?,
        @Json(name = "number_of_sales")
        var numberOfSales: Int?,
        @Json(name = "previews")
        var previews: Previews?,
        @Json(name = "price_cents")
        var priceCents: Int?,
        @Json(name = "published_at")
        var publishedAt: String?,
        @Json(name = "rating")
        var rating: Double?,
        @Json(name = "rating_count")
        var ratingCount: Int?,
        @Json(name = "site")
        var site: String?,
        @Json(name = "summary")
        var summary: String?,
        @Json(name = "tags")
        var tags: List<String?>?,
        @Json(name = "trending")
        var trending: Boolean?,
        @Json(name = "updated_at")
        var updatedAt: String?,
        @Json(name = "url")
        var url: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Attribute(
            @Json(name = "label")
            var label: String?,
            @Json(name = "name")
            var name: String?,
            @Json(name = "value")
            var value: Any?
        )

        @JsonClass(generateAdapter = true)
        data class Previews(
            @Json(name = "icon_preview")
            var iconPreview: IconPreview?,
            @Json(name = "icon_with_landscape_preview")
            var iconWithLandscapePreview: IconWithLandscapePreview?,
            @Json(name = "landscape_preview")
            var landscapePreview: LandscapePreview?,
            @Json(name = "live_site")
            var liveSite: LiveSite?
        ) {
            @JsonClass(generateAdapter = true)
            data class IconPreview(
                @Json(name = "icon_url")
                var iconUrl: String?,
                @Json(name = "type")
                var type: String?
            )

            @JsonClass(generateAdapter = true)
            data class IconWithLandscapePreview(
                @Json(name = "icon_url")
                var iconUrl: String?,
                @Json(name = "landscape_url")
                var landscapeUrl: String?,
                @Json(name = "type")
                var type: String?
            )

            @JsonClass(generateAdapter = true)
            data class LandscapePreview(
                @Json(name = "landscape_url")
                var landscapeUrl: String?,
                @Json(name = "type")
                var type: String?
            )

            @JsonClass(generateAdapter = true)
            data class LiveSite(
                @Json(name = "href")
                var href: String?,
                @Json(name = "type")
                var type: String?
            )
        }
    }
}