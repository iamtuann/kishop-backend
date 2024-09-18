package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Address;
import kidev.vn.onlineshopping.model.authUser.AddressModel;
import kidev.vn.onlineshopping.repository.AddressRepo;
import kidev.vn.onlineshopping.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepo addressRepo;

    private final AuthUserServiceImpl authUserService;

    @Override
    public Address getAddressByIdAndAuthUserId(Long id, Long userId) {
        return addressRepo.getAddressByIdAndAuthUserId(id, userId);
    }

    @Override
    public List<AddressModel> getAddressesByAuthUserId(Long id) {
        List<Address> addresses = addressRepo.getAddressesByAuthUserIdOrderByIdDesc(id);
        return addresses.stream()
                .map(AddressModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public AddressModel create(AddressModel addressModel, Long userId) {
        Address address = new Address();
        address.setAuthUser(authUserService.findById(userId));
        setAddress(addressModel, address);
        address = addressRepo.save(address);
        return new AddressModel(address);
    }

    @Override
    public AddressModel update(AddressModel addressModel, Long userId) {
        Address address = addressRepo.getAddressByIdAndAuthUserId(addressModel.getId(), userId);
        setAddress(addressModel, address);
        address = addressRepo.save(address);
        return new AddressModel(address);
    }

    @Override
    public void deleteById(Long id) {
        addressRepo.deleteById(id);
    }

    private void setAddress(AddressModel addressModel, Address address) {
        address.setName(addressModel.getName());
        address.setReceiverName(addressModel.getReceiverName());
        address.setPhoneNumber(addressModel.getPhoneNumber());
        address.setProvince(addressModel.getProvince());
        address.setDistrict(addressModel.getDistrict());
        address.setWard(addressModel.getWard());
        address.setDetailAddress(addressModel.getDetailAddress());
    }
}
