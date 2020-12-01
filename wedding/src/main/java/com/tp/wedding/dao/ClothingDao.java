package com.tp.wedding.dao;

import com.tp.wedding.entity.Clothing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClothingDao extends JpaRepository<Clothing, Long>, JpaSpecificationExecutor<Clothing> {

    @Query(value="select * from clothing where clothing_id=?1 and is_delete = 0",nativeQuery = true)
    Clothing queryByClothingId(String clothingId);

    @Modifying
    @Query(value="update clothing set is_delete = 1 where clothing_id = ?1",nativeQuery = true)
    void deleteByClothingId(String clothingId);

    @Query(value = "SELECT * FROM clothing WHERE status = ?1 and is_delete=0 \n#pageable\n",
            countQuery = "SELECT count(*) FROM clothing WHERE status = ?1 and is_delete=0",
            nativeQuery = true)
    Page<Clothing> findListByStatus(String status,Pageable pageable);

}
