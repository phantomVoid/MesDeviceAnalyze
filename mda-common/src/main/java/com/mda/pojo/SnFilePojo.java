package com.mda.pojo;

/**
 * SnFilePojo
 * @author: phantomsaber
 * @version: 2021/10/21 5:14
 * @email: phantomsaber@foxmail.com
 **/
public class SnFilePojo {
    /**
     * 条码
     */
    public String sn;

    /**
     * 检测结果
     */
    public String result;

    /**
     * 文件名
     */
    public String fileName;

    /**
     * 文件路径(不含文件名)
     */
    public String path;

    /**
     * 绝对路径(包含文件名)
     */
    public String absolutePath;

    /**
     * 文件生成时间
     */
    public String timestamp;

    /**
     * 错误代码
     */
    public String errorCode;

    /**
     * 上传移动标志
     */
    public String moveFlag;

    /**
     * 解析后保存文件根目录
     */
    public String rootPath;

    /**
     * 文件类型
     */
    public String fileType;

    public SnFilePojo() {
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getMoveFlag() {
        return moveFlag;
    }

    public void setMoveFlag(String moveFlag) {
        this.moveFlag = moveFlag;
    }

    public String getSn() {
        return sn;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
