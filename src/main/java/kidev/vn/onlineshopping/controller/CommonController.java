package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.Color;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.brand.BrandModel;
import kidev.vn.onlineshopping.service.BrandService;
import kidev.vn.onlineshopping.service.ColorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class CommonController {
    public static Logger logger = LoggerFactory.getLogger(CommonController.class);
}
