package com.sikozonpc.expensesdashboard.controller

import com.sikozonpc.expensesdashboard.dto.TransactionDTO
import com.sikozonpc.expensesdashboard.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.Validation

const val TransactionControllerURL = "/api/v1/transactions"

@RestController
@RequestMapping(TransactionControllerURL, headers = ["Accept=text/json"])
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @RequestBody dto: TransactionDTO,
        @PathVariable("id") id: Int,
    ): TransactionDTO = transactionService.update(id, dto)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable("id") id: Int) = transactionService.delete(id)
}