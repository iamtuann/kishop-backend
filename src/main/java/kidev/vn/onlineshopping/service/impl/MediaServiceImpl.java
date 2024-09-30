package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.config.cloud.CloudinaryService;
import kidev.vn.onlineshopping.entity.Folder;
import kidev.vn.onlineshopping.entity.Media;
import kidev.vn.onlineshopping.model.media.MediaModel;
import kidev.vn.onlineshopping.repository.MediaRepo;
import kidev.vn.onlineshopping.service.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepo mediaRepo;

    private final CloudinaryService cloudinaryService;

    @Override
    public Media getMediaById(Long id) {
        return mediaRepo.getMediaById(id);
    }

    @Override
    public List<Media> findAllByIds(List<Long> ids) {
        return mediaRepo.findAllById(ids);
    }

    @Override
    public List<MediaModel> getMediaByFolderId(Long folderId) {
        List<Media> media = mediaRepo.getMediaByFolderId(folderId);
        return media.stream().map(MediaModel::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MediaModel> uploadImages(Folder folder, MultipartFile[] images) throws IOException {
        List<Media> media = new ArrayList<>();
        for (MultipartFile image : images) {
            Media newMedia = new Media();
            Map data = cloudinaryService.uploadFile(image, "products/images");
            newMedia.setFolder(folder);
            newMedia.setName((String) data.get("display_name"));
            newMedia.setUrl((String) data.get("url"));
            newMedia.setPublicId((String) data.get("public_id"));
            newMedia.setContentType((String) data.get("format"));
            media.add(newMedia);
        }
        media = mediaRepo.saveAll(media);
        return media.stream().map(MediaModel::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteMedia(List<Long> mediaIds) throws Exception {
        List<Media> media = mediaRepo.findAllById(mediaIds);
        List<String> publicIds = media.stream().map(Media::getPublicId).collect(Collectors.toList());
        cloudinaryService.deleteFiles(publicIds);
        mediaRepo.deleteAll(media);
    }
}
