package com.mda.analyze.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_file_parsing_aoi
 * @author 
 */
@Data
public class TFileParsingAoi implements Serializable {
    /**
     * 大板号
     */
    private String serialNo;

    /**
     * id
     */
    private String id;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 组织机构
     */
    private String dataAuth;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改时间
     */
    private Date editTime;

    /**
     * 修改人
     */
    private String editUser;

    /**
     * 版面
     */
    private String model;

    /**
     * 小版数量
     */
    private String moduleCount;

    /**
     * 状态
     */
    private String isGood;

    /**
     * 进入时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    private static final long serialVersionUID = 1L;
}