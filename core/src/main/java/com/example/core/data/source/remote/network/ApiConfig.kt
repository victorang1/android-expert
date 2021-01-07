package com.example.core.data.source.remote.network

import okhttp3.CertificatePinner

object ApiConfig {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_IMG_PATH = "https://image.tmdb.org/t/p/w500"

    private const val HOST_NAME = "api.themoviedb.org"
    val certificatePin = CertificatePinner.Builder()
        .add(HOST_NAME, "+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
        .add(HOST_NAME, "JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
        .add(HOST_NAME, "++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
        .add(HOST_NAME, "KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
        .build()
}