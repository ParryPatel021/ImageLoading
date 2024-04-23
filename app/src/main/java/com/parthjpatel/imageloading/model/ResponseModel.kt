package com.parthjpatel.imageloading.model


import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("backupDetails")
    var backupDetails: BackupDetailsModel? = BackupDetailsModel(),
    @SerializedName("coverageURL")
    var coverageURL: String? = "",
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("language")
    var language: String? = "",
    @SerializedName("mediaType")
    var mediaType: Int? = 0,
    @SerializedName("publishedAt")
    var publishedAt: String? = "",
    @SerializedName("publishedBy")
    var publishedBy: String? = "",
    @SerializedName("thumbnail")
    var thumbnail: ThumbnailModel? = ThumbnailModel(),
    @SerializedName("title")
    var title: String? = ""
) {
    data class BackupDetailsModel(
        @SerializedName("pdfLink")
        var pdfLink: String? = "",
        @SerializedName("screenshotURL")
        var screenshotURL: String? = ""
    )

    data class ThumbnailModel(
        @SerializedName("aspectRatio")
        var aspectRatio: Int? = 0,
        @SerializedName("basePath")
        var basePath: String? = "",
        @SerializedName("domain")
        var domain: String? = "",
        @SerializedName("id")
        var id: String? = "",
        @SerializedName("key")
        var key: String? = "",
        @SerializedName("qualities")
        var qualities: List<Int?>? = listOf(),
        @SerializedName("version")
        var version: Int? = 0
    )
}