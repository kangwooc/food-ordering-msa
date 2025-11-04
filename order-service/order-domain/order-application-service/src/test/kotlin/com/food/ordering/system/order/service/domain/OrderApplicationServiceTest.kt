package com.food.ordering.system.order.service.domain

import com.food.ordering.commondomain.valueobject.*
import com.food.ordering.system.order.service.domain.entity.Customer
import com.food.ordering.system.order.service.domain.entity.Product
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress
import com.food.ordering.system.order.service.domain.dto.create.OrderItem
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.*

// 각 테스트 클래스마다 인스턴스를 하나만 생성
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [OrderTestConfiguration::class])
class OrderApplicationServiceTest @Autowired constructor(
    private val orderApplicationService: OrderApplicationService,
    private val orderDataMapper: OrderDataMapper,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val restaurantRepository: RestaurantRepository,
) {

    private val logger = LoggerFactory.getLogger(OrderApplicationServiceTest::class.java)

    private var createOrderCommand: CreateOrderCommand? = null
    private var createOrderCommandWrongPrice: CreateOrderCommand? = null
    private var createOrderCommandWrongProductPrice: CreateOrderCommand? = null
    private val CUSTOMER_ID = UUID.fromString("123e4567-e89b-12d3-a456-556642440000")
    private val RESTAURANT_ID = UUID.fromString("123e4567-e89b-12d3-a456-556642440001")
    private val PRODUCT_ID = UUID.fromString("123e4567-e89b-12d3-a456-556642440002")
    private val ORDER_ID = UUID.fromString("123e4567-e89b-12d3-a456-556642440003")
    private val PRICE: BigDecimal = BigDecimal("200.00")

    @BeforeAll
    fun init() {
        // 올바른 주문 생성 명령어
        createOrderCommand = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            price = PRICE,
            items = listOf(
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 1,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("50.00"),
                ),
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00"),
                )
            ),
            address = OrderAddress(
                street = "123 Main St",
                city = "Anytown",
                postalCode = "12345"
            )
        )

        // 잘못된 총 가격을 가진 주문 생성 명령어
        createOrderCommandWrongPrice = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            price = BigDecimal("250.00"), // 잘못된 가격
            items = listOf(
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 1,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("50.00"),
                ),
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00"),
                )
            ),
            address = OrderAddress(
                street = "123 Main St",
                city = "Anytown",
                postalCode = "12345"
            )
        )

        // 잘못된 제품 가격을 가진 주문 생성 명령어
        createOrderCommandWrongProductPrice = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            price = BigDecimal("210.00"),
            items = listOf(
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 1,
                    price = BigDecimal("60.00"),
                    subTotal = BigDecimal("60.00"),
                ),
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00"),
                )
            ),
            address = OrderAddress(
                street = "123 Main St",
                city = "Anytown",
                postalCode = "12345"
            )
        )



        val customer = Customer()
        customer.id = CustomerId(CUSTOMER_ID)

        val restaurant = Restaurant(
            active = true,
            products = listOf(
                Product(
                    name = "product-1",
                    price = com.food.ordering.system.domain.valueobject.Money(BigDecimal("50.00")),
                    productId = com.food.ordering.system.domain.valueobject.ProductId(PRODUCT_ID)
                ),
                Product(
                    name = "product-2",
                    price = com.food.ordering.system.domain.valueobject.Money(BigDecimal("50.00")),
                    productId = com.food.ordering.system.domain.valueobject.ProductId(PRODUCT_ID)
                )
            )
        )
        restaurant.id = RestaurantId(createOrderCommand!!.restaurantId)

        val order = orderDataMapper.createOrderCommandToOrder(createOrderCommand!!)
        order.id = OrderId(ORDER_ID)

        `when`(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(customer)
        `when`(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand!!))).thenReturn(restaurant)
        `when`(orderRepository.save(any<Order>())).thenReturn(order)
    }

    @Test
    fun testCreateOrder() {
        val createOrderResponse = orderApplicationService.createOrder(createOrderCommand!!)
        logger.info("Create Order Response: $createOrderResponse")

        assertEquals(OrderStatus.PENDING, createOrderResponse.orderStatus)
        assertEquals("Order created successfully", createOrderResponse.message)
        assertNotNull(createOrderResponse.orderTrackingId)
    }

    @Test
    fun testCreateOrderWithWrongTotalPrice() {
        val exception = assertThrows(com.food.ordering.system.order.service.domain.exception.OrderDomainException::class.java) {
            orderApplicationService.createOrder(createOrderCommandWrongPrice!!)
        }

        assertEquals("Total price: 250.00 does not match sum of item prices: 200.00", exception.message)
    }

//    @Test
//    fun testCreateOrderWithWrongProductPrice() {
//        val exception = assertThrows(OrderDomainException::class.java) {
//            orderApplicationService.createOrder(createOrderCommandWrongProductPrice!!)
//        }
//
//        assertEquals(exception.message, "Order item price: 60.00 is not valid for product: $PRODUCT_ID")
//    }

    @Test
    fun testCreateOrderWithPassiveRestaurant() {
        val inactiveRestaurant = Restaurant(
            active = false,
            products = listOf(
                Product(
                    name = "product-1",
                    price = com.food.ordering.system.domain.valueobject.Money(BigDecimal("50.00")),
                    productId = com.food.ordering.system.domain.valueobject.ProductId(PRODUCT_ID)
                ),
                Product(
                    name = "product-2",
                    price = com.food.ordering.system.domain.valueobject.Money(BigDecimal("50.00")),
                    productId = com.food.ordering.system.domain.valueobject.ProductId(PRODUCT_ID)
                )
            )
        )
        inactiveRestaurant.id = RestaurantId(createOrderCommand!!.restaurantId)

        `when`(restaurantRepository.findRestaurantInformation(any<Restaurant>())).thenReturn(inactiveRestaurant)

        val exception = assertThrows(OrderDomainException::class.java) {
            orderApplicationService.createOrder(createOrderCommand!!)
        }

        assertEquals(exception.message, "Restaurant with id $RESTAURANT_ID is not active")
    }
}