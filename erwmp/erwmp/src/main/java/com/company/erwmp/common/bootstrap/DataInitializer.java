package com.company.erwmp.common.bootstrap;

import com.company.erwmp.permission.entity.PermissionEntity;
import com.company.erwmp.permission.repository.PermissionRepository;
import com.company.erwmp.role.entity.RoleEntity;
import com.company.erwmp.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        //Creating Permissions
        PermissionEntity createUser = createPermission("USER_CREATE");
        PermissionEntity viewUser = createPermission("USER_VIEW");
        PermissionEntity updateUser = createPermission("USER_UPDATE");
        PermissionEntity deleteUser = createPermission("USER_DELETE");

        //Creating Roles
        createRole("ROLE_ADMIN", Set.of(createUser, viewUser, updateUser, deleteUser));
        createRole("ROLE_USER", Set.of(viewUser));
    }

    private PermissionEntity createPermission(String name) {
        return permissionRepository.findByName(name)
                .orElseGet(() -> permissionRepository.save(
                        PermissionEntity.builder().name(name).build()
                ));
    }

    private void createRole(String roleName, Set<PermissionEntity> permissions) {      //for the role created, you can set permissions created
        roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(
                        RoleEntity.builder()
                                .name(roleName)
                                .permissions(permissions)
                                .build()
                ));
    }
}

/*
* Bootstrap = initial setup

->Runs only at application startup
* Used for initial data
*
* @Component
* Tells Spring: manage this class
Spring creates this object automatically
* Required for CommandLineRunner to execute
*
*
--> private PermissionEntity createPermission(String name)
    👉 Create permission ONLY if it does not already exist
   --permissionRepository.findByName(name)
     “Check if this key already exists in the vault.”

   --.orElseGet(() -> permissionRepository.save(...))
* If permission exists → reuse it
* If not → create and save it

  -- PermissionEntity.builder().name(name).build()
* Create a Permission object
* Only name is needed
* ID is auto-generated

* */
