package com.food.ordering.system.order.service.application.exception.handler

import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException
import com.food.ordering.system.application.handler.ErrorDTO
import com.food.ordering.system.application.handler.GlobalExceptionHandler
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class OrderGlobalExceptionHandler: GlobalExceptionHandler() {
    private val logger = LoggerFactory.getLogger(OrderGlobalExceptionHandler::class.java)

    @ResponseBody
    @ExceptionHandler(OrderDomainException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleOrderDomainException(ex: OrderDomainException): ErrorDTO {
        logger.error("OrderDomainException occurred: ${ex.message}", ex)
        return ErrorDTO(code = HttpStatus.BAD_REQUEST.reasonPhrase, message = ex.message ?: "An error occurred in the order domain")
    }

    @ResponseBody
    @ExceptionHandler(OrderNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleOrderDomainException(ex: OrderNotFoundException): ErrorDTO {
        logger.error("OrderDomainException occurred: ${ex.message}", ex)
        return ErrorDTO(code = HttpStatus.NOT_FOUND.reasonPhrase, message = ex.message ?: "An error occurred in the order domain")
    }
}