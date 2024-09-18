package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Address;
import kidev.vn.onlineshopping.model.authUser.AddressModel;

import java.util.List;

public interface AddressService {
    Address getAddressByIdAndAuthUserId(Long id, Long userId);

    List<AddressModel> getAddressesByAuthUserId(Long id);

    AddressModel create(AddressModel addressModel, Long userId);

    AddressModel update(AddressModel addressModel, Long userId);

    void deleteById(Long id);
}
