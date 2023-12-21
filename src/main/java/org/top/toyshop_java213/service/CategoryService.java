package org.top.toyshop_java213.service;

import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.Category;

import java.util.Optional;

@Service
public interface CategoryService {
    Optional<Category> findById(Integer id);

    Iterable<Category> findAll();

    Optional<Category> addNew(Category category);

    Optional<Category> update(Category category);

    Optional<Category> deleteById(Integer id);
}
