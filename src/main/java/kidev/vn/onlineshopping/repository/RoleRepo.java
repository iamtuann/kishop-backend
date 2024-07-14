package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
}
