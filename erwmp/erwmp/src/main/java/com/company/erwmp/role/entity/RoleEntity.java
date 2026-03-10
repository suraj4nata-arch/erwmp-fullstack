package com.company.erwmp.role.entity;

import com.company.erwmp.permission.entity.PermissionEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissions;
}

/*
-->RoleEntity exists to define what a user is allowed to do.
User = who you are
Role = what permissions you have

This code defines a many-to-many relationship between:
Role
-->Permission
👉 A role can have many permissions
👉 A permission can belong to many roles

-->@ManyToMany(fetch = FetchType.EAGER)
    What it means (simple)
* Relationship type: many-to-many
* Fetch strategy: EAGER

-->FetchType.EAGER
📌 Means:

-->Whenever you load a Role
* Its Permissions are loaded immediately

@JoinTable(
        name = "role_permissions",
-->What it means

* Creates a third table
* This table links roles and permissions

joinColumns = @JoinColumn(name = "role_id"),
Column in role_permissions table
Refers to Role

 */