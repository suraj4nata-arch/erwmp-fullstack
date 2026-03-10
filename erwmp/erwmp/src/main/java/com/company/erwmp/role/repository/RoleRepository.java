package com.company.erwmp.role.repository;

import com.company.erwmp.role.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}

/*
* 🔹 RoleRepository – Purpose
What it does

-->RoleRepository lets you:

* Find roles by name
* Save roles

-->Assign permissions to roles
Assign roles to users
* */