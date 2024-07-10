package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.Color;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.service.ColorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/colors")
public class ColorController {
    public static Logger logger = LoggerFactory.getLogger(ColorController.class);

    @Autowired
    private ColorService colorService;

    @GetMapping
    public CommonResponse<List<Color>> getAllColors() {
        CommonResponse<List<Color>> response = new CommonResponse<>();
        try {
            List<Color> colors = colorService.findAll();
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(colors);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("getAllBrands", e);
        }
        return response;
    }
}
