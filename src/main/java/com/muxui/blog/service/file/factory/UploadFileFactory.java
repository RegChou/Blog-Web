package com.muxui.blog.service.file.factory;



import com.muxui.blog.common.base.Constants;
import com.muxui.blog.common.config.ConfigCache;
import com.muxui.blog.service.file.service.UploadFileTemplateService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件存储实例工厂
 */
public class UploadFileFactory {

    private static final Map<String, UploadFileTemplateService> uploadFileServiceMap = new ConcurrentHashMap<>();

    /**
     * 获取工厂UploadFileTemplateService
     *
     * @return
     */
    public static UploadFileTemplateService getUploadFileService() {
        return uploadFileServiceMap.get(ConfigCache.getConfig(Constants.STORE_TYPE));
    }

    /**
     * 工厂注册
     *
     * @param storyType
     * @param uploadFileTemplateService
     */
    public static void register(final String storyType, final UploadFileTemplateService uploadFileTemplateService) {
        uploadFileServiceMap.put(storyType, uploadFileTemplateService);
    }

}
