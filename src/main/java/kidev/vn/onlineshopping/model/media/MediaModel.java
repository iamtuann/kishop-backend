package kidev.vn.onlineshopping.model.media;

import kidev.vn.onlineshopping.entity.Media;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaModel {
    private Long id;
    private String name;
    private Long folderId;
    private String url;
    private String publicId;
    private String contentType;
    private Date uploadDate;

    public MediaModel(Media media) {
        this.id = media.getId();
        this.name = media.getName();
        this.folderId = media.getFolder() == null ? null : media.getFolder().getId();
        this.url = media.getUrl();
        this.publicId = media.getPublicId();
        this.contentType = media.getContentType();
        this.uploadDate = media.getUploadDate();
    }
}
