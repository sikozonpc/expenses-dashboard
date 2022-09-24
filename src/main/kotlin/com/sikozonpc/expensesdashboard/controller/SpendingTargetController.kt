package com.sikozonpc.expensesdashboard.controller

import com.sikozonpc.expensesdashboard.dto.SpendingTargetDTO
import com.sikozonpc.expensesdashboard.service.SpendingTargetService
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Min

const val SpendingTargetControllerURL = "/api/v1/spending-targets"

@RestController
@RequestMapping(SpendingTargetControllerURL, produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class SpendingTargetController(
    val spendingTargetService: SpendingTargetService,
) {
    @GetMapping
    fun getAll(
        @RequestParam("monthDecrement", required = false, defaultValue = "0")
        @Min(0)
        monthDecrement: Int,
    ): List<SpendingTargetDTO> = spendingTargetService.calculate(monthDecrement)
}