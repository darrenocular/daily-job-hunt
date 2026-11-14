package com.darrenocular.dailyjobhunt.domain.company

data class AtsBoard(
    val provider: AtsProvider,
    val token: String,
) {
    init {
        require(token.isNotBlank()) { "AtsBoard token must not be blank" }
    }
}
