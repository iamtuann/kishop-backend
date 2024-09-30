package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Color;
import kidev.vn.onlineshopping.repository.ColorRepo;
import kidev.vn.onlineshopping.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    ColorRepo colorRepo;


    @Override
    public Color findOne(Long id) {
        return colorRepo.getColorById(id);
    }

    @Override
    public List<Color> getColorsByIds(List<Long> ids) {
        return colorRepo.findAllById(ids);
    }

    @Override
    public void create(Color color) {
        colorRepo.save(color);
    }

    @Override
    public void update(Color color) {
        colorRepo.save(color);
    }

    @Override
    public void delete(Color color) {
        colorRepo.delete(color);
    }

    @Override
    public List<Color> findAll() {
        return colorRepo.findAll();
    }
}
