package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.Folder;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.media.MediaModel;
import kidev.vn.onlineshopping.service.FolderService;
import kidev.vn.onlineshopping.service.MediaService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("api/media")
@AllArgsConstructor
public class MediaController {
    public static Logger logger = LoggerFactory.getLogger(MediaController.class);

    private final MediaService mediaService;

    private final FolderService folderService;

    @PostMapping(value = "upload/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<List<MediaModel>> addMedia(
            @RequestParam @Nullable Long folderId,
            @ModelAttribute MultipartFile[] images
    ) {
        CommonResponse<List<MediaModel>> response = new CommonResponse<>();
        try {
            Folder folder = folderService.getFolderById(folderId);
            if (folderId != null && folder == null) {
                response.setStatusCode(Constants.RestApiReturnCode.NOT_FOUND);
                response.setError(Constants.RestApiReturnCode.NOT_FOUND_TXT);
                response.setMessage("cannot find folder");
            } else {
                List<MediaModel> mediaModels = mediaService.uploadImages(folder, images);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setOutput(mediaModels);
                response.setMessage("upload media success");
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("System error");
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createOrder", e);
        }
        return response;
    }

    @DeleteMapping("")
    public CommonResponse<?> deleteMedia(@RequestParam List<Long> ids) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            mediaService.deleteMedia(ids);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(null);
            response.setMessage("delete media success");
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("System error");
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createOrder", e);
        }
        return response;
    }
}
