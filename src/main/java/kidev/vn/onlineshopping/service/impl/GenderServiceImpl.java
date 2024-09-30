package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Gender;
import kidev.vn.onlineshopping.repository.GenderRepo;
import kidev.vn.onlineshopping.service.GenderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenderServiceImpl implements GenderService {
    private final GenderRepo genderRepo;

    @Override
    public Gender findOne(Long id) {
        return genderRepo.getGenderById(id);
    }

    @Override
    public List<Gender> getGendersByListId(List<Long> ids) {
        return genderRepo.findAllById(ids);
    }
}
