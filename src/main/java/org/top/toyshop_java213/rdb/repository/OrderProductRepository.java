package org.top.toyshop_java213.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.toyshop_java213.entity.OrderProduct;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct, Integer> {
}
