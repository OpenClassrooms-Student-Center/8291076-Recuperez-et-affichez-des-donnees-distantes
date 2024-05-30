package com.jeremieguillot.identityreader.core.domain

data class MRZ(
    val documentNumber: String,
    val dateOfBirth: String,
    val dateOfExpiry: String
) {
    companion object {
        val EMPTY: MRZ = MRZ("", "", "")
        val FAKE: MRZ = MRZ("23FC08455", "920819", "330606")
    }
}