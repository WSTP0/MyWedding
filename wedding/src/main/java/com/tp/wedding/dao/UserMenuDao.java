package com.tp.wedding.dao;

import com.tp.wedding.entity.Menu;
import com.tp.wedding.entity.UserMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserMenuDao extends JpaRepository<UserMenu, Long>, JpaSpecificationExecutor<UserMenu> {

    @Query(value="select * from user_menu where user_id=?1 and is_delete = 0",nativeQuery = true)
    List<UserMenu> queryByUserId(String userId);

    @Query(value="select * from user_menu where user_id = ?1 and menu_id in ?2 and is_delete = 0",nativeQuery = true)
    List<UserMenu> queryByUserIdAndMenuIds(String userId,List<String> menuIds);

    @Modifying
    @Query(value="update user_menu set is_delete = 1 where user_id = ?1 and menu_id in ?2",nativeQuery = true)
    void deleteByUserIdAndMenuIds(String userId,List<String> menuIds);
}
