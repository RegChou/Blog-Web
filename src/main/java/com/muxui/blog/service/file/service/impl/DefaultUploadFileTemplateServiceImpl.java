package com.muxui.blog.service.file.service.impl;

import com.muxui.blog.common.base.Constants;
import com.muxui.blog.common.base.ConstantsModels;
import com.muxui.blog.common.config.ConfigCache;
import com.muxui.blog.common.util.FileUtil;
import com.muxui.blog.service.file.factory.UploadFileFactory;
import com.muxui.blog.service.file.service.UploadFileTemplateService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author ou
 */
@Service
public class DefaultUploadFileTemplateServiceImpl implements UploadFileTemplateService, InitializingBean {

    @Override
    public boolean doCheck(final MultipartFile file) {
        return true;
    }

    @Override
    public String doSaveFileStore(final MultipartFile file) {
        final String filePath = ConstantsModels.getDefaultPath(ConfigCache.getConfig(Constants.DEFAULT_PATH));
        final String fileName = FileUtil.createSingleFileName(file.getOriginalFilename());
        try {
            final File destFile = new File(filePath);
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            file.transferTo(new File(filePath + fileName));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return ConfigCache.getConfig(Constants.DEFAULT_IMAGE_DOMAIN)+Constants.FILE_URL+fileName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UploadFileFactory.register(Constants.DEFAULT_TYPE, this);
    }


}
