package com.food.ordering.system.order.service.application.rest

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping(value = ["/orders"], produces = ["application/vnd.api.v1+json"])
class OrderController(
    private val orderApplicationService: OrderApplicationService
) {
    private val logger = LoggerFactory.getLogger(OrderController::class.java)

    @PostMapping
    fun createOrder(@RequestBody command: CreateOrderCommand): ResponseEntity<CreateOrderResponse> {
        logger.info("Received create order request: $command")
        val response = orderApplicationService.createOrder(command)
        logger.info("Order created successfully: $response")
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{trackingId}")
    fun getOrderByTrackingId(@PathVariable trackingId: UUID): ResponseEntity<TrackOrderResponse> {
        logger.info("Received track order request for trackingId: $trackingId")
        val response = orderApplicationService.trackOrder(TrackOrderQuery(trackingId))
        logger.info("Order tracked successfully: $response")
        return ResponseEntity.ok(response)
    }
}