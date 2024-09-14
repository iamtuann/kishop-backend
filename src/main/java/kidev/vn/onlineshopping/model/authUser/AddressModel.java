package kidev.vn.onlineshopping.model.authUser;

import kidev.vn.onlineshopping.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel {
    private Long id;
    private Long userId;
    private String name;
    private String receiverName;
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String detailAddress;

    public AddressModel(Address address) {
        this.id = address.getId();
        this.userId = address.getAuthUser().getId();
        this.name = address.getName();
        this.receiverName = address.getReceiverName();
        this.phoneNumber = address.getPhoneNumber();
        this.province = address.getProvince();
        this.district = address.getDistrict();
        this.ward = address.getWard();
        this.detailAddress = address.getDetailAddress();
    }
}
