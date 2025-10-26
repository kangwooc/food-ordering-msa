package com.food.ordering.orderdomaincore.entity

import com.food.ordering.commondomain.entity.AggregateRoot
import com.food.ordering.commondomain.valueobject.CustomerId

class Customer: AggregateRoot<CustomerId>() {
}