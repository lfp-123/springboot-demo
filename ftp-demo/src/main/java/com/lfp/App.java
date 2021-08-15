package com.lfp;

import com.lfp.ftp.FtpFileUtils;

import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException {
        String hostname = "192.168.202.129";
        String username = "zhangfaquan";
        String password = "linss999...";
        int port = 21;
        int sport = 22;
        String str = "D:\\20210606\\20210606230046_741.dump";
        String localPath= "D:\\20210606\\";
        String workingPath = "/home/zhangfaquan/temp/";
        String saveName = "wenben2.dump";
        FtpFileUtils ftpFileUtils = new FtpFileUtils();
        boolean connect = ftpFileUtils.connect(hostname, port, username, password);
        if(connect){
            ftpFileUtils.upload(workingPath,str,saveName);
        }
        ftpFileUtils.downloadSingleFile(localPath,workingPath+saveName);


    }

}
