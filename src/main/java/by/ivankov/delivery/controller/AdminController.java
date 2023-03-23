package by.ivankov.delivery.controller;

import by.ivankov.delivery.exception.ServiceException;
import by.ivankov.delivery.model.Product;
import by.ivankov.delivery.model.User;
import by.ivankov.delivery.repository.ProductRepository;
import by.ivankov.delivery.repository.UserRepository;
import by.ivankov.delivery.service.ProductService;
import by.ivankov.delivery.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired
    public AdminController(UserServiceImpl userService, UserRepository userRepository, ProductService productService, ProductRepository productRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }


    @GetMapping("/addAdmin")
    public String regPage(@ModelAttribute("user") User user) {
        return "addAdmin";
    }

    @PostMapping("/addAdmin")
    public String regSubmit(@ModelAttribute("user") User user, Model model) {
        try {
            userService.save(user, "ROLE_ADMIN");
            return "redirect:/guest/login";
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "addAdmin";
        }
    }

    @GetMapping("/editUser")
    public String getUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "editUsers";
    }

    @DeleteMapping("/editUser/{id}")
    public String deleteUsers(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/editUser";
    }

    @GetMapping("/addProduct")
    public String addPage(@ModelAttribute("product") Product product) {
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addSubmit(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/admin/addProduct";
    }

    @GetMapping("/editProduct")
    public String getProduct(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "editProducts";
    }

    @DeleteMapping("/editProduct/{id}")
    public String deleteProducts(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/editProduct";
    }
}
