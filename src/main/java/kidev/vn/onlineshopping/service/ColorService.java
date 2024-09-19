package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Color;

import java.util.List;

public interface ColorService {
    Color findOne(Long id);

    List<Color> getColorsByColorNames(List<String> colorNames);

    void create(Color product);

    void update(Color product);

    void delete(Color product);

    Color getColorByEngName(String engName);

    List<Color> findAll();
}
