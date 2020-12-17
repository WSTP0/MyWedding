package com.tp.wedding.dao;

import com.tp.wedding.entity.Clothing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClothingDao extends JpaRepository<Clothing, Long>, JpaSpecificationExecutor<Clothing> {

    @Query(value="select * from clothing where clothing_code=?1 and is_delete = 0",nativeQuery = true)
    Clothing queryByClothingCode(String clothingCode);

    @Modifying
    @Query(value="update clothing set is_delete = 1 where clothing_code = ?1",nativeQuery = true)
    void deleteByClothingCode(String clothingCode);

    @Query(value = "SELECT * FROM clothing WHERE status = ?1 and is_delete=0 \n#pageable\n",
            countQuery = "SELECT count(*) FROM clothing WHERE status = ?1 and is_delete=0",
            nativeQuery = true)
    Page<Clothing> findListByStatus(String status,Pageable pageable);

    @Query(value="select * from clothing where id in ?1 and is_delete = 0",nativeQuery = true)
    List<Clothing> queryByIds(List<Integer> ids);
}
