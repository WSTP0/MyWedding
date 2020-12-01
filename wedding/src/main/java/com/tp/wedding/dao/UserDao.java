package com.tp.wedding.dao;

import com.tp.wedding.entity.Order;
import com.tp.wedding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query(value="select * from user where user_id=?1 and is_delete = 0",nativeQuery = true)
    User queryByUserId(String userId);

    @Query(value="select * from user where user_name=?1 and is_delete = 0",nativeQuery = true)
    User queryByUserName(String userName);

}
