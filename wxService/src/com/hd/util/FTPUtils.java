package com.hd.util;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;

/**
 * ftp4j封装工具类.
 * 
 * @version 0.1
 * @author weep
 * 
 */

public class FTPUtils {
	private FTPClient client = null;
	// private String path = "C:/";
	private String tempPath = "";

	/** ******************************* 开始各种构造函数 ****************************** */
	public FTPUtils() {
	}

	public FTPUtils(String ip) {
		this.connectFtp(ip, 21, "anonymous", "anonymous");
	}

	public FTPUtils(String ip, int prot) {
		this.connectFtp(ip, prot, "anonymous", "anonymous");
	}

	public FTPUtils(String ip, String username, String password) {
		this.connectFtp(ip, 21, username, password);
	}

	public FTPUtils(String ip, int prot, String username, String password) {
		this.connectFtp(ip, prot, username, password);
	}

	/** ******************************* 结束各种构造函数 ****************************** */

	/** ******************************* 开始各种工具方法 ****************************** */

	/**
	 * 根据字符串创建层级文件夹
	 * 
	 * @param dirs
	 * @return
	 */
	public boolean createDirs(String dirs) {
		String[] dirArray = dirs.replace("\\", "/").split("/");
		String dirsT = "/";
		for (String dir : dirArray) {
			if (!"".equals(dir) && !ftpFileExists(dirsT + dir)) {
				try {
					client.createDirectory(dirsT + dir);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			dirsT += dir + "/";
		}

		return true;
	}

	/**
	 * 文件存在与否
	 * 
	 * @param filePath
	 * @return
	 */
	public Boolean ftpFileExists(String filePath) {
		String[] fileNames = null;
		try {
			client.changeDirectory(filePath.substring(0,
					filePath.lastIndexOf("/")));
			fileNames = client.listNames();
		} catch (Exception e) {
			return false;
		}
		return ArrayUtils.contains(fileNames,
				filePath.substring(filePath.lastIndexOf("/") + 1));
	}

	/**
	 * 文件存在与否
	 * 
	 * @param filePath
	 * @return
	 */
	private Boolean ftpFileDirsExists(String filePath) {
		String[] fileNames = null;
		try {
			client.changeDirectory(filePath.substring(0,
					filePath.lastIndexOf("/")));
			fileNames = client.listNames();
		} catch (Exception e) {
			return false;
		}
		return fileNames.length >= 0;
	}

	/**
	 * 链接ftp服务器.
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 */
	private void connectFtp(String ip, int port, String username,
			String password) {
		client = new FTPClient();
		System.out
				.println("---------------------FTP开始链接-----------------------");
		System.out.println("---------------------" + ip + ":" + port
				+ "---------------------");

		try {
			// String tempStr = UUID.randomUUID().toString();
			// File file = new File(path+tempStr);
			// file.mkdirs();
			// this.tempPath = file.getAbsolutePath();
			client.connect(ip, port);
			client.login(username, password);
		} catch (Exception e) {
			System.out
					.println("---------------------FTP链接失败-----------------------");
		}

	}

	/** ******************************* 结束各种工具方法 ****************************** */

	/** ******************************* 开始各种调用API ****************************** */
	/**
	 * 取得服务器链接状态.
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return client == null ? false : client.isConnected();
	}

	/**
	 * 使用完毕关闭ftp链接.
	 */
	public void disconnect() {
		if (client != null && client.isConnected()) {
			try {
				client.disconnect(true);
				System.out
						.println("---------------------FTP断开链接-----------------------");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					FileUtils.deleteDirectory(new File(tempPath));
				} catch (IOException e) {
				} finally {
					System.out
							.println("---------------------已清理临时文件-----------------------");
				}
			}
		}
	}

	public void disconnect(boolean flag) {
		if (client != null && client.isConnected()) {
			try {
				client.disconnect(true);
				System.out
						.println("---------------------FTP连接已关闭-----------------------");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			if (flag) {
				FileUtils.deleteDirectory(new File(tempPath));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取得ftp上的文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 如返回文件不存在请检查ftp链接是否有效
	 */
	public File getFile(String filePath) {

		filePath = filePath.replace("\\", "/").replace("//", "/");

		File dirFile = new File(tempPath + "/"
				+ filePath.substring(0, filePath.lastIndexOf("/")));
		dirFile.mkdirs();

		File docFile = new File(tempPath + "/" + filePath);

		if (ftpFileExists(filePath)) {
			try {
				if (filePath.endsWith(".aip")) {
					client.setType(FTPClient.TYPE_BINARY);
				}
				client.download(filePath, docFile);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("---------------------FTP获取文件发生异常："
						+ e.getMessage() + "-----------------------");
			}
		}

		return docFile;
	}

	/**
	 * 取得ftp上的文件，保存到指定位置.
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 */
	public File getFile(String filePath, String savePath) {
		File file = new File(savePath);
		try {
			if (filePath.endsWith(".aip")) {
				client.setType(FTPClient.TYPE_BINARY);
			}
			client.download(filePath, file);
		} catch (Exception e) {
			System.out.println("---------------------FTP获取文件发生异常："
					+ e.getMessage() + "-----------------------");
		}
		return file;
	}

	/**
	 * 上传文件到ftp服务器
	 * 
	 * @param filePath
	 *            文件地址
	 * @param putPath
	 *            上传到ftp位置
	 * @return
	 */
	public boolean putFile(String filePath, String putPath) {
		File file = new File(filePath);
		return this.putFile(file, putPath);
	}

	/**
	 * 上传文件到ftp服务器
	 * 
	 * @param file
	 *            文件对象
	 * @param putPath
	 *            上传到ftp位置
	 * @return Boolean true 成功 false失败（失败请检查ftp链接是否失败）
	 */
	public boolean putFile(File file, String putPath) {
		boolean result = false;

		try {
			if (!ftpFileDirsExists(putPath)) {
				this.createDirs(putPath);
			}
			client.changeDirectory("/" + putPath);
			if (file.getName().endsWith(".aip")) {
				client.setType(FTPClient.TYPE_BINARY);
			}
			client.upload(file);
			result = true;
		} catch (Exception e) {
			System.out.println("---------------------FTP上传文件发生异常："
					+ e.getMessage() + "-----------------------");
		}

		return result;
	}

	/**
	 * 
	 * @author 高明
	 * @time 2014-1-23
	 * @param sourceFile
	 *            FTP上文件原始路径
	 * @param savePath
	 *            FTP上将要保存的路径
	 * @param fileName
	 *            FTP上将要保存的文件名称
	 * @param flag
	 *            是否删除本地的临时文件
	 * @return 方法功能:copyFTP上的文件，并进行改名
	 */
	public boolean copyFile(String sourceFile, String savePath,
			String fileName, boolean flag) {
		sourceFile = sourceFile.replace("\\", "/");
		if (ftpFileExists(sourceFile)) {
			String sourceFilePath = sourceFile.substring(0,
					sourceFile.lastIndexOf("/"));
			File dirFile = null;
			try {
				dirFile = new File(tempPath
						+ sourceFile.substring(0, sourceFile.lastIndexOf("/")));
				dirFile.mkdirs();
				File docFile = new File(tempPath + sourceFile);
				if (sourceFile.endsWith(".aip")) {
					client.setType(FTPClient.TYPE_BINARY);
				}
				client.download(sourceFile, docFile);
			} catch (Exception e) {
				System.out
						.println("--------------------copy过程中文件下载失败---------------------");
				dirFile.delete();// 删除本地文件
				return false;
			}

			File tempFile = new File(tempPath + sourceFile);
			String filePath = tempFile.getAbsolutePath();
			String fileType = "";
			if (filePath.indexOf(".") >= 0) {
				fileType = filePath.substring(filePath.lastIndexOf("."),
						filePath.length());
			}

			tempFile.renameTo(new File(tempPath + sourceFilePath + "/"
					+ fileName + fileType)); // 改名
			File newFile = new File(tempPath + sourceFilePath + "/" + fileName
					+ fileType);
			if (newFile.exists()) {
				try {
					// 判断将删除的文件夹是否存在
					if (putFile(newFile, savePath + "/")) {// 将新文件上传至ftp服务器
						dirFile.delete();// 删除本地文件
						disconnect(flag);
					}
				} catch (Exception e) {
					System.out
							.println("--------------------copy过程中文件上传失败---------------------");
					dirFile.delete();// 删除本地文件
					return false;
				}
			}
		} else {
			System.out
					.println("---------------------FTP中文件未找到-----------------------");
			return false;
		}
		return true;
	}

	/**
	 * @author 矫健
	 * @time Jan 23, 2014
	 * @param path
	 *            要删除的文件路径
	 * @return 是否删除成功 方法功能:删除ftp上的文件
	 */
	public boolean deleteFile(String path) {
		boolean flag = true;
		try {
			if (this.ftpFileExists(path))// 文件是否存在,存在删除
			{
				client.deleteFile(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * @author 矫健
	 * @time Feb 11, 2014
	 * @param dir
	 *            路径
	 * @return 路径的属性 方法功能:-1不存在,1目录,0文件
	 */
	public int getFileType(String dir) {
		FTPFile[] files = null;
		try {
			files = client.list(dir);// 获取ftp当前目录所有文件
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		if (files.length > 1) {
			return FTPFile.TYPE_DIRECTORY;
		} else if (files.length == 1) {
			FTPFile f = files[0];

			if (f.getType() == FTPFile.TYPE_DIRECTORY) {
				return FTPFile.TYPE_DIRECTORY;
			}

			String path = dir + "/" + f.getName();
			try {
				int len = client.list(path).length;
				if (len == 1) {
					return FTPFile.TYPE_DIRECTORY;
				} else {
					return FTPFile.TYPE_FILE;
				}
			} catch (Exception e) {
				return FTPFile.TYPE_FILE;
			}
		} else {
			try {
				client.changeDirectory(dir);
				client.changeDirectoryUp();
				return FTPFile.TYPE_DIRECTORY;
			} catch (Exception e) {
				return -1;
			}
		}
	}

	/**
	 * @author 矫健
	 * @time Feb 11, 2014
	 * @param path
	 *            路径 方法功能:删除文件夹
	 */
	public void deleteFolder(String path) {
		String name = null;
		String newPath = "";
		FTPFile[] files = null;
		try {
			client.changeDirectory(path);
			files = client.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (FTPFile file : files) {
			name = file.getName();
			newPath = path.concat("/").concat(name);
			// 排除隐藏目录
			if (".".equals(name) || "..".equals(name)) {
				continue;
			}

			if (file.getType() == FTPFile.TYPE_DIRECTORY) {
				// 递归删除子目录
				deleteFolder(newPath + "/");
			} else if (file.getType() == FTPFile.TYPE_FILE) {
				// 删除文件
				try {
					// 删除当前目录
					client.deleteFile(file.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		try {
			client.changeDirectoryUp();
			client.deleteDirectory(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ******************************* 结束各种调用API ******************************
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) {
		File file = null;
		FTPUtils ftpUtils = new FTPUtils("192.168.2.196", 21, "administrator",
				"999888_hz");
		// ftpUtils.deleteFolder("/qqqqqqqqqqqqq/");
		ftpUtils.putFile("C://a.txt", "/1");
		System.out.println();
		;
		/*
		 * if (ftpUtils.isConnected()) {
		 * 
		 * try { file = ftpUtils .getFile(
		 * "/DE8505A0-7D1E-46B3-BC2D-A3C4F5C2D8FF/731EC225-5908-4E76-9159-164EEDD9B0E6/F805646E-3523-41F6-8C73-C4AA1CD25F3E/5cf0eae9-d9b1-49a8-8cbb-fb03e8065c72.doc"
		 * ); boolean b = ftpUtils.putFile("c:/a.txt", "/1/2/3/4/5/6/7/");
		 * System.out.println(b); } catch (Exception e1) { e1.printStackTrace();
		 * } finally { try { FileUtils.readFileToByteArray(file);
		 * System.out.println(file.getName()); } catch (Exception e) {
		 * e.printStackTrace(); } finally { ftpUtils.disconnect(); } } } else {
		 * boolean b = ftpUtils.putFile("c:/a.txt", "/1/2/3/4/5/6/7/"); file =
		 * ftpUtils .getFile(
		 * "/DE8505A0-7D1E-46B3-BC2D-A3C4F5C2D8FF/731EC225-5908-4E76-9159-164EEDD9B0E6/F805646E-3523-41F6-8C73-C4AA1CD25F3E/5cf0eae9-d9b1-49a8-8cbb-fb03e8065c72.doc"
		 * ); System.out.println("ftp链接失败"); }
		 */

		// File file = null;
		// try {
		// file =
		// ftpUtils.getFile("/codeimg/7EA7D8D8-70EB-479C-8ABD-B7CE068A59714.jpg");
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// try {
		// if(file.exists()){
		// FileUtils.readFileToByteArray(file);
		// }
		// System.out.println(file.getName());
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// ftpUtils.disconnect();
		// }
		// ftpUtils.copyFile("/codeimg/000633AD-A346-4A05-8A02-32FA5B182795.jpg","/ftp","222",true);

	}
}