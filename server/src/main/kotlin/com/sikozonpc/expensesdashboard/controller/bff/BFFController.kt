package com.sikozonpc.expensesdashboard.controller.bff

import com.sikozonpc.expensesdashboard.dto.WebBFFDTO
import com.sikozonpc.expensesdashboard.service.BFFService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val BFFControllerURL = "/api/v1/bff"


@RestController
@RequestMapping(BFFControllerURL, produces = [MediaType.APPLICATION_JSON_VALUE])
class BFFController(
    private val webBFFService: BFFService,
) {

    @GetMapping("/web")
    fun getWeb(): WebBFFDTO = webBFFService.composeAndReturnForWeb()
}