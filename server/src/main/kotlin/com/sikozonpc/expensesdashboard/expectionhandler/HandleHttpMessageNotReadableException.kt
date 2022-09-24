package com.sikozonpc.expensesdashboard.expectionhandler

import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class HandleHttpMessageNotReadableException {
    companion object : KLogging()

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleException(
        exception: HttpMessageNotReadableException,
        request: HttpServletRequest?,
    ): ResponseEntity<String> {
        logger.error("HttpMessageNotReadableException observed: ${exception.message}", exception)

        return ResponseEntity(
            "Could not parse JSON input",
            HttpStatus.BAD_REQUEST
        )
    }
}