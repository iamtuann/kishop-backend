package kidev.vn.onlineshopping.model.folder;

import kidev.vn.onlineshopping.entity.Folder;
import kidev.vn.onlineshopping.model.media.MediaModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderModel {
    private Long id;
    private Long parentFolderId;
    private String name;
    private Date createdDate;
    private Date updatedDate;
    private List<FolderBasic> subFolders;
    private List<MediaModel> media;

    public FolderModel(Folder folder) {
        this.id = folder.getId();
        this.parentFolderId = folder.getParentFolder() == null ? null : folder.getParentFolder().getId();
        this.name = folder.getName();
        this.createdDate = folder.getCreatedDate();
        this.updatedDate = folder.getUpdatedDate();
        if (folder.getMedia() != null) {
            this.media = folder.getMedia().stream()
                    .map(MediaModel::new)
                    .collect(Collectors.toList());
        }
        if (folder.getSubFolders() != null) {
            this.subFolders = folder.getSubFolders().stream()
                    .map(FolderBasic::new).collect(Collectors.toList());
        }
    }
}
