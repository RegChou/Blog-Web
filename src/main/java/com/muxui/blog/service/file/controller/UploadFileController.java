package com.muxui.blog.service.file.controller;


import com.muxui.blog.common.base.Result;
import com.muxui.blog.common.base.ResultCode;
import com.muxui.blog.service.file.factory.UploadFileFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author ou
 */
@RestController
@RequestMapping("/file")
public class UploadFileController {

    @PostMapping("/v1/upload")
    public Result uploadFile(@RequestParam(value = "file") final MultipartFile file, final HttpServletRequest request) throws IOException {
        final String store = UploadFileFactory.getUploadFileService().saveFileStore(file);
        return new  Result(ResultCode.SUCCESS,store);
    }

}
