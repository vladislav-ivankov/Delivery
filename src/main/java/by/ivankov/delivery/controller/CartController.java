package by.ivankov.delivery.controller;

import by.ivankov.delivery.exception.ServiceException;
import by.ivankov.delivery.model.Cart;
import by.ivankov.delivery.model.OrderDetails;
import by.ivankov.delivery.model.Product;
import by.ivankov.delivery.service.CartService;
import by.ivankov.delivery.service.OrderDetailsService;
import by.ivankov.delivery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class CartController {

    private final CartService cartService;
    private final OrderDetailsService orderDetailsService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, OrderDetailsService orderDetailsService, ProductService productService) {
        this.cartService = cartService;
        this.orderDetailsService = orderDetailsService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String cartPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Cart cart = cartService.cartPage(userDetails);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/cart")
    public String recalculationPrice(@ModelAttribute("cart") Cart cart, Model model) {
        BigDecimal subtotalPrice = cartService.getSubtotalPrice(cart);
        model.addAttribute("subtotal", subtotalPrice);
        return "cart";
    }

    @PostMapping("/addToCart/{productId}")
    public String addToCart(@PathVariable Long productId, Authentication authentication, Model model) throws ServiceException {
        String username = authentication.getName();
        cartService.addProductToCart(username, productId);
        model.addAttribute("success", "Item successfully added to cart");
        return "redirect:/guest/home";
    }

    @DeleteMapping("/cart/{cartId}")
    public String removeProductFromCart(@PathVariable("cartId") Long cartId, @RequestParam("productId") Long productId) {
        cartService.removeProduct(cartId, productId);
        return "redirect:/user/cart";
    }

    @PostMapping("/updateQuantity")
    @ResponseBody
    public String updateQuantity(@RequestParam Long productId, @RequestParam Integer quantity) {
        Product product = productService.getProductById(productId);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setProduct(product);
        orderDetails.setQuantity(quantity);
        orderDetailsService.save((List<OrderDetails>) orderDetails);
        return "cart";
    }
}
