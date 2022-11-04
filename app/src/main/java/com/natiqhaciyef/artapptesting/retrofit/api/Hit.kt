package com.natiqhaciyef.artapptesting.retrofit.api

import com.google.gson.annotations.SerializedName

data class Hit(
    var id: Int,
    var comments: Int,
    var downloads: Int,
    var favorites: Int,
    var imageHeight: Int,
    var imageSize: Int,
    var imageWidth: Int,
    var largeImageURL: String,
    var likes: Int,
    var pageURL: String,
    var previewHeight: Int,
    var previewURL: String,
    var previewWidth: Int,
    var userImageURL: String,
    var views: Int,
    var webformatHeight: Int,
    var webformatURL: String,
    var webformatWidth: Int,
    @SerializedName("user_id")
    var userId: Int,
    var tags: String,
    var type: String,
    var user: String
)
