package com.tp.wedding.dao;

import com.tp.wedding.entity.Clothing;
import com.tp.wedding.entity.Menu;
import com.tp.wedding.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuDao extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu>{

    @Query(value="select * from menu where menu_id=?1 and is_delete = 0",nativeQuery = true)
    Menu queryByMenuId(String menuId);

    @Query(value="select * from menu where is_delete = 0",nativeQuery = true)
    List<Menu> queryAll();

    @Query(value="select * from menu where menu_id in ?1 and is_delete = 0",nativeQuery = true)
    List<Menu> queryByMenuIds(List<String> menuIds);

}
