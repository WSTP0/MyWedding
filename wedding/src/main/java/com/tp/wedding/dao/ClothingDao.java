package com.tp.wedding.dao;

import com.tp.wedding.entity.Clothing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClothingDao extends JpaRepository<Clothing, Long>, JpaSpecificationExecutor<Clothing> {

    Clothing queryByClothingId(String clothingId);

    @Modifying
    void deleteByClothingId(String clothingId);

    @Query(value = "SELECT * FROM clothing WHERE status = ?1 ",
            countQuery = "SELECT count(*) FROM clothing WHERE status = ?1",
            nativeQuery = true)
    Page<Clothing> findListByStatus(String status,Pageable pageable);

}
