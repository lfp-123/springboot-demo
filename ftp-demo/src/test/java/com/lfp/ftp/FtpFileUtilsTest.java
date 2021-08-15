package com.lfp.ftp;


import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * @author ${林锋鹏}
 * @Title: FtpFileUtilsTest
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/15 11:28
*/
public class FtpFileUtilsTest {
    String hostname = "192.168.202.129";
    String username = "zhangfaquan";
    String password = "linss999...";
    int port = 21;
    String str = "D:\\20210606\\20210606230046_741.dump";
    String localPath= "D:\\20210606\\";
    String workingPath = "/home/zhangfaquan/temp/";
    String saveName = "wenben2.dump";
    FtpFileUtils ftpFileUtils;

    @Before
    public void before(){
        ftpFileUtils = new FtpFileUtils();
    }
    @Test
    public void connect() throws FileNotFoundException {

      ftpFileUtils.connect(hostname, port, username, password);


    }

    @Test
    public void upload() throws FileNotFoundException {
        ftpFileUtils.upload(workingPath,str,saveName);
    }


    @Test
    public void disconnect() {
        ftpFileUtils.disconnect();
    }

    @Test
    public void downloadSingleFile() {
        ftpFileUtils.downloadSingleFile(localPath,workingPath+saveName);
    }
}
