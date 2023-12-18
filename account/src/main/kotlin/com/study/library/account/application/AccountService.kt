package com.study.library.account.application

import com.study.library.account.domain.Account
import com.study.library.account.domain.Password
import com.study.library.account.port.`in`.AuthenticateUseCase
import com.study.library.account.port.`in`.AuthenticateUseCase.AuthenticationData
import com.study.library.account.port.`in`.SignUpUseCase
import com.study.library.account.port.`in`.SignUpUseCase.AccountData
import com.study.library.account.port.out.AccountCommandPort
import com.study.library.account.port.out.AccountQueryPort
import com.study.library.common.auth.JwtToken
import com.study.library.common.auth.JwtTokenProvider
import com.study.library.common.error.AccountNotFoundException
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountCommandPort: AccountCommandPort,
    private val accountQueryPort: AccountQueryPort,
    private val tokenProvider: JwtTokenProvider
) : SignUpUseCase, AuthenticateUseCase {

    override suspend fun signUp(accountData: AccountData): Account {
        val account = Account(
            email = accountData.email,
            password = Password(accountData.password),
            name = accountData.name
        )

        return accountCommandPort.create(account)
    }

    override suspend fun authenticate(authenticationData: AuthenticationData): JwtToken {
        val account = accountQueryPort.findByEmail(authenticationData.email)
            ?: throw AccountNotFoundException("not found account")
        account.authenticate(authenticationData.password)

        return tokenProvider.createAccessToken(authenticationData.email)
    }
}