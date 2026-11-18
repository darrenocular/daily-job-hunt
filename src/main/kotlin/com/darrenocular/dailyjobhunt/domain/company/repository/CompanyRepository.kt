package com.darrenocular.dailyjobhunt.domain.company.repository

import com.darrenocular.dailyjobhunt.domain.company.AtsBoard
import com.darrenocular.dailyjobhunt.domain.company.Company
import com.darrenocular.dailyjobhunt.domain.company.CompanyId

interface CompanyRepository {
    fun findById(id: CompanyId): Company?
    fun findByAtsBoard(atsBoard: AtsBoard): Company?
    fun findAll(): List<Company>
    fun findAllByIds(ids: List<CompanyId>): List<Company>
    fun save(company: Company): Company
    fun delete(company: Company)
}