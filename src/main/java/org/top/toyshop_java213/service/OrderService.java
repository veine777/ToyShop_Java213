package org.top.toyshop_java213.service;

import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.Order;
import org.top.toyshop_java213.entity.OrderProduct;

import java.util.Optional;

@Service
public interface OrderService {
    boolean BuyProduct(Integer productId, Integer userId);

    boolean RemoveProduct(Integer orderProductId);

    boolean addProduct(Integer orderProductId);

    // получение текущего открытого заказа
    Optional<Order> findNotCompletedOrder(Integer userId);


    // получение всех сформированных заказов пользователя
    Iterable<Order> findAllCompletedOrders(Integer userId);

    // получение товара в заказе по заказу и товару
    Optional<OrderProduct> findOrderProductByOrderIdAndProductId(Integer orderId, Integer productId);

    // подтверждение заказа
    boolean completeOrder(Integer orderId);
}
