package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Color;

public interface ColorService {
    Color findOne(Long id);

    void create(Color product);

    void update(Color product);

    void delete(Color product);

    Color getColorByEngName(String engName);
}
