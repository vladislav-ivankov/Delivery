package by.ivankov.delivery.controller;

import by.ivankov.delivery.model.Cart;
import by.ivankov.delivery.model.Product;
import by.ivankov.delivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private final ProductRepository productRepository;

    @Autowired
    public UserController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/guest/home";
    }

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    @PostMapping("/addToCart")
    public String addProductToCart(@RequestParam("productId") Long id, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        Product product = productRepository.getProductById(id);
        cart.getProductSet().add(product);
        return "redirect:user/home";
    }
}
