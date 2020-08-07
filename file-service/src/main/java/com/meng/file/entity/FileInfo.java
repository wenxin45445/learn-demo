package com.meng.file.entity;

/**
 * 文件信息
 */
public class FileInfo {
    // 文件名
    private String fileName;
    // 路径
    private String filePath;
    // 请求类型
    private String contentType;
    // 文件后缀
    private String suffix;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
