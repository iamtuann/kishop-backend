package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "authUserRepo")
public interface AuthUserRepo extends JpaRepository<AuthUser, Long> {
    AuthUser findAuthUserById(Long id);

    AuthUser findByUsername(String username);

    boolean existsByEmail(String email);
}
