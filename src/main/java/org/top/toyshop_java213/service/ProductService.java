package org.top.toyshop_java213.service;

import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.Product;
import org.top.toyshop_java213.entity.ProductCategory;
import org.top.toyshop_java213.form.ProductFilterForm;

import java.util.Optional;

@Service
public interface ProductService {
    Optional<Product> findById(Integer id);

    Iterable<Product> findAll();

    Optional<Product> addNew(Product product);

    Optional<Product> update(Product product);

    Optional<Product> deleteById(Integer id);

    // группа методов для работы с категориями продукта
    boolean addCategory(ProductCategory productCategory);

    // метод получения всех товаров, соответствующих форме
    Iterable<Product> filter(ProductFilterForm form);
}
