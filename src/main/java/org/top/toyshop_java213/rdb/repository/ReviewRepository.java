package org.top.toyshop_java213.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.toyshop_java213.entity.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
}