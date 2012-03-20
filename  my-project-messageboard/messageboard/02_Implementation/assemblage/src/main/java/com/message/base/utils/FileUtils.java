package com.message.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.exception.FileExistException;

/**
 * 文件操作的工具类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-16 下午08:25:56
 */
public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	/**
	 * 系统文件分割符
	 */
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	
	/**
	 * 构造器私有化
	 */
	private FileUtils(){
		super();
	}
	
	/**
	 * 根据给定path创建文件夹
	 * 
	 * @param path	给定路径
	 */
	public static void createFolder(String path){
		if(StringUtils.isEmpty(path)){
			logger.error("the given path is null!");
			return;
		}
		if(logger.isDebugEnabled()){
			logger.debug("given path is '{}'!", path);
		}
		
		File file = new File(path);
		createFolder(file);
	}
	
	/**
	 * 根据给定file创建文件夹
	 * 
	 * @param file	给定文件
	 */
	public static void createFolder(File file){
		if(file.exists()){
			logger.error("folder named '{}' is exist!", file.getName());
			throw new FileExistException("'" + file.getName() + "'文件夹已存在");
		} else {
			try {
				org.apache.commons.io.FileUtils.forceMkdir(file);
			} catch (IOException e) {
				logger.error("create folder '{}' error!", file.getName());
			}
		}
	}
	
	/**
	 * 删除文件夹
	 * 
	 * @param folerName		文件名
	 * @param path			文件路径
	 * @return
	 */
	public static boolean deleteFolder(String folerName, String path){
		return deleteFolder(folerName + path);
	}
	
	/**
	 * 删除文件夹
	 * 
	 * @param filePath		路径名+文件名
	 * @return
	 */
	public static boolean deleteFolder(String filePath){
		if(StringUtils.isEmpty(filePath)){
			logger.error("given filePath '{}' is null!", filePath);
			return false;
		}
		
		File file = new File(filePath);
		
		if(!file.exists()){
			logger.error("this file named '{}' is not exist!", file.getName());
			return false;
		}
		
		return file.delete();
	}
	
	/**
	 * 创建文件
	 * 
	 * @param path		文件路径
	 * @param file		文件字节流
	 */
	public static void createFile(String path, byte[] file){
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			fos.write(file);
			
			if(fos != null){
				fos.close();
			}
		} catch (Exception e) {
			logger.error("create file '{}' error!", path);
		}
	}
	
	/**
	 * 重命名文件夹
	 * 
	 * @param oldName	旧文件夹名
	 * @param newName	新文件夹名
	 * @param path		文件夹路径
	 * @return
	 */
	public static boolean renameFolder(String oldName, String newName, String path){
		File oldFile = new File(path + FILE_SEPARATOR + oldName);
		return oldFile.renameTo(new File(path + FILE_SEPARATOR + newName));
	}
	
	/**
	 * 如是是文件,删除;如是是文件夹,删除它和它下面的所有文件
	 * 
	 * @param dirPath	文件夹路径
	 * @return
	 */
	public static boolean deleteFiles(String dirPath){
		File file = new File(dirPath);
		boolean success = Boolean.TRUE;
		
		if(!file.isDirectory()){
			//如果不是文件夹，而是文件，则直接删除
			if(!file.delete()){
				success = Boolean.FALSE;
			}
			return success;
		}
		
		String[] fileNames = file.list();
		for(String fileName : fileNames){
			if(!deleteFiles(dirPath + FILE_SEPARATOR + fileName)){
				success = Boolean.FALSE;
			}
		}
		if(!file.delete()){
			success = Boolean.FALSE;
		}
		
		return success;
	}
	
	/**
	 * 获得文件的扩展名,如果是文件夹,返回null.没有扩展名,返回""
	 * 
	 * @param file	要获取信息的文件
	 * @return
	 */
	public static String getExt(File file){
		if(!file.isFile() || !file.exists()){
			return StringUtils.EMPTY;
		}
		int i = file.getName().lastIndexOf(".");
		if(i == -1){
			return StringUtils.EMPTY;
		}
		
		return file.getName().substring(i);
	}
	
	/**
	 * 获得文件的扩展名,如果是文件夹,返回null.没有扩展名,返回""
	 * 
	 * @param fileName		文件名
	 * @return
	 */
	public static String getExt(String fileName){
		if(StringUtils.isEmpty(fileName)){
			logger.error("given fileName '{}' is null!", fileName);
		}
		int i = fileName.lastIndexOf(".");
		if(i == -1){
			return StringUtils.EMPTY;
		}
		
		return fileName.substring(i);
	}
	
	/**
	 * 根据所给文件获取此文件的字节流
	 * 
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	public static byte[] getFileByte(File file) throws Exception{
		if(!file.exists() || !file.isFile() || !file.canRead()) {
			logger.error("given path '{}' is not a file, maybe a folder! or it can not read!", file);
			return null;
		}
		
		FileInputStream fis = openInputStream(file);
		if(fis == null){
			logger.error("the FileInputStream is null!");
		}
		
		return IOUtils.toByteArray(fis);
	}
	
	/**
	 * 根据所给路径获取此文件的字节流
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public static byte[] getFileByte(String filePath) throws Exception {
		if(StringUtils.isEmpty(filePath)){
			logger.error("given filePath is null!");
			return null;
		}
		File file = new File(filePath);
		return getFileByte(file);
	}
	
	/**
	 * 根据所给文件获取此文件的字节流
	 * 
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	public static char[] getFileChar(File file) throws Exception{
		if(!file.exists() || !file.isFile() || !file.canRead()) {
			logger.error("given path '{}' is not a file, maybe a folder! or it can not read!", file);
			return null;
		}
		
		FileInputStream fis = openInputStream(file);
		if(fis == null){
			logger.error("the FileInputStream is null!");
		}
		
		return IOUtils.toCharArray(fis);
	}
	
	/**
	 * 根据所给路径获取此文件的字节流
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public static char[] getFileChar(String filePath) throws Exception {
		if(StringUtils.isEmpty(filePath)){
			logger.error("given filePath is null!");
			return null;
		}
		File file = new File(filePath);
		return getFileChar(file);
	}
	
	/**
	 * 根据所给文件获取输入流
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static FileInputStream openInputStream(File file) throws Exception{
		if (file.exists()) {
			if (file.isDirectory() || !file.canRead()) {
				logger.error("File '{}' exists but is a directory", file);
			}
		} else {
			logger.error("File '{}' does not exist", file);
		}
		return new FileInputStream(file);
	}
	
	/**
	 * 根据所给路径获取输入流
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static FileInputStream openInputStream(String filePath) throws Exception{
		if(StringUtils.isEmpty(filePath)){
			logger.error("given filePath is null!");
			return null;
		}
		File file = new File(filePath);
		return openInputStream(file);
	}

    /**
     * 获取单个文件的大小
     *
     * @param file      要取得文件的大小
     * @return  文件大小，单位字节
     */
    public static Long getFileSize(File file){
        if(!file.exists() || file.isDirectory()) {
            logger.warn("this file is not exists or it is not a file! file name is '{}'", file.getName());
            return Long.valueOf(0);
        }

        return file.length();
    }

    /**
     * 获取单个文件的大小
     *
     * @param filePath      要取得文件的path
     * @return  文件大小，单位字节
     */
    public static Long getFileSize(String filePath){
        if(StringUtils.isEmpty(filePath)){
            logger.error("given file path is null");
            return Long.valueOf(0);
        }

        File file = new File(filePath);

        return getFileSize(file);
    }

    /**
     * 获取文件夹的大小
     * 
     * @param file  文件夹
     * @return
     */
    public static Long getDirectorySize(File file){
        if(!file.exists()){
            logger.warn("given file '{}' is null!", file);
            return Long.valueOf(0);
        }
        Long size = 0L;
        if(file.isDirectory()){
            File files[] = file.listFiles();
            for(int i = 0; i < files.length; i++){
                if(files[i].isDirectory()){
                    size = size + getDirectorySize(files[i]);
                } else {
                    size += getFileSize(files[i]);
                }
            }
        } else {
            size += file.length();
        }

        return size;
    }

    /**
     * 获取文件夹的大小
     *
     * @param filePath  文件夹路径
     * @return
     */
    public static Long getDirectorySize(String filePath){
        if(StringUtils.isEmpty(filePath)){
            logger.error("given file path is null");
            return Long.valueOf(-1);
        }
        File file = new File(filePath);

        return getDirectorySize(file);
    }

    public static void main(String[] args){
        String filePath = "F:\\workspace\\workspace\\messageboard\\messageboard\\02_Implementation\\" +
                "assemblage\\src\\main\\java\\com\\message\\base";
//        System.out.println(getFileSize(filePath));\
        System.out.println(getDirectorySize(filePath));
    }
}
