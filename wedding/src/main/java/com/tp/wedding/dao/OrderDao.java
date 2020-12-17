package com.tp.wedding.dao;

import com.tp.wedding.entity.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderDao extends JpaRepository<OrderInfo, Long>, JpaSpecificationExecutor<OrderInfo> {

    @Query(value="select * from order_info where order_id=?1 and is_delete = 0",nativeQuery = true)
    OrderInfo queryByOrderId(String orderId);

    @Modifying
    @Query(value="update order_info set is_delete = 1 where order_id = ?1",nativeQuery = true)
    void deleteByOrderId(String orderId);

    @Query(value = "SELECT * FROM order_info WHERE status = ?1 and is_delete=0 \n#pageable\n",
            countQuery = "SELECT count(*) FROM order_info WHERE status = ?1 and is_delete=0",
            nativeQuery = true)
    Page<OrderInfo> findListByStatus(String status, Pageable pageable);
}
