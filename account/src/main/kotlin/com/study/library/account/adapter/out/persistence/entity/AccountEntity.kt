package com.study.library.account.adapter.out.persistence.entity

import com.study.library.account.domain.Account
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account")
class AccountEntity(
    @Id
    val id: Long = 0L,
    val email: String,
    val password: String,
    val name: String
) {
    @CreatedDate
    lateinit var createdTime: LocalDateTime

    fun toDomain(): Account {
        return Account(
            id = id,
            email = email,
            password = password,
            name = name
        )
    }

    companion object {
        fun from(account: Account): AccountEntity {
            return AccountEntity(
                id = account.id,
                email = account.email,
                password = account.password,
                name = account.name
            )
        }
    }
}
