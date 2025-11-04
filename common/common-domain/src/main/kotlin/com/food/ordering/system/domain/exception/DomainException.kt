package com.food.ordering.system.domain.exception

open class DomainException(message: String): RuntimeException(message) {
    constructor(message: String, cause: Throwable): this(message) {
        initCause(cause)
    }
}