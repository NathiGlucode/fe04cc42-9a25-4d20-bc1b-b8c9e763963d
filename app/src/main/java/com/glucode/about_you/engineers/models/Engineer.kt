package com.glucode.about_you.engineers.models

import android.net.Uri

data class Engineer(
    val name: String,
    val role: String,
    val defaultImageName: Uri?,
    val quickStats: QuickStats,
    val questions: List<Question>,
)