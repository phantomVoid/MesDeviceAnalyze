package com.mda;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.*;
import com.mda.demo.HlhDemo;
import com.mda.enums.ResultEnum;
import com.mda.pojo.SnFilePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * java远程上传文件
 *
 * @author lenovo
 */
public class SSH2Util {
    private static final Logger log = LoggerFactory.getLogger(SSH2Util.class);

    private String host;

    private String user;

    private String password;

    private int port;

    private Session session;

    /**
     * 创建一个连接
     *
     * @param host     地址
     * @param user     用户名
     * @param password 密码
     * @param port     ssh2端口
     */
    public SSH2Util(String host, String user, String password, int port) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    private void initialSession() throws Exception {
        if (session == null) {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setUserInfo(new UserInfo() {

                @Override
                public String getPassphrase() {
                    return null;
                }

                @Override
                public String getPassword() {
                    return null;
                }

                @Override
                public boolean promptPassword(String arg0) {
                    return false;
                }

                @Override
                public boolean promptPassphrase(String arg0) {
                    return false;
                }

                @Override
                public boolean promptYesNo(String arg0) {
                    return true;
                }

                @Override
                public void showMessage(String arg0) {
                }

            });
            session.setPassword(password);
            session.connect();
        }
    }

    /**
     * 关闭连接
     *
     * @throws Exception
     */
    public void close() throws Exception {
        if (session != null && session.isConnected()) {
            session.disconnect();
            session = null;
        }
    }

    /**
     * 上传文件
     *
     * @param localPath  本地路径，若为空，表示当前路径
     * @param localFile  本地文件名，若为空或是“*”，表示目前下全部文件
     * @param remotePath 远程路径，若为空，表示当前路径，若服务器上无此目录，则会自动创建
     * @throws Exception
     */
    public void putFile(String localPath, String localFile, String remotePath) throws Exception {
        this.initialSession();
        Channel channelSftp = session.openChannel("sftp");
        channelSftp.connect();
        ChannelSftp c = (ChannelSftp) channelSftp;
        String remoteFile = null;
        if (remotePath != null && remotePath.trim().length() > 0) {
            try {
                c.mkdir(remotePath);
            } catch (Exception e) {
            }
            remoteFile = remotePath + "/.";
        } else {
            remoteFile = ".";
        }
        String file = null;
        if (localFile == null || localFile.trim().length() == 0) {
            file = "*";
        } else {
            file = localFile;
        }
        if (localPath != null && localPath.trim().length() > 0) {
            if (localPath.endsWith("/")) {
                file = localPath + file;
            } else {
                file = localPath + "/" + file;
            }
        }
        c.put(file, remoteFile);
        log.info(localFile + " 上传成功");
        channelSftp.disconnect();
    }

    public void putFileList(String localPath, List<SnFilePojo> localFiles, String remotePath) throws Exception {
        this.initialSession();
        Channel channelSftp = session.openChannel("sftp");
        channelSftp.connect();
        ChannelSftp c = (ChannelSftp) channelSftp;
        String remoteFile = null;
        if (remotePath != null && remotePath.trim().length() > 0) {
            try {
                c.mkdir(remotePath);
            } catch (Exception e) {

            }
            remoteFile = remotePath + "/.";
        } else {
            remoteFile = ".";
        }
        String file = null;

        if (CollectionUtils.isEmpty(localFiles) || localFiles.size() == 0) {
            log.info("该目录下没有可操作的文件");
            return;
        } else {
            for (SnFilePojo localFile : localFiles) {
                file = localFile.getFileName();
                try {
                    if (localPath != null && localPath.trim().length() > 0) {
                        if (localPath.endsWith("/")) {
                            file = localPath + file;
                        } else {
                            file = localPath + "/" + file;
                        }
                    }
                    c.put(file, remoteFile);
                    localFile.setMoveFlag(ResultEnum.OK.getKey());
                    log.info(JSON.toJSONString(localFile) + " 上传成功");
                } catch (SftpException e) {
                    localFile.setMoveFlag(ResultEnum.NG.getKey());
                }
            }
        }
        moveFileList(localPath, localFiles);
        channelSftp.disconnect();
    }

    public void moveFileList(String path, List<SnFilePojo> fileNameList) {
        File filePath = new File(path);
        if (filePath.exists()) {
            move(fileNameList);
            log.info("success");
        } else {
            log.info("error");
        }
    }

    public static void move(List<SnFilePojo> fileNameList) {

        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String format = sdf.format(new Date());
        for (SnFilePojo snFilePojo : fileNameList) {
            File startFile = new File(snFilePojo.getAbsolutePath());

            String endPath = snFilePojo.getRootPath() + File.separator + snFilePojo.getFileType() + File.separator + snFilePojo.getMoveFlag() + File.separator + format;
            File endDirection = new File(endPath);

            if (!endDirection.exists()) {
                endDirection.mkdirs();
            }

            File endFile = new File(endDirection + File.separator + startFile.getName());

            try {
                if (startFile.renameTo(endFile)) {
                    log.info("文件移动成功！目标路径：{" + endFile.getAbsolutePath() + "}");
                } else {
                    log.info("文件移动失败！起始路径：{" + startFile.getAbsolutePath() + "}");
                }
            } catch (Exception e) {
                log.info("文件移动出现异常！起始路径：{" + startFile.getAbsolutePath() + "}");
            }
        }
    }

    /**
     * command 命令
     *
     * @param command
     * @return
     * @throws Exception
     */
    public String runCommand(String command) throws Exception {
        // CommonUtil.printLogging("[" + command + "] begin", host, user);

        this.initialSession();
        InputStream in = null;
        InputStream err = null;
        BufferedReader inReader = null;
        BufferedReader errReader = null;
        int time = 0;
        String s = null;
        boolean run = false;
        StringBuffer sb = new StringBuffer();

        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(null);
        err = ((ChannelExec) channel).getErrStream();
        in = channel.getInputStream();
        channel.connect();
        inReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        errReader = new BufferedReader(new InputStreamReader(err, "UTF-8"));

        while (true) {
            s = errReader.readLine();
            if (s != null) {
                sb.append("error:" + s).append("\n");
            } else {
                run = true;
                break;
            }
        }
        while (true) {
            s = inReader.readLine();
            if (s != null) {
                sb.append("info:" + s).append("\n");
            } else {
                run = true;
                break;
            }
        }

        while (true) {
            if (channel.isClosed() || run) {
                // CommonUtil.printLogging("[" + command + "] finish: " +
                // channel.getExitStatus(), host, user);
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
            }
            if (time > 180) {
                // CommonUtil.printLogging("[" + command + "] finish2: " +
                // channel.getExitStatus(), host, user);
                break;
            }
            time++;
        }

        inReader.close();
        errReader.close();
        channel.disconnect();
        session.disconnect();
        log.info(sb.toString());
        return sb.toString();
    }
}