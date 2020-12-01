package com.tp.wedding.dao;

import com.tp.wedding.entity.Clothing;
import com.tp.wedding.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderDao extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query(value="select * from order where order_id=?1 and is_delete = 0",nativeQuery = true)
    Order queryByOrderId(String orderId);

    @Modifying
    @Query(value="update order set is_delete = 1 where order_id = ?1",nativeQuery = true)
    void deleteByOrderId(String orderId);

    @Query(value = "SELECT * FROM order WHERE status = ?1 and is_delete=0 \n#pageable\n",
            countQuery = "SELECT count(*) FROM order WHERE status = ?1 and is_delete=0",
            nativeQuery = true)
    Page<Order> findListByStatus(String status, Pageable pageable);
}
