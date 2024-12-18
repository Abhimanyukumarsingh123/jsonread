package com.leelaland.pawansir

import kotlinx.serialization.Serializable

@Serializable
data class Usesr(
    val id: Int,
    val name: String,
    val email: String
)
