package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "addressRepo")
public interface AddressRepo extends JpaRepository<Address, Long> {
    Address getAddressByIdAndAuthUserId(Long id, Long userId);

    List<Address> getAddressesByAuthUserIdOrderByIdDesc(Long id);
}
