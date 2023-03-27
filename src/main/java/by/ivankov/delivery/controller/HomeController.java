package by.ivankov.delivery.controller;

import by.ivankov.delivery.exception.ServiceException;
import by.ivankov.delivery.model.Product;
import by.ivankov.delivery.model.User;
import by.ivankov.delivery.repository.ProductRepository;
import by.ivankov.delivery.security.RegistrationDetails;
import by.ivankov.delivery.service.ProductService;
import by.ivankov.delivery.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/guest")
public class HomeController {
    private final UserServiceImpl userService;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public HomeController(UserServiceImpl userService, ProductRepository productRepository, ProductService productService) {
        this.userService = userService;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/home")
    public String homePage(Authentication authentication, Model model) {
        userService.givePagesByRole(authentication, model);
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/info")
    public String userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RegistrationDetails regDetails = (RegistrationDetails) authentication.getPrincipal();
        System.out.println(regDetails.getUser());

        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/reg")
    public String regPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/reg")
    public String regSubmit(@ModelAttribute("user") User user, Model model) {
        try {
            userService.save(user, "ROLE_USER");
            return "redirect:/guest/login";
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.searchProducts(query);
        model.addAttribute("products", products);
        return "productPage";
    }

    @GetMapping("/productPage/{id}")
    public String getProduct(@PathVariable("id") Long id, Authentication authentication, Model model) {
        userService.givePagesByRole(authentication, model);
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productPage";
    }
}
