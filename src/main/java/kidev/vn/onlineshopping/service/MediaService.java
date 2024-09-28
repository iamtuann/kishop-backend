package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.model.media.MediaModel;

import java.util.List;

public interface MediaService {
    MediaModel getMediaById(Long id);

    List<MediaModel> getMediaByFolderId(Long folderId);
}
