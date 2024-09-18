package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.config.security.service.UserDetailsImpl;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.authUser.AddressModel;
import kidev.vn.onlineshopping.service.AddressService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/addresses")
public class AddressController {
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    private final AddressService addressService;

    @GetMapping("")
    public CommonResponse<List<AddressModel>> getAddresses(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonResponse<List<AddressModel>> response = new CommonResponse<>();
        try {
            List<AddressModel> addressModels = addressService.getAddressesByAuthUserId(userDetails.getId());
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setMessage("Get addresses success");
            response.setOutput(addressModels);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("Có lỗi xảy ra");
            response.setOutput(null);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createUser ", e);
        }
        return response;
    }

    @PostMapping("")
    public CommonResponse<AddressModel> createAddresses(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody AddressModel addressModel
    ) {
        CommonResponse<AddressModel> response = new CommonResponse<>();
        try {
            AddressModel output = addressService.create(addressModel, userDetails.getId());
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setMessage("Create address success");
            response.setOutput(output);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("Có lỗi xảy ra");
            response.setOutput(null);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createUser ", e);
        }
        return response;
    }

    @PostMapping("/update")
    public CommonResponse<AddressModel> updateAddresses(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody AddressModel addressModel
    ) {
        CommonResponse<AddressModel> response = new CommonResponse<>();
        try {
            if (addressService.getAddressByIdAndAuthUserId(addressModel.getId(), userDetails.getId()) == null) {
                response.setStatusCode(Constants.RestApiReturnCode.NOT_FOUND);
                response.setMessage("Cannot find address");
                response.setOutput(null);
                response.setError(Constants.RestApiReturnCode.NOT_FOUND_TXT);
            } else {
                AddressModel output = addressService.update(addressModel, userDetails.getId());
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setMessage("Update address success");
                response.setOutput(output);
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("Có lỗi xảy ra");
            response.setOutput(null);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createUser ", e);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public CommonResponse<?> updateAddresses(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            if (addressService.getAddressByIdAndAuthUserId(id, userDetails.getId()) == null) {
                response.setStatusCode(Constants.RestApiReturnCode.NOT_FOUND);
                response.setMessage("Cannot find address");
                response.setOutput(null);
                response.setError(Constants.RestApiReturnCode.NOT_FOUND_TXT);
            } else {
                addressService.deleteById(id);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setMessage("Delete address success");
                response.setOutput(null);
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("Có lỗi xảy ra");
            response.setOutput(null);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createUser ", e);
        }
        return response;
    }
}
