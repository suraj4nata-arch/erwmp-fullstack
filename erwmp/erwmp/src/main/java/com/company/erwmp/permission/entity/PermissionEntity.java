package com.company.erwmp.permission.entity;

import com.company.erwmp.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}



/*
-->PermissionEntity represents a single action that a user is allowed to perform in the system.

Examples:

* Create a user
* View a user
* Update a user
* Delete a user

*
 */
