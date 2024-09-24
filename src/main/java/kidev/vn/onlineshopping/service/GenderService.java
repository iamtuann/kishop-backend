package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Gender;

import java.util.List;

public interface GenderService {
    Gender findOne(Long id);

    List<Gender> getGendersByListId(List<Long> ids);
}
