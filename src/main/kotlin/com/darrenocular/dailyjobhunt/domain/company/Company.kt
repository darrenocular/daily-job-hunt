package com.darrenocular.dailyjobhunt.domain.company

import kotlin.time.Clock
import kotlin.time.Instant

data class Company(
    val id: CompanyId,
    val name: String,
    val atsBoard: AtsBoard,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = createdAt,
) {
    init {
        require(name.trim().isNotBlank()) { "Company name must not be blank" }
    }

    companion object {
        fun create(
            name: String,
            atsBoard: AtsBoard,
            id: CompanyId = CompanyId.generate(),
        ): Company = Company(
            id = id,
            name = name.trim(),
            atsBoard = atsBoard,
        )
    }

    fun updateName(newName: String): Company {
        val trimmed = newName.trim()
        require(trimmed.isNotBlank()) { "Company name must not be blank" }
        if (this.name == trimmed) return this

        return copy(name = trimmed, updatedAt = Clock.System.now())
    }


    fun updateAtsBoard(newAtsBoard: AtsBoard): Company {
        if (this.atsBoard == newAtsBoard) return this

        return copy(atsBoard = newAtsBoard, updatedAt = Clock.System.now())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Company) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "Company(id=$id, name='$name', atsBoard=$atsBoard)"
}