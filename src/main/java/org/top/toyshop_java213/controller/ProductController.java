package org.top.toyshop_java213.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.toyshop_java213.entity.Category;
import org.top.toyshop_java213.entity.Product;
import org.top.toyshop_java213.entity.ProductCategory;
import org.top.toyshop_java213.entity.Review;
import org.top.toyshop_java213.form.ProductFilterForm;
import org.top.toyshop_java213.service.CategoryService;
import org.top.toyshop_java213.service.ProductService;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String findAll(ProductFilterForm filter, Model model) {
        if (filter.isFormEmpty()) {
            Iterable<Product> products = productService.findAll();
            model.addAttribute("products", products);
        } else {
            // сделать фильтрацию
            Iterable<Product> filteredProducts = productService.filter(filter);
            model.addAttribute("products", filteredProducts);
        }
        model.addAttribute("filter", filter);
        return "product/product-list";
    }

    @GetMapping("{id}")
    public String findById(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            Review review = new Review();
            review.setProduct(product.get());
            List<Review> reviews = product.get().getReviewSet()
                    .stream()
                    .sorted(Comparator.comparing(Review::getWrittenIn).reversed())
                    .toList();
            model.addAttribute("review", review);
            model.addAttribute("reviews", reviews);
            model.addAttribute("product", product.get());
            model.addAttribute("productCategory", new ProductCategory());
            List<Category> categories = new ArrayList<>();
            for (Category category : categoryService.findAll()) {
                if (product.get().getProductCategorySet()
                        .stream()
                        .noneMatch(c -> Objects.equals(c.getCategory().getId(), category.getId()))) {
                    categories.add(category);
                }
            }
            model.addAttribute("categories", categories);
            return "product/product-details";
        }
        redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE, "Товар не найден");
        return "redirect:/product";
    }

    @GetMapping("new")
    public String getNew(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/add-product-form";
    }

    @PostMapping("new")
    public String postNew(Product product, @RequestParam MultipartFile previewImage, RedirectAttributes redirectAttributes) throws IOException {
        // перед добавлением декодировать данные из формы и записать в данные объекта
        String previewImageData = Base64.getEncoder().encodeToString(previewImage.getBytes());
        product.setPreviewImageData(previewImageData);
        Optional<Product> added = productService.addNew(product);
        if (added.isPresent()) {
            redirectAttributes.addFlashAttribute(
                    ViewMessageKeys.SUCCESS_MESSAGE,
                    "Товар " + product + " добавлен."
            );
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Товар " + product + " не может быть добавлен.");
        }
        return "redirect:/product";
    }

    @GetMapping("update/{id}")
    public String getUpdate(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> updated = productService.findById(id);
        if (updated.isPresent()) {
            model.addAttribute("product", updated.get());
            return "product/update-product-form";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Товар с id " + id + " не найден.");
            return "redirect:/product";
        }
    }

    @PostMapping("update")
    public String postUpdate(Product product, @RequestParam MultipartFile previewImage, RedirectAttributes redirectAttributes) throws IOException {
        String previewImageData = Base64.getEncoder().encodeToString(previewImage.getBytes());
        product.setPreviewImageData(previewImageData);
        Optional<Product> updated = productService.update(product);
        if (updated.isPresent()) {
            redirectAttributes.addFlashAttribute(
                    ViewMessageKeys.SUCCESS_MESSAGE,
                    "Товар " + product + " измене."
            );
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Товар " + product + " не может быть изменен.");
        }
        return "redirect:/product";
    }


    @GetMapping("delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Product> deleted = productService.deleteById(id);
        if (deleted.isPresent()) {
            redirectAttributes.addFlashAttribute(
                    ViewMessageKeys.SUCCESS_MESSAGE,
                    "Товар " + deleted.get() + " удален."
            );
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Товар с Id " + id + " не может быть удален.");
        }
        return "redirect:/product";
    }

    @PostMapping("{id}/add-category")
    public String addCategory(@PathVariable Integer id, ProductCategory productCategory, RedirectAttributes redirectAttributes) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            productCategory.setProduct(product.get());
            if (productService.addCategory(productCategory)) {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                        "Категория добавлена");
            } else {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                        "Категория не добавлена");
            }
            return "redirect:/product/" + productCategory.getProduct().getId();
        } else {
            return "redirect:/product/0"; // не найден
        }

    }
}
