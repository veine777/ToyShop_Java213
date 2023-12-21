package org.top.toyshop_java213.service;

import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.Review;

import java.util.Optional;

// Сервис для работы с отзывами
@Service
public interface ReviewService {
    // 1. Получение всех отзывов по отелю
    Iterable<Review> findAllByProduct(Integer productId);

    // 2. Получение отзыва по id
    Optional<Review> findById(Integer id);

    // 3. Добавление отзыва
    Review save(Review review);

    // 4. Редактирование - разрешается только в течение недели после написания
    // само редактирование не меняет дату написания
    Optional<Review> update(Review review);

    // 5. Удаление отзыва
    Optional<Review> deleteById(Integer id);
}

