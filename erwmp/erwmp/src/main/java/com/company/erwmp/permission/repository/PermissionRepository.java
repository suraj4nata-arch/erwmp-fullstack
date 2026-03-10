package com.company.erwmp.permission.repository;

import com.company.erwmp.permission.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    Optional<PermissionEntity> findByName(String name);
}

/*
*
* 🔹 PermissionRepository – Purpose
-->What it does
* PermissionRepository lets you:
* Find permissions by name
* Save new permissions
* Load permissions when assigning to roles
*
* */
