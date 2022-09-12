package com.sikozonpc.expensesdashboard.expectionhandler

import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class GlobalErrorHandler : ResponseEntityExceptionHandler() {
    companion object : KLogging()

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        logger.error("MethodArgumentNotValidException observed: ${ex.message}", ex)

        val errors = ex.bindingResult.allErrors
            .map { it.defaultMessage!! } // Just take the default message without the other noise
            .sorted()

        logger.info("Errors: $errors")

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errors.joinToString(", ") { it })
    }


    @ExceptionHandler(Exception::class)
    fun handleAllRequests(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        logger.error("Exception observed: ${ex.message}", ex)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ex.message)
    }
}
