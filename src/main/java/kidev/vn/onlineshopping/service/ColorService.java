package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Color;

import java.util.List;

public interface ColorService {
    Color findOne(Long id);

    List<Color> getColorsByIds(List<Long> ids);

    void create(Color product);

    void update(Color product);

    void delete(Color product);

    List<Color> findAll();
}
