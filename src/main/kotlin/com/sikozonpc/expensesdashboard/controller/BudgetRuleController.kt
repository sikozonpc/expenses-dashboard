package com.sikozonpc.expensesdashboard.controller

import com.sikozonpc.expensesdashboard.dto.BudgetRuleDTO
import com.sikozonpc.expensesdashboard.service.BudgetRuleService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

const val BudgetRuleControllerURL = "/api/v1/budget-rule"

@RestController
@RequestMapping(BudgetRuleControllerURL, headers = ["Accept=text/json"])
@Validated
class BudgetRuleController(
    val budgetRuleService: BudgetRuleService,
) {
    @GetMapping
    fun getAll(): List<BudgetRuleDTO> = budgetRuleService.getAll()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun add(
        @RequestBody @Valid dto: BudgetRuleDTO,
    ): BudgetRuleDTO = budgetRuleService.add(dto)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable("id") id: Int) = budgetRuleService.deleteBydId(id)
}