package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Role;
import kidev.vn.onlineshopping.repository.RoleRepo;
import kidev.vn.onlineshopping.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepo roleRepo;

    @Override
    public Role findById(Long id) {
        return roleRepo.findRoleById(id);
    }
}
