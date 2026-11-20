package com.darrenocular.dailyjobhunt.domain.company

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldBeUUID
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CompanyTest {
    private val defaultAtsBoard = AtsBoard(provider = AtsProvider.GREENHOUSE, token = "test-company")

    @Nested
    inner class Creation {
        @Test
        fun `create should create a company with trimmed name and generated id`() {
            val company = Company.create(
                name = "  Test Company  ",
                atsBoard = defaultAtsBoard
            )

            company.id.value.shouldNotBeNull()
            company.id.value.toString().shouldBeUUID()
            company.name.shouldBe("Test Company")
            company.atsBoard.shouldBe(defaultAtsBoard)
        }

        @ParameterizedTest
        @ValueSource(strings = ["", "   ", "\t\n"])
        fun `create should throw IllegalArgumentException if company name is blank`(blankName: String) {
            shouldThrow<IllegalArgumentException> {
                Company.create(name = blankName, atsBoard = defaultAtsBoard)
            }
        }

        @Test
        fun `create should throw IllegalArgumentException if ATS token in AtsBoard is blank`() {
            shouldThrow<IllegalArgumentException> {
                AtsBoard(provider = AtsProvider.GREENHOUSE, token = "   ")
            }
        }
    }

    @Nested
    inner class Update {
        @Test
        fun `updateName should return a new Company without modifying the original`() {
            val original = Company.create(name = "Stripe", atsBoard = defaultAtsBoard)
            val updated = original.updateName("Stripe Inc")

            original.shouldNotBeSameInstanceAs(updated)
            original.name.shouldBe("Stripe")
            updated.name.shouldBe("Stripe Inc")
            original.id.shouldBeEqual(updated.id)
        }

        @Test
        fun `updateName should return same reference if name is identical`() {
            val original = Company.create(name = "Stripe", atsBoard = defaultAtsBoard)
            val same = original.updateName("Stripe")

            original.shouldBeSameInstanceAs(same)
        }

        @ParameterizedTest
        @ValueSource(strings = ["", "   ", "\t\n"])
        fun `updateName should throw IllegalArgumentException if newName is blank`(blankName: String) {
            val original = Company.create(name = "Stripe", atsBoard = defaultAtsBoard)
            shouldThrow<IllegalArgumentException> {
                original.updateName(blankName)
            }
        }

        @Test
        fun `updateAtsBoard should return a new Company with updated AtsBoard`() {
            val original = Company.create(name = "Canva", atsBoard = defaultAtsBoard)
            val newBoard = AtsBoard(AtsProvider.LEVER, "canva")
            val updated = original.updateAtsBoard(newBoard)

            original.shouldNotBeSameInstanceAs(updated)
            original.atsBoard.shouldBe(defaultAtsBoard)
            updated.atsBoard.shouldBe(newBoard)
        }

        @Test
        fun `updateAtsBoard should return same reference if AtsBoard is unchanged`() {
            val original = Company.create(name = "Canva", atsBoard = defaultAtsBoard)
            val same = original.updateAtsBoard(defaultAtsBoard)

            original.shouldBeSameInstanceAs(same)
        }
    }

    @Nested
    inner class Equality {
        @Test
        fun `companies with same id should be equal regardless of attributes`() {
            val id = CompanyId.generate()
            val c1 = Company.create(name = "Acme A", atsBoard = defaultAtsBoard, id = id)
            val c2 = Company.create(
                name = "Acme B",
                atsBoard = AtsBoard(AtsProvider.LEVER, "other"),
                id = id
            )

            c1.shouldBe(c2)
            c1.hashCode().shouldBe(c2.hashCode())
        }

        @Test
        fun `companies with different ids should not be equal even if attributes match`() {
            val c1 = Company.create(name = "Acme", atsBoard = defaultAtsBoard)
            val c2 = Company.create(name = "Acme", atsBoard = defaultAtsBoard)

            c1.shouldNotBe(c2)
        }
    }
}
