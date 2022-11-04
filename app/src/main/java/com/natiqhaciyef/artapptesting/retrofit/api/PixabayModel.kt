package com.natiqhaciyef.artapptesting.retrofit.api

data class PixabayModel(
    var total: Long,
    var totalHits: Int,
    var hits: List<Hit>
)

