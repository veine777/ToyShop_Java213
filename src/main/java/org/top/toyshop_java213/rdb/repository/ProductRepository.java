package org.top.toyshop_java213.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.toyshop_java213.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
