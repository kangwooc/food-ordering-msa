package com.food.ordering.system.payment.service.domain

import com.food.ordering.system.domain.DomainConstant.Companion.UTC
import com.food.ordering.system.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.domain.valueobject.PaymentStatus
import com.food.ordering.system.payment.service.domain.entity.CreditEntry
import com.food.ordering.system.payment.service.domain.entity.CreditHistory
import com.food.ordering.system.payment.service.domain.entity.Payment
import com.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent
import com.food.ordering.system.payment.service.domain.event.PaymentEvent
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent
import com.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId
import com.food.ordering.system.payment.service.domain.valueobject.TransactionType
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class PaymentDomainServiceImpl : PaymentDomainService {
    private val logger = LoggerFactory.getLogger(PaymentDomainServiceImpl::class.java)

    override fun validateAndInitiatePayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>?,
        paymentCompletedDomainEventPublisher: DomainEventPublisher<PaymentCompletedEvent>,
        paymentFailedDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>
    ): PaymentEvent {
        payment.validatePayment(failureMessages)
        payment.initializePayment()
        logger.info("Payment with id: {} is initiated", payment.id.value)
        validateCreditEntry(creditEntry, payment, failureMessages)
        subtractCreditEntry(creditEntry, payment)
        updateCreditEntry(payment, creditHistories, TransactionType.DEBIT)
        validateCreditHistory(creditEntry, creditHistories, failureMessages)

        if (failureMessages.isNullOrEmpty()) {
            logger.info("Payment with id: {} is Completed", payment.id.value)
            payment.updateStatus(PaymentStatus.COMPLETED)
            return PaymentCompletedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), paymentCompletedDomainEventPublisher)
        }

        logger.error("Payment initialization with id: {} is Failed", payment.id.value)
        payment.updateStatus(PaymentStatus.FAILED)
        return PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), paymentFailedDomainEventPublisher)
    }

    override fun validateAndCancelPayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>?,
        paymentCancelledEventPublisher: DomainEventPublisher<PaymentCancelledEvent>,
        paymentFailedDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>,
    ): PaymentEvent {
        payment.validatePayment(failureMessages)
        addCreditEntry(creditEntry, payment)
        updateCreditEntry(payment, creditHistories, TransactionType.CREDIT)

        if (failureMessages.isNullOrEmpty()) {
            logger.info("Payment with id: {} is Cancelled", payment.id.value)
            payment.updateStatus(PaymentStatus.CANCELLED)
            return PaymentCancelledEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), paymentCancelledEventPublisher)
        }

        logger.error("Payment cancellation with id: {} is Failed", payment.id.value)
        payment.updateStatus(PaymentStatus.FAILED)
        return PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), paymentFailedDomainEventPublisher)
    }

    private fun validateCreditEntry(creditEntry: CreditEntry, payment: Payment, failureMessages: MutableList<String>?) {
        if (payment.price!!.isGreaterThan(creditEntry.totalCreditAmount)) {
            logger.error("Payment with id: {} is already in use.", payment.id.value)
            failureMessages?.add(
                "Customer with id: ${payment.customerId.value} doesn't have enough " +
                        "credit for payment id: ${payment.id.value}"
            )
        }
    }

    private fun subtractCreditEntry(creditEntry: CreditEntry, payment: Payment) {
        creditEntry.subtractCreditAmount(payment.price!!)
    }

    private fun updateCreditEntry(
        payment: Payment,
        creditHistories: MutableList<CreditHistory>,
        transactionType: TransactionType
    ) {
        val creditHistory = CreditHistory(
            customerId = payment.customerId,
            amount = payment.price!!,
            transactionType = transactionType
        )
        creditHistory.id = CreditHistoryId(UUID.randomUUID())

        creditHistories.add(creditHistory)
    }

    private fun validateCreditHistory(
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>?
    ) {
        val totalCreditHistory =
            getTotalHistoryAmount(creditHistories, TransactionType.CREDIT)

        val totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT)

        if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
            logger.error(
                "Customer with id: {} doesn't have credit according to credit history.",
                creditEntry.customerId.value
            )
            failureMessages?.add(
                "Customer with id: ${creditEntry.customerId.value} doesn't have enough " +
                        "credit according to credit history!"
            )
        }

        if (creditEntry.totalCreditAmount != totalCreditHistory.minus(totalDebitHistory)) {
            logger.error(
                "Credit entry total: {} is not equal to credit history total: {} for customer id: {}",
                creditEntry.totalCreditAmount.amount,
                totalCreditHistory.minus(totalDebitHistory).amount,
                creditEntry.customerId.value
            )
            failureMessages?.add(
                "Credit entry total: ${creditEntry.totalCreditAmount.amount} is not equal to " +
                        "credit history total: ${totalCreditHistory.minus(totalDebitHistory).amount} " +
                        "for customer id: ${creditEntry.customerId.value}"
            )
        }

    }

    private fun getTotalHistoryAmount(creditHistories: MutableList<CreditHistory>, transactionType: TransactionType) =
        creditHistories.filter { it.transactionType == transactionType }.map { it.amount }
            .fold(com.food.ordering.system.domain.valueobject.Money.ZERO) { acc, m -> acc.plus(m) }

    private fun addCreditEntry(creditEntry: CreditEntry, payment: Payment) {
        creditEntry.addCreditAmount(payment.price!!)
    }
}