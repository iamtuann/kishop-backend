package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.entity.Folder;
import kidev.vn.onlineshopping.model.folder.FolderBasic;
import kidev.vn.onlineshopping.model.folder.FolderModel;
import kidev.vn.onlineshopping.model.media.MediaModel;
import kidev.vn.onlineshopping.repository.FolderRepo;
import kidev.vn.onlineshopping.service.FolderService;
import kidev.vn.onlineshopping.service.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final FolderRepo folderRepo;

    private final MediaService mediaService;

    @Override
    public Folder getFolderById(Long id) {
        return folderRepo.getFolderById(id);
    }

    @Override
    public FolderModel getRootFolder() {
        FolderModel rootFolder = new FolderModel();
        List<Folder> folders = folderRepo.getFoldersByParentFolderId(null);
        rootFolder.setSubFolders(folders.stream().map(FolderBasic::new).collect(Collectors.toList()));
        List<MediaModel> mediaModels = mediaService.getMediaByFolderId(null);
        rootFolder.setMedia(mediaModels);
        return rootFolder;
    }

    @Override
    public FolderModel create(FolderModel folderModel) {
        Folder parentFolder = folderModel.getParentFolderId() == null
                ? null
                : folderRepo.getFolderById(folderModel.getParentFolderId());
        Folder folder = new Folder(folderModel.getName(), parentFolder);
        folder = folderRepo.save(folder);
        return new FolderModel(folder);
    }

    @Override
    public FolderModel update(FolderModel folderModel) {
        Folder folder = folderRepo.getFolderById(folderModel.getId());
        folder.setName(folderModel.getName());
        return new FolderModel(folderRepo.save(folder));
    }

    @Override
    public void delete(Long id) {
        folderRepo.deleteById(id);
    }
}
