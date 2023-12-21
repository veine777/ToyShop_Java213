package org.top.toyshop_java213.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.top.toyshop_java213.entity.Order;
import org.top.toyshop_java213.entity.OrderProduct;
import org.top.toyshop_java213.entity.User;
import org.top.toyshop_java213.service.OrderService;
import org.top.toyshop_java213.service.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("")
    public String orderList(Model model, Principal principal) {
        Optional<User> user = userService.findUserByLogin(principal.getName());

        Iterable<Order> completedOrders = orderService.findAllCompletedOrders(user.get().getId());
        Optional<Order> notCompletedOrder = orderService.findNotCompletedOrder(user.get().getId());
        // посчитать стоимость (можно вынести в сервисы или в сущность)
        if (notCompletedOrder.isPresent()) {
            double total = 0.0;
            for (OrderProduct op : notCompletedOrder.get().getOrderProductSet()) {
                total += op.getProduct().getPrice() * op.getQuantity();
            }
            model.addAttribute("total", total);
        }
        model.addAttribute("completedOrders", completedOrders);
        model.addAttribute("notCompletedOrder", notCompletedOrder.orElse(null));
        return "order/order-list";
    }

    @GetMapping("buy/{productId}")
    public String buy(@PathVariable Integer productId, Principal principal) {
        Optional<User> user = userService.findUserByLogin(principal.getName());
        orderService.BuyProduct(productId, user.get().getId());
        return "redirect:/product";
    }

    @GetMapping("complete/{orderId}")
    public String complete(@PathVariable Integer orderId) {
        orderService.completeOrder(orderId);
        return "redirect:/order";
    }

    @GetMapping("add-unit/{orderProductId}")
    public String addUnit(@PathVariable Integer orderProductId) {
        orderService.addProduct(orderProductId);
        return "redirect:/order";
    }

    @GetMapping("remove-unit/{orderProductId}")
    public String removeUnit(@PathVariable Integer orderProductId) {
        orderService.RemoveProduct(orderProductId);
        return "redirect:/order";
    }
}
