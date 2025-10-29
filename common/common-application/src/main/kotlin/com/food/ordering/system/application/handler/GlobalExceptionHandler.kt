package com.food.ordering.system.application.handler

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.ValidationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ResponseBody
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(ex: Exception): ErrorDTO {
        logger.error("Unhandled exception occurred: ${ex.message}", ex)
        return ErrorDTO(
            code = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = ex.message ?: "An unexpected error occurred"
        )
    }


    @ResponseBody
    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(ex: ValidationException): ErrorDTO {
        logger.error("Unhandled exception occurred: ${ex.message}", ex)
        if (ex is ConstraintViolationException) {
            val violations = extractViolationsFromException(ex)
            logger.error(violations, ex)
            return ErrorDTO(
                code = HttpStatus.BAD_REQUEST.reasonPhrase,
                message = "Validation failed: $violations"
            )
        } else {
            logger.error("Unhandled exception occurred: ${ex.message}")
            return ErrorDTO(
                code = HttpStatus.BAD_REQUEST.reasonPhrase,
                message = ex.message ?: "A validation error occurred"
            )
        }
    }

    private fun extractViolationsFromException(ex: ConstraintViolationException): String {
        return ex.constraintViolations.joinToString("; ") { violation ->
            "${violation.propertyPath}: ${violation.message}"
        }
    }

}