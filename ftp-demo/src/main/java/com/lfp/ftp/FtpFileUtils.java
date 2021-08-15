package com.lfp.ftp;

import com.sun.deploy.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FtpFileUtils
 * @Description: TODO
 * @Author lfp
 * @Date 2020/10/10
 * @Version V1.0
 **/

public class FtpFileUtils {
private static Logger logger = Logger.getLogger(FtpFileUtils.class);
    private  FTPClient ftpClient;

    /**
     * 测试是否能连接
     * @return 返回真则能连接
     */
    public  boolean connect (String ip,int port,String username,String password) {
        boolean flag = false;
        try {
            ftpClient = new FTPClient();
            //ftp初始化的一些参数
            ftpClient.connect(ip, port);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("UTF-8");
            if (ftpClient.login(username, password)) {
                System.out.println("连接ftp成功");
                flag = true;
            } else {
                System.out.println("连接ftp失败，可能用户名或密码错误!");
                try {
                    disconnect();
                } catch (Exception e) {
                    System.out.println("ftp关闭失败！");
                }
            }
        } catch (IOException e) {
            System.out.println("连接失败，可能ip或端口错误");
        }
        return flag;
    }

    /**
     * 上传
     *
     * @param workingPath 服务器的工作目
     * @param localFile 本地要上传的文件
     * @param saveName    设置上传之后的文件名
     * @return
     */
    public  boolean upload(String workingPath, String localFile, String saveName) throws FileNotFoundException {
        boolean flag = false;
        InputStream fileInputStream = new FileInputStream(new File(localFile));
            try {
                // Use passive mode to pass firewalls.
                ftpClient.enterLocalPassiveMode();
                //2 检查工作目录是否存在
                if (ftpClient.changeWorkingDirectory(workingPath)) {
                    // 3 检查是否上传成功
                    if (storeFile(saveName, fileInputStream)) {
                        System.out.println("文件上传成功！！！");
                        flag = true;
                    }
                }else {
                    System.out.println("目录不存在");
                }
            } catch (IOException e) {
                System.out.println("工作目录不存在");
                disconnect();
            }

        return flag;
    }

    /**
     * 上传文件
     *
     * @param saveName        ftp上要保存的文件名
     * @param fileInputStream 要上传的文件流
     * @return
     */
    public  boolean storeFile(String saveName, InputStream fileInputStream) {
        boolean flag = false;
        try {
            if (ftpClient.storeFile(saveName, fileInputStream)) {
                flag = true;
                System.out.println(("{}文件上传成功"+saveName));
            }
        } catch (IOException e) {
            System.out.println("{}文件上传失败"+saveName+e.getMessage());
            disconnect();
        }
        return flag;
    }
    /**
     * @MethodName: forceMkdir
     * @Description: 创建文件夹
     * @Param: [remotePath]
     * @Return: boolean
     * @Author: zheng
     * @Date: 2019/12/10
     **/
    public  boolean forceMkdir(String remotePath) throws Exception {
        boolean res = false;
        res = ftpClient.makeDirectory(remotePath);
        return res;
    }

    /**
     * @MethodName: exists
     * @Description: 判断路径是否存在
     * @Param: [remotePath]
     * @Return: boolean
     * @Author: zheng
     * @Date: 2019/12/10
     **/
    public  boolean exists(String remotePath) {
        boolean isExists = true;
        try {
             isExists = ftpClient.changeWorkingDirectory(remotePath);

        } catch (Exception e) {
            isExists = false;
        }
        return isExists;
    }

    /**
     * 断开连接
     *
     * @param
     * @throws Exception
     */
    public  void disconnect() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
            }
        }
    }
    /**
     * 下载单个文件
     *
     * @param ：已连接的ftp服务器
     * @param localdirectory：本地目录
     * @param remotepath：远程文件路径
     */
    public void downloadSingleFile(String localdirectory, String remotepath) {
        /* *如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() ) {
            logger.info(">>>>>FTP服务器连接已经关闭或者连接无效*********");
            return;
        }
        File file=new File(localdirectory);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        try {
            String workDir = remotepath.substring(0, remotepath.lastIndexOf("/"));
            /*  *文件下载前，FTPClient工作目录必须切换到文件所在的目录，否则下载失败
             * "/" 表示用户根目录*/
            ftpClient.changeWorkingDirectory(workDir);
            /*  *没有对应路径时，FTPFile[] 大小为0，不会为null*/
            FTPFile[] ftpFiles = ftpClient.listFiles(remotepath);
            FTPFile ftpFile = null;
            if (ftpFiles.length >= 1) {
                ftpFile = ftpFiles[0];
            }
            //isFile ()
            //确定该文件是否是常规文件。
            if (ftpFile != null && ftpFile.isFile()) {
                File localFile = new File(localdirectory + "/" + ftpFile.getName());
                OutputStream outputStream = new FileOutputStream(localFile);
                /*  *下载指定的 FTP 文件 到本地
                 * 1)注意只能是文件，不能直接下载整个目录
                 * 2)如果文件本地已经存在，默认会重新下载
                 * 3)下载文件之前，ftpClient 工作目录必须是下载文件所在的目录
                 * 4)下载成功返回 true，失败返回 false
                 */
                boolean flag = ftpClient.retrieveFile(ftpFile.getName(), outputStream);
                outputStream.flush();
                outputStream.close();
                if (flag) {
                    System.out.println(">>>>>FTP服务器文件下载完毕*********" + ftpFile.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历目录，的到文件名称、大小信息，返回map
     *
     * @param ftpClient：已连接的ftp服务器
     * @param remotePath：远程路径
     * @param map：map集合，存储文件的绝对路径名和文件大小
     * @param regStr：正则表达式，筛选出应该被下载的文件
     * @return
     */
    public static Map<String, Long> loopServerPath(FTPClient ftpClient, String remotePath, Map<String, Long> map, String regStr) {
        /* *如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            logger.info("ftp 连接已经关闭或者连接无效......");
            return map;
        }
        try {
            ftpClient.changeWorkingDirectory(remotePath);
            FTPFileFilter filter = file -> {
                if (file.isDirectory()) {
                    return true;
                } else {
                    if (file.getName().endsWith(regStr)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            FTPFile[] ftpFiles = ftpClient.listFiles(remotePath, filter);
            /*for (FTPFile ff : ftpFiles) {
                logger.info(ff.getName());
            }*/
            if (ftpFiles != null && ftpFiles.length > 0) {
                for (FTPFile ftpFile : ftpFiles) {
                    if (ftpFile.isFile()) {
                        String filepathName = remotePath + "/" + ftpFile.getName();
                        Long fileSize = ftpFile.getSize();
                        map.put(filepathName, fileSize);
                    } else if (ftpFile.isDirectory()) {
                        loopServerPath(ftpClient, remotePath + "/" + ftpFile.getName(), map, regStr);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Long> loopLocalPath(String LocalPath, Map<String, Long> map) {
        File ff = new File(LocalPath);
        File[] listFiles = ff.listFiles();
            /*for (FTPFile ff : ftpFiles) {
                logger.info(ff.getName());
            }*/
        try {
            if (listFiles != null && listFiles.length > 0) {
                for (File ftpFile : listFiles) {
                    if (ftpFile.isFile()) {
                        String filepathName = LocalPath + "/" + ftpFile.getName();
                        Long fileSize = ftpFile.length();
                        map.put(filepathName, fileSize);
                    } else if (ftpFile.isDirectory()) {
                        loopLocalPath(LocalPath + "/" + ftpFile.getName(), map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 下载整个目录下的文件
     *
     *
     * @param LocalDirectory：本地目录
     * @param map：map集合，存储文件的绝对路径名和文件大小
     */
    public  void downloadDirFile(String LocalDirectory, Map<String, Long> map) {
        for (String filename : map.keySet()) {
            logger.info("准备下载的服务器文件：" + filename);
            downloadSingleFile(LocalDirectory, filename);
        }
    }

    /**
     * 下载后的检验，检验下载前后的文件大小
     *
     * @param beforeFileMap：下载前的map集合
     * @param afterFileMap：下载后的map集合
     * @return
     */
    public  boolean fileCheck(Map<String, Long> beforeFileMap, Map<String, Long> afterFileMap) {
        boolean flag = false;
        Map<String, Long> Map1 = new HashMap<>();
        Map<String, Long> Map2 = new HashMap<>();
        try {
            for (Map.Entry<String, Long> entry : beforeFileMap.entrySet()) {
                Map1.put(entry.getKey().substring(entry.getKey().lastIndexOf("/") + 1), entry.getValue());
            }
          /*  for (Map.Entry<String, Long> entry : Map1.entrySet()) {
                logger.info("文件名 ：" + entry.getKey() + " 文件大小：" + entry.getValue());
            }*/
            for (Map.Entry<String, Long> entry : afterFileMap.entrySet()) {
                Map2.put(entry.getKey().substring(entry.getKey().lastIndexOf("/") + 1), entry.getValue());
            }
           /* for (Map.Entry<String, Long> entry : Map2.entrySet()) {
                logger.info("文件名 ：" + entry.getKey() + " 文件大小：" + entry.getValue());
            }*/
            for (Map.Entry<String, Long> entry1 : Map1.entrySet()) {
                Long m1value = entry1.getValue() == null ? new Long(0) : entry1.getValue();
                Long m2value = Map2.get(entry1.getKey()) == null ? new Long(1) : Map2.get(entry1.getKey());
                if (m1value.equals(m2value)) {//若两个map中相同key对应的value相等
                    flag = true;
                    logger.info("下载成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单个文件
     *
     * @param ftpClient：已连接的ftp服务器
     * @param deleteFilename：要被删除的文件名
     * @return
     */
    public  void deleteFile(FTPClient ftpClient, String deleteFilename) {
        /* *如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            logger.info(">>>>>FTP服务器连接已经关闭或者连接无效*****放弃文件上传****");
            return;
        }
        try {
            /* * 尝试改变当前工作目录到 deleteFiles
             * 1）changeWorkingDirectory：变更FTPClient当前工作目录，变更成功返回true，否则失败返回false
             * 2）如果变更工作目录成功，则表示 deleteFiles 为服务器已经存在的目录
             * 3）否则变更失败，则认为 deleteFiles 是文件，是文件时则直接删除
             */
            boolean changeFlag = ftpClient.changeWorkingDirectory(deleteFilename);
            if (changeFlag) {
                /* *当被删除的是目录时*/
                FTPFile[] ftpFiles = ftpClient.listFiles();
                for (FTPFile ftpFile : ftpFiles) {
                    logger.info("----------------::::" + ftpClient.printWorkingDirectory());
                    if (ftpFile.isFile()) {
                        boolean deleteFlag = ftpClient.deleteFile(ftpFile.getName());
                        if (deleteFlag) {
                            logger.info(">>>>>删除服务器文件成功****" + ftpFile.getName());
                        } else {
                            logger.info(">>>>>删除服务器文件失败****" + ftpFile.getName());
                        }
                    } else {
                        /* *printWorkingDirectory：获取 FTPClient 客户端当前工作目录
                         * 然后开始迭代删除子目录
                         */
                        String workingDirectory = ftpClient.printWorkingDirectory();
                        deleteFile(ftpClient, workingDirectory + "/" + ftpFile.getName());
                    }
                }
                /* *printWorkingDirectory：获取 FTPClient 客户端当前工作目录
                 * removeDirectory：删除FTP服务端的空目录，注意如果目录下存在子文件或者子目录，则删除失败
                 * 运行到这里表示目录下的内容已经删除完毕，此时再删除当前的为空的目录，同时将工作目录移动到上移层级
                 * */
              /*  String workingDirectory = ftpClient.printWorkingDirectory();
                ftpClient.removeDirectory(workingDirectory);*/
                ftpClient.changeToParentDirectory();
            } else {
                /* *deleteFile：删除FTP服务器上的文件
                 * 1）只用于删除文件而不是目录，删除成功时，返回 true
                 * 2）删除目录时无效,方法返回 false
                 * 3）待删除文件不存在时，删除失败，返回 false
                 * */
                boolean deleteFlag = ftpClient.deleteFile(deleteFilename);
                if (deleteFlag) {
                    logger.info(">>>>>删除服务器文件成功****" + deleteFilename);
                } else {
                    logger.info(">>>>>删除服务器文件失败****" + deleteFilename);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件移动
     *
     * @param originalFilePath：原来的文件或目录
     * @param targetFilePath：目标目录
     */
    public  void moveLocalFile(String originalFilePath, String targetFilePath) {
        File originalFile = new File(originalFilePath);
        File targetFile = new File(targetFilePath);
        if (originalFile == null || !originalFile.exists()) {
            logger.info(">>>>>待复制文件、目录为空或者不存在*****放弃文件复制****");
            return;
        }
        try {
            if (originalFile.isDirectory()) {
                FileUtils.copyDirectory(originalFile, targetFile);
                FileUtils.deleteDirectory(originalFile);
            } else if (originalFile.isFile()) {
                FileUtils.copyFileToDirectory(originalFile, targetFile, false);
                FileUtils.deleteDirectory(originalFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void moveRemoteFile(FTPClient ftpClient, String originalFilePath, String targetFilePath) {
        /* *如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected()) {
            logger.info(">>>>>FTP服务器连接已经关闭或者连接无效*********");
            return;
        }
        try {
            if (targetFilePath == null || !new File(targetFilePath).exists()) {
                ftpClient.makeDirectory(targetFilePath);
            }
            String workDir = originalFilePath.substring(0, originalFilePath.lastIndexOf("/"));
            String filename = originalFilePath.substring(originalFilePath.lastIndexOf("/") + 1);
            ftpClient.changeWorkingDirectory(workDir);
            ftpClient.rename(workDir + "/" + filename, targetFilePath + "/" + filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void moveDirFile(FTPClient ftpClient, Map<String, Long> map, String targetFilePath) {
        for (String filename : map.keySet()) {
            logger.info("准备移动的的服务器文件：" + filename);
            moveRemoteFile(ftpClient, filename, targetFilePath);
        }
    }



}
