package org.top.toyshop_java213.rdb;

import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.Order;
import org.top.toyshop_java213.entity.OrderProduct;
import org.top.toyshop_java213.entity.Product;
import org.top.toyshop_java213.entity.User;
import org.top.toyshop_java213.rdb.repository.OrderProductRepository;
import org.top.toyshop_java213.rdb.repository.OrderRepository;
import org.top.toyshop_java213.service.OrderService;
import org.top.toyshop_java213.service.ProductService;
import org.top.toyshop_java213.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdbOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserService userService;
    private final ProductService productService;

    public RdbOrderService(OrderRepository orderRepository, OrderProductRepository orderProductRepository, UserService userService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public boolean BuyProduct(Integer productId, Integer userId) {
        // System.out.println("User " + userId + " buy product " + productId);
        /*
            1) Если пользователь не имеет открытого заказа, то формируется новый открытый заказ, в него добавляется
            данный товар.
            2) Если пользователь имеет открытый заказ, то товар добавляется в него
            2.1) При этом если в заказе уже есть этот товар, то необходимо увеличить его количество
         */
        // 1. получение объектов
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return false;
        }
        Optional<Product> product = productService.findById(productId);
        if (product.isEmpty()) {
            return false;
        }
        // 2. логика добавления
        Optional<Order> notCompletedOrder = findNotCompletedOrder(userId);
        if (notCompletedOrder.isPresent()) {
            // ветка 2)
            // проверить, нет ли уже такого продукта для такого пользователя среди заказов
            Optional<OrderProduct> orderProduct =
                    findOrderProductByOrderIdAndProductId(notCompletedOrder.get().getId(), productId);
            if (orderProduct.isPresent()) {
                // то увеличить кол-во товара на 1
                orderProduct.get().setQuantity(orderProduct.get().getQuantity() + 1);
                orderProductRepository.save(orderProduct.get());
            } else {
                OrderProduct newOrderProduct = new OrderProduct();
                newOrderProduct.setQuantity(1);
                newOrderProduct.setOrder(notCompletedOrder.get());
                newOrderProduct.setProduct(product.get());
                orderProductRepository.save(newOrderProduct);
            }
        } else {
            // ветка 1)
            Order newOrder = new Order();
            newOrder.setUser(user.get());
            newOrder = orderRepository.save(newOrder);
            // к этому заказу сразу добавить товар
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setQuantity(1);
            orderProduct.setOrder(newOrder);
            orderProduct.setProduct(product.get());
            orderProductRepository.save(orderProduct);
        }
        return true;
    }

    @Override
    public boolean RemoveProduct(Integer orderProductId) {
        Optional<OrderProduct> orderProduct = orderProductRepository.findById(orderProductId);
        if (orderProduct.isPresent()) {
            orderProduct.get().setQuantity(orderProduct.get().getQuantity() - 1);
            if (orderProduct.get().getQuantity() == 0) {
                orderProductRepository.deleteById(orderProductId);
            } else {
                orderProductRepository.save(orderProduct.get());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addProduct(Integer orderProductId) {
        Optional<OrderProduct> orderProduct = orderProductRepository.findById(orderProductId);
        if (orderProduct.isPresent()) {
            orderProduct.get().setQuantity(orderProduct.get().getQuantity() + 1);
            orderProductRepository.save(orderProduct.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Order> findNotCompletedOrder(Integer userId) {
        Iterable<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            if (Objects.equals(order.getUser().getId(), userId) && !order.getCompleted()) {
                Set<OrderProduct> opSet = order.getOrderProductSet();
                order.setOrderProductSet(opSet.stream().sorted(Comparator.comparing(op -> op.getProduct().getTitle())).collect(Collectors.toCollection(LinkedHashSet::new)));
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Order> findAllCompletedOrders(Integer userId) {
        Iterable<Order> orders = orderRepository.findAll();
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (Objects.equals(order.getUser().getId(), userId) && order.getCompleted()) {
                result.add(order);
            }
        }
        return result;
    }

    @Override
    public Optional<OrderProduct> findOrderProductByOrderIdAndProductId(Integer orderId, Integer productId) {
        Iterable<OrderProduct> orderProducts = orderProductRepository.findAll();
        for (OrderProduct orderProduct : orderProducts) {
            if (
                    orderProduct.getOrder().getId().equals(orderId) &&
                            orderProduct.getProduct().getId().equals(productId)
            ) {
                return Optional.of(orderProduct);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean completeOrder(Integer orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty() || order.get().getCompleted()) {
            return false;
        }
        order.get().setCompleted(true);
        order.get().setCompletedAt(new Date());
        orderRepository.save(order.get());
        return true;
    }
}
