package com.hd.img;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.SocketException;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.hd.img.HdTabFtpServer;

 
/**

 *******************************************************************************
 *     File Name         :  com.hz.sgl.fileUpload.dao.ContinueFTP.java
 *     Description(说明)	 :  FTP服务器操作类
 * -----------------------------------------------------------------------------
 * *****************************************************************************
 */
public class ContinueFTP { 
	 
    public enum UploadStatus {

        CREATE_DIRECTORY_FAIL, // 远程服务器相应目录创建失�?

        CREATE_DIRECTORY_SUCCESS, // 远程服务器闯将目录成�?

        UPLOAD_NEW_FILE_SUCCESS, // 上传新文件成�?

        UPLOAD_NEW_FILE_FAILED, // 上传新文件失�?

        FILE_EXITS, // 文件已经存在

        REMOTE_BIGGER_LOCAL, // 远程文件大于本地文件

        UPLOAD_FROM_BREAK_SUCCESS, // 断点续传成功

        UPLOAD_FROM_BREAK_FAILED, // 断点续传失败

        DELETE_REMOTE_FAILD; // 删除远程文件失败

    }
	 
    private FTPClient ftpClient = new FTPClient();  
       
    public ContinueFTP(){  
        //设置将过程中使用到的命令输出到控制台  
        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out))); 
    }  
       
    /**
     * @function: java编程中用于连接到FTP服务�?
     * @param hostname 主机�?
     * @param port 端口 
     * @param username 用户�?
     * @param password 密码 
     * @return 是否连接成功 
     * @throws IOException
    */
    public boolean connect(HdTabFtpServer ftp) throws IOException{  
        ftpClient.connect(ftp.getServer_ip(),Integer.parseInt(ftp.getFtp_port()));  
        if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){  
            if(ftpClient.login(ftp.getFtp_user_name(), ftp.getFtp_user_password())){  
                return true;  
            }  
        }  
        return false;  
     }  
    
    /**
     * @function 断开与远程服务器的连�? 
     * @return void
     * @throws IOException
    */
    public void disconnect() throws IOException{  
        if(ftpClient.isConnected()){  
            ftpClient.disconnect();  
        }  
    }  
    
    /**
     * @function  上传文件到FTP服务器，以流的方式写�?
     * @param is 本地文件的流 
     * @param remote 远程文件路径，使�?home/directory1/subdirectory/file.ext 按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构 
     * @param localsize 本地文件的大�?
     * @return 上传结果 
     * @throws IOException
    */
    public UploadStatus upload(InputStream is,String remote,long localsize) throws IOException{
        //设置PassiveMode传输  
        ftpClient.enterLocalPassiveMode();  
        //设置以二进制流的方式传输  
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
        UploadStatus result;  
        //对远程目录的处理  
        String remoteFileName = remote;  
        if(remote.contains("/")){  
            remoteFileName = remote.substring(remote.lastIndexOf("/")+1);  
            String directory = remote.substring(0,remote.lastIndexOf("/")+1);  
            if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(directory)){  
                //如果远程目录不存在，则�?归创建远程服务器目录  
                int start=0;  
                int end = 0;  
                if(directory.startsWith("/")){  
                    start = 1;  
                }else{  
                    start = 0;  
                }  
                end = directory.indexOf("/",start);  
                while(true){  
                    String subDirectory = remote.substring(start,end);  
                    if(!ftpClient.changeWorkingDirectory(subDirectory)){  
                        if(ftpClient.makeDirectory(subDirectory)){  
                            ftpClient.changeWorkingDirectory(subDirectory);  
                        }else {  
                            System.out.println("创建目录失败");  
                            return UploadStatus.CREATE_DIRECTORY_FAIL;  
                        }  
                    }  
                    start = end + 1;  
                    end = directory.indexOf("/",start);  
                    //�?���?��目录是否创建完毕  
                    if(end <= start){  
                        break;  
                    }  
                }  
            }  
        }  
           
        //�?��远程是否存在文件  
        FTPFile[] files = ftpClient.listFiles(remoteFileName);  
        if(files.length == 1){  
            long remoteSize = files[0].getSize();  
            if(remoteSize==localsize){  
                return UploadStatus.FILE_EXITS;  
            }else if(remoteSize > localsize){  
                return UploadStatus.REMOTE_BIGGER_LOCAL;  
            }  
            //尝试移动文件内读取指�?实现断点续传  
            if(is.skip(remoteSize)==remoteSize){  
                ftpClient.setRestartOffset(remoteSize);  
                if(ftpClient.storeFile(remote, is)){  
                    return UploadStatus.UPLOAD_FROM_BREAK_SUCCESS;  
                }  
            }  
            //如果断点续传没有成功，则删除服务器上文件，重新上�? 
            if(!ftpClient.deleteFile(remoteFileName)){  
                return UploadStatus.DELETE_REMOTE_FAILD;  
            }  
            if(ftpClient.storeFile(remote, is)){      
                result = UploadStatus.UPLOAD_NEW_FILE_SUCCESS;  
            }else{  
                result = UploadStatus.UPLOAD_NEW_FILE_FAILED;  
            }  
            is.close();  
        }else { 
            if(ftpClient.storeFile(new String(remoteFileName.getBytes("GBK"), "iso-8859-1"), is)){  
                result = UploadStatus.UPLOAD_NEW_FILE_SUCCESS;  
            }else{  
                result = UploadStatus.UPLOAD_NEW_FILE_FAILED;  
            }  
            is.close();  
        }  
        return result;   
    }
 
    /**
     * @function  循环创建目录，并且创建完目录后，设置工作目录为当前创建的目录�?
     * @param ftpPath:附件上传路径
     * @return boolean
     */
    public boolean mkDir(String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            // 将路径中的斜杠统�?
            char[] chars = ftpPath.toCharArray();
		    StringBuffer sbStr = new StringBuffer(256);
		    for (int i = 0; i < chars.length; i++) {
		        if ('\\' == chars[i]) {
		            sbStr.append('/');
		        } else {
		            sbStr.append(chars[i]);
	            }
            }
		    ftpPath = sbStr.toString();
		    if (ftpPath.indexOf('/') == -1) {
		       // 只有�?��目录
		        ftpClient.makeDirectory(new String(ftpPath.getBytes(),"iso-8859-1"));
		        ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(),"iso-8859-1"));
		    } else {
		        // 多层目录循环创建
		        String[] paths = ftpPath.split("/");
		        // String pathTemp = "";
		        for (int i = 0; i < paths.length; i++) {
		            ftpClient.makeDirectory(new String(paths[i].getBytes(),"iso-8859-1"));
		            ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
		       }
		    }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /** 
     * 上传文件到FTP服务器，支持断点续传 
     * @param local 本地文件名称，绝对路�?
     * @param remote 远程文件路径，使�?home/directory1/subdirectory/file.ext 按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构 
     * @return 上传结果 
     * @throws IOException 
     */  
    public UploadStatus upload(String local,String remotefile) throws IOException{ 
    	String remote="/"+remotefile;
        //设置PassiveMode传输  
        ftpClient.enterLocalPassiveMode();  
        //设置以二进制流的方式传输  
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
        UploadStatus result;  
        //�?��远程是否存在文件  
        FTPFile[] files = ftpClient.listFiles(remote);  
        if(files.length == 1){  
            long remoteSize = files[0].getSize();  
            File f = new File(local);  
            long localSize = f.length();  
            if(remoteSize==localSize){  
                return UploadStatus.FILE_EXITS;  
            }else if(remoteSize > localSize){  
                return UploadStatus.REMOTE_BIGGER_LOCAL;  
            }  
              
            //尝试移动文件内读取指�?实现断点续传  
            InputStream is = new FileInputStream(f);  
            if(is.skip(remoteSize) == remoteSize){  
                ftpClient.setRestartOffset(remoteSize);  
                if(ftpClient.storeFile(remote, is)){  
                    return UploadStatus.UPLOAD_FROM_BREAK_SUCCESS;  
                }  
            }  
              
            //如果断点续传没有成功，则删除服务器上文件，重新上�? 
            if(!ftpClient.deleteFile(remote)){  
                return UploadStatus.DELETE_REMOTE_FAILD;  
            }  
            is = new FileInputStream(f);  
            if(ftpClient.storeFile(remote, is)){      
                result = UploadStatus.UPLOAD_NEW_FILE_SUCCESS;  
            }else{  
                result = UploadStatus.UPLOAD_NEW_FILE_FAILED;  
            }  
            is.close();  
        }else {  
            InputStream is = new FileInputStream(local);  
            if(ftpClient.storeFile(new String(remote.getBytes("GBK"), "iso-8859-1") , is)){  
                result = UploadStatus.UPLOAD_NEW_FILE_SUCCESS;  
            }else{  
                result = UploadStatus.UPLOAD_NEW_FILE_FAILED;  
            }  
            is.close();  
        }  
        return result;  
    }  
    
    /**
     * @function  删除FTP服务器上的文�?
     * @param deletePath 要删除的文件名，即远程服务器上的文件�?
     * @throws IOException
     */
    public void deleteFile(String deletePath){
        try {
            ftpClient.deleteFile(deletePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @param hostname 主机�?
     * @param port 端口 
     * @param username 用户�?
     * @param password 密码 
     * @return 是否连接成功 
     * @throws IOException
    */
    public boolean getconnect(HdTabFtpServer ftp) {  
        try {
			ftpClient.connect(ftp.getServer_ip(),Integer.parseInt(ftp.getFtp_port()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}  
        if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){  
            try {
				if(ftpClient.login(ftp.getFtp_user_name(), ftp.getFtp_user_password())){  
				    return true;  
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}  
        }  
        return false;  
     }     
    
}