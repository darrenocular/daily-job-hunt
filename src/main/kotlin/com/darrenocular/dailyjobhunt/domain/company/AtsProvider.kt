package com.darrenocular.dailyjobhunt.domain.company

enum class AtsProvider {
    GREENHOUSE,
    LEVER,
    ASHBY;

    companion object {
        fun fromString(value: String): AtsProvider =
            entries.firstOrNull { it.name.equals(value.trim(), ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown ATS Provider: $value. Supported: ${entries.joinToString { it.name }}")
    }
}