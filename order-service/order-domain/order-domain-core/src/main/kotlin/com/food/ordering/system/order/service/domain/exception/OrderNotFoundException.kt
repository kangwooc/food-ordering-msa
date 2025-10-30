package com.food.ordering.orderdomaincore.exception

import com.food.ordering.commondomain.exception.DomainException

class OrderNotFoundException(message: String): DomainException(message)
