package com.food.ordering.commondomain.exception

open class DomainException(message: String): RuntimeException(message) {
    constructor(message: String, cause: Throwable): this(message) {
        initCause(cause)
    }
}