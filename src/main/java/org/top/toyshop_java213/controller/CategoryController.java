package org.top.toyshop_java213.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.toyshop_java213.entity.Category;
import org.top.toyshop_java213.service.CategoryService;

import java.util.Optional;

@Controller
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String findAll(Model model) {
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/category-list";
    }

    @GetMapping("new")
    public String getNew(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "category/add-category-form";
    }

    @PostMapping("new")
    public String postNew(Category category, RedirectAttributes redirectAttributes) {
        Optional<Category> added = categoryService.addNew(category);
        if (added.isPresent()) {
            redirectAttributes.addFlashAttribute(
                    ViewMessageKeys.SUCCESS_MESSAGE,
                    "Категория " + category + " добавлена."
            );
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Категория " + category + " не может быть добавлена.");
        }
        return "redirect:/category";
    }

    @GetMapping("update/{id}")
    public String getUpdate(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Category> updated = categoryService.findById(id);
        if (updated.isPresent()) {
            model.addAttribute("category", updated.get());
            return "category/update-category-form";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Категория с id " + id + " не найдена.");
            return "redirect:/category";
        }
    }

    @PostMapping("update")
    public String postUpdate(Category category, RedirectAttributes redirectAttributes) {
        Optional<Category> updated = categoryService.update(category);
        if (updated.isPresent()) {
            redirectAttributes.addFlashAttribute(
                    ViewMessageKeys.SUCCESS_MESSAGE,
                    "Категория " + category + " изменена."
            );
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Категория " + category + " не может быть изменена.");
        }
        return "redirect:/category";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Category> deleted = categoryService.deleteById(id);
        if (deleted.isPresent()) {
            redirectAttributes.addFlashAttribute(
                    ViewMessageKeys.SUCCESS_MESSAGE,
                    "Категория " + deleted.get() + " удалена."
            );
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Категория с id " + id + " не может быть удалена.");
        }
        return "redirect:/category";
    }
}
