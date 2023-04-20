package by.ivankov.delivery.controller;

import by.ivankov.delivery.model.Category;
import by.ivankov.delivery.model.Product;
import by.ivankov.delivery.service.CategoryService;
import by.ivankov.delivery.service.ProductService;
import by.ivankov.delivery.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserServiceImpl userService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService, UserServiceImpl userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
    }


    @GetMapping("/admin/addCategory")
    public String addCategoryPage(@ModelAttribute("category") Category category) {
        return "addCategory";
    }

    @PostMapping("/admin/addCategory")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/admin/addProduct";
    }

    @GetMapping("/guest/category")
    public String categoryPage(Authentication authentication, Model model) {
        userService.givePagesByRole(authentication, model);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category";
    }

    @GetMapping("/guest/category/{id}")
    public String productInCategory(@PathVariable("id") Long categoryId, Model model, Authentication authentication) {
        userService.givePagesByRole(authentication, model);
        Optional<Category> optionalCategory = categoryService.findById(categoryId);
        Category category = optionalCategory.get();
        List<Product> products = productService.findByCategory(category);
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        return "productCategory";
    }
}
