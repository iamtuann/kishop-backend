package kidev.vn.onlineshopping.repository;

import kidev.vn.onlineshopping.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "FolderRepo")
public interface FolderRepo extends JpaRepository<Folder, Long> {
    Folder getFolderById(Long id);

    List<Folder> getFoldersByParentFolderId(Long id);
}
