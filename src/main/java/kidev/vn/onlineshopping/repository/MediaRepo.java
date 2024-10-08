package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "MediaRepo")
public interface MediaRepo extends JpaRepository<Media, Long> {
    Media getMediaById(Long id);

    List<Media> getMediaByFolderId(Long id);
}
