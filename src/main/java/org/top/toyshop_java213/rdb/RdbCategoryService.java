package org.top.toyshop_java213.rdb;

import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.Category;
import org.top.toyshop_java213.rdb.repository.CategoryRepository;
import org.top.toyshop_java213.service.CategoryService;

import java.util.Optional;

@Service
public class RdbCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    public RdbCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> addNew(Category category) {
        Optional<Category> sameCategory = categoryRepository.findByTitle(category.getTitle());
        if (sameCategory.isEmpty()) {
            return Optional.of(categoryRepository.save(category));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Category> update(Category category) {
        Optional<Category> updated = findById(category.getId());
        Optional<Category> sameCategory = categoryRepository.findByTitle(category.getTitle());
        if (updated.isPresent() && sameCategory.isEmpty()) {
            return Optional.of(categoryRepository.save(category));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Category> deleteById(Integer id) {
        Optional<Category> deleted = findById(id);
        if (deleted.isPresent()) {
            categoryRepository.deleteById(id);
        }
        return deleted;
    }
}
