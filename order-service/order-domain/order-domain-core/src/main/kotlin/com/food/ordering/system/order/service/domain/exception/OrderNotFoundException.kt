package com.food.ordering.system.order.service.domain.exception

import com.food.ordering.commondomain.exception.DomainException

class OrderNotFoundException(message: String): DomainException(message)
