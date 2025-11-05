package com.food.ordering.system.payment.service.dataaccess.credithistory.adapter

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.payment.service.dataaccess.credithistory.mapper.CreditHistoryDataAccessMapper
import com.food.ordering.system.payment.service.dataaccess.credithistory.repository.CreditHistoryJpaRepository
import com.food.ordering.system.payment.service.domain.entity.CreditHistory
import com.food.ordering.system.payment.service.domain.ports.output.repository.CreditHistoryRepository
import org.springframework.stereotype.Component

@Component
class CreditHistoryRepositoryImpl(
    private val creditHistoryJpaRepository: CreditHistoryJpaRepository,
    private val creditHistoryDataAccessMapper: CreditHistoryDataAccessMapper,
): CreditHistoryRepository {
    override fun save(creditHistory: CreditHistory): CreditHistory {
        return creditHistoryDataAccessMapper.creditHistoryEntityToCreditHistory(
            creditHistoryJpaRepository.save(
                creditHistoryDataAccessMapper.creditHistoryToCreditHistoryEntity(creditHistory)
            )
        )
    }

    override fun findByCustomerId(customerId: CustomerId): List<CreditHistory>? {
        return creditHistoryJpaRepository.findByCustomerId(customerId.value).map {
            creditHistoryDataAccessMapper.creditHistoryEntityToCreditHistory(it)
        }
    }
}