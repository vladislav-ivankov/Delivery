package by.ivankov.delivery.controller;

import by.ivankov.delivery.exception.ServiceException;
import by.ivankov.delivery.model.Category;
import by.ivankov.delivery.model.Product;
import by.ivankov.delivery.model.User;
import by.ivankov.delivery.repository.ProductRepository;
import by.ivankov.delivery.repository.UserRepository;
import by.ivankov.delivery.service.CategoryService;
import by.ivankov.delivery.service.ProductService;
import by.ivankov.delivery.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    @Autowired
    public AdminController(UserServiceImpl userService, UserRepository userRepository, ProductService productService, ProductRepository productRepository, CategoryService categoryService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
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
    public String addPage(Model model, @ModelAttribute("product") Product product) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addSubmit(@ModelAttribute("product") Product product, @RequestParam("categoryName") Long categoryId) {
        Optional<Category> categoryOptional = categoryService.findById(categoryId);
        Category category = categoryOptional.get();
        product.setCategory(category);
        productService.save(product);
        productService.save(product);
        return "redirect:/admin/addProduct";
    }

    @GetMapping("/editProduct")
    public String getProduct(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "editProducts";
    }

    @GetMapping("/characteristics")
    public String getCharacteristics(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "characteristics";
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestParam("id") Long id, @RequestParam("field") String field,
                                           @RequestParam("value") String value) {
        return productService.updateProduct(id, field, value).ok().build();
    }

    @DeleteMapping("/editProduct/{id}")
    public String deleteProducts(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/editProduct";
    }
}
