package com.tekion.rolesandpermissionsv2.module.role.service;

import com.tekion.arorapostgres.service.BasePostgresServiceImpl;
import com.tekion.arorapostgres.transaction.PostgresTransactionManager;
import com.tekion.rolesandpermissionsv2.module.permission.domain.PermissionDomain;
import com.tekion.rolesandpermissionsv2.module.permission.repo.PermissionRepo;
import com.tekion.rolesandpermissionsv2.module.role.domain.RoleDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RoleEntity;
import com.tekion.rolesandpermissionsv2.module.role.repo.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl extends BasePostgresServiceImpl<RoleEntity, RoleDomain, String>
        implements RoleService {

    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;
    private final PostgresTransactionManager transactionManager;

    public RoleServiceImpl(RoleRepo roleRepo,
                           PermissionRepo permissionRepo,
                           PostgresTransactionManager transactionManager) {
        super(roleRepo);
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
        this.transactionManager = transactionManager;
    }

    public RoleDomain createRoleWithPermissionValidation(RoleDomain role, List<String> permissionIds) {
        return transactionManager.executeInTransaction(roleRepo, () -> {
            List<PermissionDomain> permissions = permissionRepo.getByIds(permissionIds);

            if (permissions.size() != permissionIds.size()) {
                throw new IllegalArgumentException(
                    "Some permissions not found. Expected: " + permissionIds.size() +
                    ", Found: " + permissions.size()
                );
            }

            RoleDomain createdRole = roleRepo.create(role);
            log.info("Created role {} with {} validated permissions", createdRole.getId(), permissions.size());
            return createdRole;
        });
    }

    public List<RoleDomain> updateRolesBulk(List<RoleDomain> roles) {
        return transactionManager.executeInTransaction(roleRepo, () -> {
            List<RoleDomain> updatedRoles = roleRepo.upsertBulk(roles);
            log.info("Updated {} roles in transaction", updatedRoles.size());
            return updatedRoles;
        });
    }

    public void deleteRoleIfNoPermissions(String roleId) {
        transactionManager.executeInTransactionVoid(roleRepo, () -> {
            RoleDomain role = roleRepo.getById(roleId);
            if (role == null) {
                throw new IllegalArgumentException("Role not found: " + roleId);
            }

            roleRepo.deleteById(roleId);
            log.info("Deleted role {}", roleId);
        });
    }

}
