package com.tp.wedding.dao;

import com.tp.wedding.entity.PackageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PackageInfoDao extends JpaRepository<PackageInfo, Long>, JpaSpecificationExecutor<PackageInfo> {

}
