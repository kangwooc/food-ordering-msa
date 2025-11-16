package com.food.ordering.system.payment.service.domain.ports

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.payment.service.domain.PaymentDomainService
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest
import com.food.ordering.system.payment.service.domain.entity.CreditEntry
import com.food.ordering.system.payment.service.domain.entity.CreditHistory
import com.food.ordering.system.payment.service.domain.event.PaymentEvent
import com.food.ordering.system.payment.service.domain.exception.PaymentApplicationServiceException
import com.food.ordering.system.payment.service.domain.mapper.PaymentDataMapper
import com.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher
import com.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher
import com.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher
import com.food.ordering.system.payment.service.domain.ports.output.repository.CreditEntryRepository
import com.food.ordering.system.payment.service.domain.ports.output.repository.CreditHistoryRepository
import com.food.ordering.system.payment.service.domain.ports.output.repository.PaymentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PaymentRequestHelper(
    private val paymentDomainService: PaymentDomainService,
    private val paymentDataMapper: PaymentDataMapper,
    private val paymentRepository: PaymentRepository,
    private val creditEntryRepository: CreditEntryRepository,
    private val creditHistoryRepository: CreditHistoryRepository,
    private val paymentCompletedMessagePublisher: PaymentCompletedMessagePublisher,
    private val paymentCancelledMessagePublisher: PaymentCancelledMessagePublisher,
    private val paymentFailedMessagePublisher: PaymentFailedMessagePublisher
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun persistPayment(paymentRequest: PaymentRequest): PaymentEvent {
        logger.info("Persisting payment request $paymentRequest")
        val payment = paymentDataMapper.paymentRequestModelToPayment(paymentRequest)
        val creditEntry = getCreditEntry(payment.customerId)
        val creditHistories = getCreditHistory(payment.customerId).toMutableList()
        val failureMessages = mutableListOf<String>()

        val paymentEvent = paymentDomainService.validateAndInitiatePayment(
            payment,
            creditEntry,
            creditHistories,
            failureMessages,
            paymentCompletedMessagePublisher,
            paymentFailedMessagePublisher
        )

        paymentRepository.save(payment)
        if (failureMessages.isEmpty()) {
            creditEntryRepository.save(creditEntry)
            creditHistoryRepository.save(creditHistories.last())
        }
        return paymentEvent
    }

    @Transactional
    fun persistCancelPayment(paymentRequest: PaymentRequest): PaymentEvent {
        logger.info("Persisting cancel payment request $paymentRequest")
        val payment = paymentRepository.findByOrderId(UUID.fromString(paymentRequest.orderId))
            ?: throw PaymentApplicationServiceException("Could not find payment for order id: ${paymentRequest.orderId}")

        val creditEntry = getCreditEntry(payment.customerId)
        val creditHistories = getCreditHistory(payment.customerId).toMutableList()
        val failureMessages = mutableListOf<String>()

        val paymentEvent = paymentDomainService.validateAndCancelPayment(
            payment,
            creditEntry,
            creditHistories,
            failureMessages,
            paymentCancelledMessagePublisher,
            paymentFailedMessagePublisher
        )

        paymentRepository.save(payment)
        if (failureMessages.isEmpty()) {
            creditEntryRepository.save(creditEntry)
            creditHistoryRepository.save(creditHistories.last())
        }
        return paymentEvent
    }

    private fun getCreditEntry(customerId: CustomerId): CreditEntry {
        val creditEntry = creditEntryRepository.findByCustomerId(customerId)
        if (creditEntry == null) {
            logger.error("Could not find credit entry for customer id: $customerId")
            throw PaymentApplicationServiceException("Could not find credit entry for customer id: $customerId")
        }
        return creditEntry
    }

    private fun getCreditHistory(customerId: CustomerId): List<CreditHistory> {
        val creditHistories = creditHistoryRepository.findByCustomerId(customerId)
        if (creditHistories == null) {
            logger.error("Could not find credit history for customer id: $customerId")
            throw PaymentApplicationServiceException("Could not find credit history for customer id: $customerId")
        }
        return creditHistories
    }
}
