package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Color;
import kidev.vn.onlineshopping.repository.ColorRepo;
import kidev.vn.onlineshopping.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Color> getColorsByColorNames(List<String> colorNames) {
        List<Color> colors = new ArrayList<>();
        for (String colorName : colorNames) {
            Color color = colorRepo.getColorByEngName(colorName);
            colors.add(color);
        }
        return colors;
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
    public Color getColorByEngName(String engName) {
        return colorRepo.getColorByEngName(engName);
    }

    @Override
    public List<Color> findAll() {
        return colorRepo.findAll();
    }
}
