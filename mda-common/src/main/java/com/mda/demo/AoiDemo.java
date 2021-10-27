package com.mda.demo;

import com.alibaba.fastjson.JSON;
import com.mda.MdaConstant;
import com.mda.SSH2Util;
import com.mda.enums.FileTypeEnum;
import com.mda.enums.ResultEnum;
import com.mda.pojo.SnFilePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class AoiDemo {

    public static final String rootPath = MdaConstant.ROOT_PATH;
    public static final String fileType = FileTypeEnum.AOI.getKey();

    public static final String path0 = MdaConstant.REMOTE_AOI_PATH;
    public static String path = MdaConstant.LOCAL_AOI_PATH;

    private static final Logger log = LoggerFactory.getLogger(AoiDemo.class);

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        Date date1 = new Date();
        String AOI_DIR = sdf.format(date1);
        AOI_DIR = "20211020";
        path = path + "\\" + AOI_DIR;

        log.info("开始读取" + path + "路径下的AOI文件信息...");
        /**
         * 获取其file对象
         */
        File file = new File(path);

        /**
         * 遍历path下的文件和目录，放在File数组中
         */
        File[] fs = file.listFiles();

        List<SnFilePojo> snFilePojoList = new ArrayList<>();

        if (fs == null) {
            log.info("该路径下" + path + ",文件不存在");
            return;
        }

        /**
         * 遍历File[]数组
         */
        for (File f : fs) {
            /**
             * 获取文件和目录名
             */
            String fileName = f.getName();

            if (f.isDirectory()) {
                String sn = fileName;
                File[] revieweds = f.listFiles();

                if (revieweds == null) {
                    log.info("该路径下" + path + "\\" + fileName + ",文件不存在");
                    return;
                }

                List<Map<String, Object>> lMaps = new ArrayList<>();
                String serial = null;

                for (File reviewed : revieweds) {
                    Map<String, Object> maps = new HashMap<String, Object>();

                    log.info(JSON.toJSONString(reviewed));
                    if (reviewed.isFile() && reviewed.getName().endsWith(".xml")) {
                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document document = docBuilder.parse(reviewed);
                        Element element = document.getDocumentElement();
                        NodeList childNodes = element.getChildNodes();

                        for (int i = 0; i < childNodes.getLength(); i++) {
                            Node node1 = childNodes.item(i);
                            NodeList nodeList = node1.getChildNodes();

                            for (int l = 0; l < nodeList.getLength(); l++) {
                                Node node2 = nodeList.item(l);

                                //根节点下名称为 “BoardInfo” 的子节点
                                if (node1.getNodeName().equalsIgnoreCase("BoardInfo")) {
                                    //子节点里所有的属性
                                    if (node2.getNodeName().equalsIgnoreCase("Model")) {
                                        maps.put("model", node2.getTextContent().substring(0, 20));
                                    } else if (node2.getNodeName().equalsIgnoreCase("SerialNo")) {
                                        maps.put("serial_no", node2.getTextContent());
                                        serial = node2.getTextContent();
                                    } else if (node2.getNodeName().equalsIgnoreCase("ModuleCount")) {
                                        maps.put("module_count", node2.getTextContent());
                                    } else if (node2.getNodeName().equalsIgnoreCase("IsGood")) {
                                        maps.put("is_good", node2.getTextContent());
                                    }
                                }

                                //根节点下名称为 “DefectInfo” 的子节点
                                if (node1.getNodeName().equalsIgnoreCase("DefectInfo")) {
                                    //子节点里所有的属性
                                    if (node2.getNodeName().equalsIgnoreCase("InspStartTime")) {
                                        maps.put("start_time", node2.getTextContent());
                                    } else if (node2.getNodeName().equalsIgnoreCase("InspEndTime")) {
                                        maps.put("end_time", node2.getTextContent());
                                    }
                                }

                                //根节点下名称为 “ModuleResult” 的子节点
                                if (node1.getNodeName().equalsIgnoreCase("ModuleResult")) {
                                    Map<String, Object> map = new HashMap<>();
                                    //子节点里所有的属性
                                    if (node2.getNodeName().equalsIgnoreCase("Module")) {
                                        if (lMaps.size() <= node2.getNodeName().length()) {
                                            String isSkiped = node2.getAttributes().getNamedItem("IsSkiped").getTextContent();
                                            String modueId = node2.getAttributes().getNamedItem("ModuleId").getTextContent();
                                            String result = node2.getAttributes().getNamedItem("Result").getTextContent();
                                            maps.put("isSkiped", isSkiped);
                                            maps.put("modueId", modueId);
                                            maps.put("result", result);
                                            lMaps.add(maps);
                                        }
                                    }
                                }
                            }
                        }

                        log.info("maps: >>> " + JSON.toJSONString(maps));
                        log.info("lMaps: >>> " + JSON.toJSONString(lMaps));

                        if (maps.get("serial_no") != null || maps.get("serial_no") != "" && maps.get("is_good") == "1") {
                            log.info(maps.get("serial_no").toString() + "解析成功...");
                        }
                    }
                }
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
