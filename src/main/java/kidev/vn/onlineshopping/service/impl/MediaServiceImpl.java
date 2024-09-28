package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Media;
import kidev.vn.onlineshopping.model.media.MediaModel;
import kidev.vn.onlineshopping.repository.MediaRepo;
import kidev.vn.onlineshopping.service.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepo mediaRepo;

    @Override
    public MediaModel getMediaById(Long id) {
        return new MediaModel(mediaRepo.getMediaById(id));
    }

    @Override
    public List<MediaModel> getMediaByFolderId(Long folderId) {
        List<Media> media = mediaRepo.getMediaByFolderId(folderId);
        return media.stream().map(MediaModel::new).collect(Collectors.toList());
    }
}
