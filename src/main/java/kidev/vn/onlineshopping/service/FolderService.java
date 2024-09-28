package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.Folder;
import kidev.vn.onlineshopping.model.folder.FolderModel;

public interface FolderService {
    Folder getFolderById(Long id);

    FolderModel getRootFolder();

    FolderModel create(FolderModel folderModel);

    FolderModel update(FolderModel folderModel);
}
