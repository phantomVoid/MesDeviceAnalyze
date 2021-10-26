package com.mda.demo;

import com.mda.MdaConstant;
import com.mda.SSH2Util;
import com.mda.enums.FileTypeEnum;
import com.mda.enums.ResultEnum;
import com.mda.pojo.SnFilePojo;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HlhDemo {

    public static final String rootPath = MdaConstant.ROOT_PATH;
    public static final String fileType = FileTypeEnum.HLH.getKey();

    public static final String path0 = MdaConstant.REMOTE_HLH_PATH;
    public static final String path = MdaConstant.LOCAL_HLH_PATH + "\\20211020";

    private final static Logger log = Logger.getLogger(HlhDemo.class);

    public static void main(String[] args) throws Exception {
        log.info("this is HlhDemo start");
        /**
         * 获取其file对象
         */
        File file = new File(path);

        /**
         * 遍历path下的文件和目录，放在File数组中
         */
        File[] fs = file.listFiles();

        /**
         * 遍历File[]数组
         */
        assert fs != null;

        Date date1 = new Date();
        List<SnFilePojo> snFilePojoList = new ArrayList<>();
        for (File f : fs) {
            /**
             * 获取文件和目录名
             */
            String fileName = f.getName();
            if (!f.isDirectory() & fileName.endsWith("csv")) {

                String[] split = fileName.split("_");
                String sn = split[6];
                String result = "OK";
                String timestamp = split[0] + split[1] + split[2] + split[3] + split[4] + split[5];

                SnFilePojo snFilePojo = new SnFilePojo();
                snFilePojo.setSn(sn);
                snFilePojo.setResult(result);
                snFilePojo.setFileName(fileName);
                snFilePojo.setTimestamp(timestamp);
                snFilePojo.setPath(path);
                snFilePojo.setAbsolutePath(f.getAbsolutePath());

                snFilePojo.setRootPath(rootPath);
                snFilePojo.setFileType(fileType);
                if (ResultEnum.OK.getKey().equalsIgnoreCase(result)) {
                    snFilePojo.setErrorCode(null);
                } else {
                    snFilePojo.setErrorCode("B040");
                }
                snFilePojoList.add(snFilePojo);
            }
        }

        Date date2 = new Date();
        uploadFileList(path, snFilePojoList);
        log.info(String.valueOf(getDiff(date1, date2)));
    }

    public static void uploadFile(String path, String fileName) throws Exception {
        SSH2Util ssh = new SSH2Util(MdaConstant.hostName, MdaConstant.userName, MdaConstant.passWord, MdaConstant.port);
        try {
            ssh.putFile(path, fileName, path0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ssh.close();
    }

    public static void uploadFileList(String path, List<SnFilePojo> fileNameList) throws Exception {
        SSH2Util ssh = new SSH2Util(MdaConstant.hostName, MdaConstant.userName, MdaConstant.passWord, MdaConstant.port);
        try {
            ssh.putFileList(path, fileNameList, path0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ssh.close();
    }

    public static long getDiff(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long diffTime = time2 - time1;
        return diffTime;
    }
}
