package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository(value = "colorRepo")
public interface ColorRepo extends JpaRepository<Color, Long> {
    Color getColorById(Long id);
    Color getColorByEngName(String engName);
}
