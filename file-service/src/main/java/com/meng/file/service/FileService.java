package com.meng.file.service;

import com.meng.file.entity.FileInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * 文件处理服务
 */
@Service
public class FileService {
    /**
     * 根据文件路径 名称  指定流类型，下载文件
     */
    public void downloadFile(FileInfo fileInfo, HttpServletResponse response) throws Exception {
        if(fileInfo == null){
            return;
        }

        if (StringUtils.isEmpty(fileInfo.getFilePath())) {
            return;
        }

        if (StringUtils.isEmpty(fileInfo.getFileName())) {
            return;
        }

        if (StringUtils.isEmpty(fileInfo.getContentType())) {
            fileInfo.setContentType("application/octet-stream;charset=utf-8");
        }

        File file = new File(fileInfo.getFilePath() + File.separator + fileInfo.getFileName());
        if (!file.exists() || !file.isFile()) {
            return ;
        }
        response.setHeader("Set-Cookie","cookiename=cookievalue; path=/; Domain=domainvaule; Max- age=seconds; HttpOnly");
        try(FileInputStream in = new FileInputStream(file);
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream())){
            response.setContentType(fileInfo.getContentType());
            int b;
            byte[] by = new byte[1024];
            while ((b = in.read(by)) != -1) {
                toClient.write(by, 0, b);
            }
            toClient.flush();
        }
    }
}
