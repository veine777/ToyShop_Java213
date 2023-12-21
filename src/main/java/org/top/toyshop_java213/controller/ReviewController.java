package org.top.toyshop_java213.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.top.toyshop_java213.entity.Product;
import org.top.toyshop_java213.entity.Review;
import org.top.toyshop_java213.service.ProductService;
import org.top.toyshop_java213.service.ReviewService;

import java.util.Optional;

@Controller
@RequestMapping("review")
public class ReviewController {

    // Сервисы
    private final ProductService productService;
    private final ReviewService reviewService;

    public ReviewController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping("new")
    public String getAddForm(Model model) {
        Review review = new Review();
        Iterable<Product> products = productService.findAll();
        model.addAttribute("review", review);
        model.addAttribute("products", products);
        return "review/add-review-form";
    }

    @GetMapping("new/{id}")
    public String getAddForm(@PathVariable Integer id, Model model) {
        Review review = new Review();
        Optional<Product> product = productService.findById(id);
        model.addAttribute("review", review);
        model.addAttribute("products", new Product[]{product.get()});
        return "review/add-review-form";
    }

    @PostMapping("new")
    public String postAddForm(Review review) {
        reviewService.save(review);
        return "redirect:/review/new" + review.getProduct().getId();
    }
}
