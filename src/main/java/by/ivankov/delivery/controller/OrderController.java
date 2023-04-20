package by.ivankov.delivery.controller;

import by.ivankov.delivery.model.*;
import by.ivankov.delivery.service.CartService;
import by.ivankov.delivery.service.OrderDetailsService;
import by.ivankov.delivery.service.OrderService;
import by.ivankov.delivery.service.ProductService;
import by.ivankov.delivery.service.impl.UserServiceImpl;
import by.ivankov.delivery.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class OrderController {

    private final OrderService orderService;
    private final UserServiceImpl userService;
    private final CartService cartService;

    private final ProductService productService;
    private final OrderDetailsService orderDetailsService;

    @Autowired
    public OrderController(OrderService orderService, UserServiceImpl userService, CartService cartService, ProductService productService, OrderDetailsService orderDetailsService) {
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
        this.orderDetailsService = orderDetailsService;
    }

    @PostMapping("/order")
    public String orderSubmit(@ModelAttribute("order") Order order, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        Cart cart = user.getCart();
        cart.getProducts();
        order.setCart(cart);
        orderService.save(order);
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for(Product product : cart.getProducts()) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setProduct(product);
            orderDetails.setOrder(order);
            orderDetailsList.add(orderDetails);
        }
        orderDetailsService.save(orderDetailsList);

        return "receipt";
    }

    @GetMapping("/order")
    public String orderPage(Model model) {
        model.addAttribute("order", new Order());
        return "order";
    }

    @GetMapping("/check")
    public String checkPage(Model model, Principal principal) {
        LocalDateTime time = LocalDateTime.now();
        User user = userService.getUserByUsername(principal.getName());
        Cart cart = user.getCart();
        List<Product> productList = cart.getProducts();
        model.addAttribute("products", productList);
        model.addAttribute("orderNumber", IdGenerator.orderIdGenerator());
        model.addAttribute("time", time);
        return "receipt";
    }
}
