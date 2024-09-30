package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Folder;
import kidev.vn.onlineshopping.model.media.MediaModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MediaService {
    MediaModel getMediaById(Long id);

    List<MediaModel> getMediaByFolderId(Long folderId);

    List<MediaModel> uploadImages(Folder folder, MultipartFile[] images) throws IOException;

    void deleteMedia(List<Long> mediaIds) throws Exception;
}
