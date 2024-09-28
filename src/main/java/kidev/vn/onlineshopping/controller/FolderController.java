package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.Folder;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.folder.FolderModel;
import kidev.vn.onlineshopping.service.FolderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;

@RestController
@RequestMapping("api/folders")
@AllArgsConstructor
public class FolderController {
    public static Logger logger = LoggerFactory.getLogger(FolderController.class);

    private final FolderService folderService;

    @GetMapping("")
    public CommonResponse<FolderModel> getFolders(@RequestParam @Nullable Long id) {
        CommonResponse<FolderModel> response = new CommonResponse<>();
        try {
            FolderModel folderModel;
            if (id == null) {
                folderModel = folderService.getRootFolder();
            } else {
                folderModel = new FolderModel(folderService.getFolderById(id));
            }
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setMessage("get folders success");
            response.setOutput(folderModel);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("System error");
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createOrder", e);
        }
        return response;
    }

    @PostMapping("")
    public CommonResponse<FolderModel> createFolder(@RequestBody FolderModel request) {
        CommonResponse<FolderModel> response = new CommonResponse<>();
        try {
            FolderModel folderModel = folderService.create(request);
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setMessage("create folders success");
            response.setOutput(folderModel);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("System error");
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createOrder", e);
        }
        return response;
    }

    @PutMapping("")
    public CommonResponse<FolderModel> updateFolder(@RequestBody FolderModel request) {
        CommonResponse<FolderModel> response = new CommonResponse<>();
        try {
            Folder folder = folderService.getFolderById(request.getId());
            if (folder != null) {
                FolderModel folderModel = folderService.update(request);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setMessage("update folders success");
                response.setOutput(folderModel);
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.NOT_FOUND);
                response.setError(Constants.RestApiReturnCode.NOT_FOUND_TXT);
                response.setMessage("cannot find folder");
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("System error");
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createOrder", e);
        }
        return response;
    }
}
