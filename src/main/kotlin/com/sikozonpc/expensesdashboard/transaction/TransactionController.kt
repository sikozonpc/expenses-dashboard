package com.sikozonpc.expensesdashboard.transaction

import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

const val TransactionControllerURL = "/transactions"

@RestController
@RequestMapping(TransactionControllerURL)
@Validated
class TransactionController(
    val transactionService: TransactionService,
) {
    @GetMapping
    fun getAll(): List<TransactionDTO> = transactionService.getAll()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun add(
        @RequestBody @Valid dto: TransactionDTO,
    ): TransactionDTO = transactionService.add(dto)
}