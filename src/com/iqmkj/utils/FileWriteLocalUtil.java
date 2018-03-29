package com.iqmkj.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件操作工具类（向本地写文件）
 * @author gql
 *
 */
public class FileWriteLocalUtil {

	/**
	 * 保存流文件
	 * @param inputStream 文件输入流
	 * @param fileDir 文件保存目录，如“userImage”
	 * @param fileName 文件名称，如“abc.png”
	 * @return  文件保存成功返回文件的相对路径，如：/upload/userImage/20150516/abc.png；保存不成功返回null。
	 */
	public static String SaveInputStreamToFile(InputStream inputStream, String fileDir, String fileName){
		String classRoot = FileWriteLocalUtil.class.getResource("/").getFile();
		String rootPath = "";
		try {
			rootPath = new File(classRoot).getParentFile().getParentFile().getCanonicalPath();
		} catch (IOException e) {
			LogUtil.printErrorLog(e);
		}

		if(!StringUtil.isNotNull(rootPath) || !StringUtil.isNotNull(fileDir) || !StringUtil.isNotNull(fileName) || inputStream == null){
			return null;
		}

		rootPath = rootPath.replaceAll("%20", " ");
		String formatDate = DateUtil.longToTimeStr(DateUtil.getCurrentTimeMillis(), DateUtil.dateFormat11);
		String fileDirectory = "/upload" ;
		//创建保存文件的文件路劲
		String targetDirectory = rootPath + fileDirectory + "/" + fileDir + "/" + formatDate + "/";

		String newFileName = DateUtil.longToTimeStr(DateUtil.getCurrentTimeMillis(), DateUtil.dateFormat6) + NumberUtil.createNumberStr(11);
		String suffixStr = fileName.substring(fileName.lastIndexOf("."));
		//新文件名
		newFileName = newFileName + suffixStr;

		try {
			File targetFile = new File(targetDirectory, newFileName);
			FileUtils.copyInputStreamToFile(inputStream, targetFile);

			return fileDirectory + "/" + fileDir + "/" + formatDate + "/" + newFileName;
		} catch (Exception ex) {
			LogUtil.printErrorLog(ex);
			return null;
		}
	}

	/**
	 * 保存流文件
	 *
	 * @param inputStream 文件输入流
	 * @param fileDir     文件保存目录，如“userImage”
	 * @param fileName    文件名称，如“abc.png”
	 * @return 文件保存成功返回文件的绝对路径，如：C:/iqmkj_idea/jydp/out/artifacts/jydp_war_exploded/upload/tempReduceImage/abc.png；保存不成功返回null。
	 */
	public static String SaveInputStreamToFileByPath(InputStream inputStream, String fileDir, String fileName) {
		String classRoot = FileWriteLocalUtil.class.getResource("/").getFile();
		String rootPath = "";
		try {
			rootPath = new File(classRoot).getParentFile().getParentFile().getCanonicalPath();
		} catch (IOException e) {
			LogUtil.printErrorLog(e);
		}

		if (!StringUtil.isNotNull(rootPath) || !StringUtil.isNotNull(fileDir) || !StringUtil.isNotNull(fileName) || inputStream == null) {
			return null;
		}

		rootPath = rootPath.replaceAll("%20", " ");
		String formatDate = DateUtil.longToTimeStr(DateUtil.getCurrentTimeMillis(), DateUtil.dateFormat11);
		String fileDirectory = "/upload";
		//创建保存文件的文件路劲
		String targetDirectory = rootPath + fileDirectory + "/" + fileDir + "/";

		String newFileName = DateUtil.longToTimeStr(DateUtil.getCurrentTimeMillis(), DateUtil.dateFormat6) + NumberUtil.createNumberStr(11);
		String suffixStr = fileName.substring(fileName.lastIndexOf("."));
		//新文件名
		newFileName = newFileName + suffixStr;

		try {
			File targetFile = new File(targetDirectory, newFileName);
			FileUtils.copyInputStreamToFile(inputStream, targetFile);

			return targetFile.getPath();
		} catch (Exception ex) {
			LogUtil.printErrorLog(ex);
			return null;
		}
	}

	/**
	 * 删除文件
	 * @param filePath  文件相对路径
	 * @return  删除成功：返回true，删除失败：返回false
	 */
	public static boolean deleteFile(String filePath){
		String classRoot = FileWriteLocalUtil.class.getResource("/").getFile();
		String rootPath = "";
		try {
			rootPath = new File(classRoot).getParentFile().getParentFile().getCanonicalPath();
		} catch (IOException e) {
			LogUtil.printErrorLog(e);
		}

		if(!StringUtil.isNotNull(rootPath) || !StringUtil.isNotNull(filePath)){
			return false;
		}

		rootPath = rootPath.replaceAll("%20", " ");
		String fileFullPath = rootPath + filePath;

		try{
			File targetFile = new File(fileFullPath);
			if(targetFile != null && targetFile.exists()){
				return targetFile.delete();
			}
		}catch(Exception e){
			LogUtil.printErrorLog(e);
		}
		return false;
	}

	/**
	 * 删除文件
	 * @param filePath  文件绝对路径
	 * @return  删除成功：返回true，删除失败：返回false
	 */
	public static boolean deleteFileRealPath(String filePath){
		if(!StringUtil.isNotNull(filePath)){
			return false;
		}

		try{
			File targetFile = new File(filePath);
			if(targetFile != null && targetFile.exists()){
				return targetFile.delete();
			}
		}catch(Exception e){
			LogUtil.printErrorLog(e);
		}
		return false;
	}

	/**
	 * 删除过期文件（定时删除）
	 * @param outTime 过期时间（以文件的最后修改时间为准）
	 * @param ignoreDirList 忽略的文件目录名称（不做删除的），不填则删除全部
	 */
	public static void deleteOutTimeFile(Timestamp outTime, List<String> ignoreDirList){
		String classRoot = FileWriteLocalUtil.class.getResource("/").getFile();
		String rootPath = "";
		try {
			rootPath = new File(classRoot).getParentFile().getParentFile().getCanonicalPath();
		} catch (IOException e) {
			LogUtil.printErrorLog(e);
		}

		if(!StringUtil.isNotNull(rootPath) || outTime == null){
			return;
		}

		//忽略目录
		Map<String, String> ignoreDirMap = new HashMap<String, String>();
		if(ignoreDirList != null && ignoreDirList.size() > 0){
			for (String ignoreDir : ignoreDirList) {
				ignoreDirMap.put(ignoreDir, ignoreDir);
			}
		}

		rootPath = rootPath.replaceAll("%20", " ");
		String rootDir = rootPath + "/upload";

		File rootDirFile = new File(rootDir);
		if(rootDirFile != null && rootDirFile.isDirectory()){
			File[] childDirFileList = rootDirFile.listFiles();
			if(childDirFileList != null && childDirFileList.length > 0){
				for (File childFile : childDirFileList) {
					deleteFile(childFile, outTime.getTime(), ignoreDirMap);
				}
			}
		}
	}

	/**
	 * 删除过期文件（执行方法）
	 * @param deleteFile 待删除的文件
	 * @param outTime 过期时间（以文件的最后修改时间为准）
	 * @param ignoreDirMap 忽略的文件目录名称（不做删除的），不填则删除全部
	 */
	private static void deleteFile(File deleteFile, long outTime, Map<String, String> ignoreDirMap){
		if(deleteFile != null && deleteFile.exists()){
			if(deleteFile.isDirectory()){
				//是文件夹

				//不删除文件夹规则
				if(ignoreDirMap != null && ignoreDirMap.size() > 0){
					String fileName = deleteFile.getName();
					if(ignoreDirMap.containsKey(fileName)){
						return;
					}
				}

				File[] childFileList = deleteFile.listFiles();
				if(childFileList == null || childFileList.length <= 0){
					//删除空文件夹
					deleteFile.delete();
				}else{
					for (File childFile : childFileList) {
						deleteFile(childFile, outTime, ignoreDirMap);
					}
				}
			}else{
				//是文件
				long lastModifyTime = deleteFile.lastModified();
				if(lastModifyTime < outTime){
					deleteFile.delete();
				}
			}
		}
	}


	/**
	 * 将字符处写入文件
	 * @param filePath 路径字符串
	 * @param fileName 文件名
	 * @param writeString 字符信息
	 */
	public static void writeStringToFile(String filePath, String fileName, String writeString){
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File file = new File(filePath+"/"+fileName);
			if (file.exists()){
				file.delete();
			}else{
				new File(filePath).mkdirs();
			}
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(writeString);
		} catch (IOException e) {
			LogUtil.printErrorLog(e);
		}finally {
			if (bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					LogUtil.printErrorLog(e);
				}
			}
			if (fw !=null){
				try {
					fw.close();
				} catch (IOException e) {
					LogUtil.printErrorLog(e);
				}
			}
		}
	}

}
