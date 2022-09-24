package com.sikozonpc.expensesdashboard.controller

import com.sikozonpc.expensesdashboard.dto.TransactionDTO
import com.sikozonpc.expensesdashboard.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

const val TransactionControllerURL = "/api/v1/transactions"

@RestController
@RequestMapping(TransactionControllerURL, produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class TransactionController(
    val transactionService: TransactionService,
) {
    @GetMapping
    fun getAll(
        @RequestParam("monthDecrement", required = false, defaultValue = "")
        monthDecrement: String?,
    ): List<TransactionDTO> {
        if (monthDecrement.isNullOrEmpty()) return transactionService.getAll()
        return transactionService.getAllByMonthDecrement(monthDecrement.toInt())
    }

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