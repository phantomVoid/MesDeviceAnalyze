package com.mda.pojo;

import com.alibaba.fastjson.util.TypeUtils;
import com.mda.annotation.ConfigIsRequired;

/**
 * 配置文件POJO
 * @author: phantomsaber
 * @version: 2021/10/26 18:46
 * @email: phantomsaber@foxmail.com
 **/

public class ConfigPojo {
    /**
     * 本地SPI文件路径
     */
    @ConfigIsRequired(value = "Y", msg = "本地SPI文件路径不能为空")
    public String LOCAL_SPI_PATH;

    /**
     * 本地回流炉文件路径
     */
    @ConfigIsRequired(value = "Y", msg = "本地回流炉文件路径不能为空")
    public String LOCAL_HLH_PATH;

    /**
     * 本地AOI文件路径
     */
    @ConfigIsRequired(value = "Y", msg = "本地AOI文件路径不能为空")
    public String LOCAL_AOI_PATH;

    /**
     * 远程SPI文件路径
     */
    @ConfigIsRequired(value = "Y", msg = "远程SPI文件路径不能为空")
    public String REMOTE_SPI_PATH;

    /**
     * 远程回流炉文件路径
     */
    @ConfigIsRequired(value = "Y", msg = "远程回流炉文件路径不能为空")
    public String REMOTE_HLH_PATH;

    /**
     * 远程AOI文件路径
     */
    @ConfigIsRequired(value = "Y", msg = "远程AOI文件路径不能为空")
    public String REMOTE_AOI_PATH;

    /**
     * 本地日志ROOT路径
     */
    @ConfigIsRequired(value = "Y", msg = "本地日志ROOT路径不能为空")
    public String ROOT_PATH;

    /**
     * 配置类型 FileTypeEnum
     */
    @ConfigIsRequired(value = "Y", msg = "配置类型CONFIG_TYPE不能为空")
    public String CONFIG_TYPE;

    public ConfigPojo() {
        TypeUtils.compatibleWithJavaBean = true;
    }

    public String getLOCAL_SPI_PATH() {
        return LOCAL_SPI_PATH;
    }

    public void setLOCAL_SPI_PATH(String LOCAL_SPI_PATH) {
        this.LOCAL_SPI_PATH = LOCAL_SPI_PATH;
    }

    public String getLOCAL_HLH_PATH() {
        return LOCAL_HLH_PATH;
    }

    public void setLOCAL_HLH_PATH(String LOCAL_HLH_PATH) {
        this.LOCAL_HLH_PATH = LOCAL_HLH_PATH;
    }

    public String getLOCAL_AOI_PATH() {
        return LOCAL_AOI_PATH;
    }

    public void setLOCAL_AOI_PATH(String LOCAL_AOI_PATH) {
        this.LOCAL_AOI_PATH = LOCAL_AOI_PATH;
    }

    public String getREMOTE_SPI_PATH() {
        return REMOTE_SPI_PATH;
    }

    public void setREMOTE_SPI_PATH(String REMOTE_SPI_PATH) {
        this.REMOTE_SPI_PATH = REMOTE_SPI_PATH;
    }

    public String getREMOTE_HLH_PATH() {
        return REMOTE_HLH_PATH;
    }

    public void setREMOTE_HLH_PATH(String REMOTE_HLH_PATH) {
        this.REMOTE_HLH_PATH = REMOTE_HLH_PATH;
    }

    public String getREMOTE_AOI_PATH() {
        return REMOTE_AOI_PATH;
    }

    public void setREMOTE_AOI_PATH(String REMOTE_AOI_PATH) {
        this.REMOTE_AOI_PATH = REMOTE_AOI_PATH;
    }

    public String getROOT_PATH() {
        return ROOT_PATH;
    }

    public void setROOT_PATH(String ROOT_PATH) {
        this.ROOT_PATH = ROOT_PATH;
    }

    public String getCONFIG_TYPE() {
        return CONFIG_TYPE;
    }

    public void setCONFIG_TYPE(String CONFIG_TYPE) {
        this.CONFIG_TYPE = CONFIG_TYPE;
    }
}
