package kidev.vn.onlineshopping.model.folder;

import kidev.vn.onlineshopping.entity.Folder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderBasic {
    private Long id;
    private String name;
    private Date createdDate;
    private Date updatedDate;

    public FolderBasic(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
        this.createdDate = folder.getCreatedDate();
        this.updatedDate = folder.getUpdatedDate();
    }
}
