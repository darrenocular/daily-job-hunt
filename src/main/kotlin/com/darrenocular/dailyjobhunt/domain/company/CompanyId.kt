package com.darrenocular.dailyjobhunt.domain.company

import java.util.UUID

@JvmInline
value class CompanyId(val value: UUID) {
    companion object {
        fun generate(): CompanyId = CompanyId(UUID.randomUUID())
        fun fromString(str: String): CompanyId = CompanyId(UUID.fromString(str))
    }
}