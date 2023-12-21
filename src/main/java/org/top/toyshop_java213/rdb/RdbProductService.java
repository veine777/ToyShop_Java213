package org.top.toyshop_java213.rdb;

import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.Category;
import org.top.toyshop_java213.entity.Product;
import org.top.toyshop_java213.entity.ProductCategory;
import org.top.toyshop_java213.form.ProductFilterForm;
import org.top.toyshop_java213.rdb.repository.ProductCategoryRepository;
import org.top.toyshop_java213.rdb.repository.ProductRepository;
import org.top.toyshop_java213.service.CategoryService;
import org.top.toyshop_java213.service.ProductService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class RdbProductService implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryService categoryService;

    public RdbProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> addNew(Product product) {
        return Optional.of(productRepository.save(product));
    }

    @Override
    public Optional<Product> update(Product product) {
        Optional<Product> updated = findById(product.getId());
        if (updated.isPresent()) {
            return Optional.of(productRepository.save(product));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> deleteById(Integer id) {
        Optional<Product> deleted = findById(id);
        if (deleted.isPresent()) {
            productRepository.deleteById(id);
        }
        return deleted;
    }

    @Override
    public boolean addCategory(ProductCategory productCategory) {
        // 1. проверить, есть ли такой продукт и есть ли такая категория
        Optional<Product> product = findById(productCategory.getProduct().getId());
        if (product.isEmpty()) {
            return false;
        }
        Optional<Category> category = categoryService.findById(productCategory.getCategory().getId());
        if (category.isEmpty()) {
            return false;
        }
        // 2. проверить, нет ли уже такой категории в этом продукте
        Integer newProductCategoryId = productCategory.getCategory().getId();
        Set<ProductCategory> productCategories = product.get().getProductCategorySet();

        for (ProductCategory pc : productCategories) {
            if (Objects.equals(pc.getCategory().getId(), newProductCategoryId)) {
                return false;
            }
        }
        productCategoryRepository.save(productCategory);
        return true;
    }

    @Override
    public Iterable<Product> filter(ProductFilterForm form) {
        // Каскадная фильтрация:
        // Возьмем все продукты и начнем фильтровать их
        List<Product> products = (List<Product>) findAll();
        if (!form.getProduct().equals("")) {
            // отфильтровать по названию и описанию: оставить только те продукты, которые в
            // title или description содержат подстроку, указанную в form.product
            String pattern = form.getProduct().toLowerCase();
            products = products.stream()
                    .filter(p -> p.getTitle().toLowerCase().contains(pattern) ||
                            p.getDescription().toLowerCase().contains(pattern)
                    )
                    .toList();
        }
        if (form.getMinPrice() != 0) {
            products = products.stream()
                    .filter(p -> p.getPrice() >= form.getMinPrice())
                    .toList();
        }
        if (form.getMaxPrice() != 0) {
            products = products.stream()
                    .filter(p -> p.getPrice() <= form.getMaxPrice())
                    .toList();
        }
        return products;
    }
}
