package com.message.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import com.message.base.spring.ApplicationHelper;

/**
 * 读取系统的配置文件<br/><br/>
 * <b>使用方法(配置文件中配置)：</b>
 * <table border='1'>
 * 	<tr>
 * 		<th>key</th><th>是否必填</th><th>意义</th>
 *  </tr>
 * 	<tr>
 * 		<th>propertiesFilePath</th><th>必填项</th><th>系统的配置文件路径</th>
 *  </tr>  
 * 	<tr>
 * 		<th>singleton</th><th>非必填项</th><th>是否单例，默认true</th>
 *  </tr>
 * 	<tr>
 * 		<th>isSupportXmlFile</th><th>非必填项</th><th>是否支持读取XML，默认false</th>
 *  </tr> 
 * 	<tr>
 * 		<th>fileEncoding</th><th>非必填项</th><th>读取文件时的编码，默认UTF-8</th>
 *  </tr> 
 * 	<tr>
 * 		<th>fileLastLoadPrefix</th><th>非必填项</th><th>最后加载的文件的前缀，默认"config."</th>
 *  </tr>
 * 	<tr>
 * 		<th>defaultConfigKey</th><th>非必填项</th><th>默认的寻找配置文件的key，默认"config.root"</th>
 *  </tr>
 * </table>
 * 
 * @author sunhao
 * @version V1.0
 * @createTime 2012-3-10 下午07:28:05
 */
@SuppressWarnings({ "rawtypes", "unused" })
public final class PropertiesFactoryBean implements FactoryBean, InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(PropertiesFactoryBean.class);
	
	/**
	 * 指定系统配置文件的路径，如"/config/message/root.properties"
	 */
	private String propertiesFilePath;
	
	/**
	 * 存放配置文件的配置项
	 */
    private Object configProperties;
    /**
     * 是否单例
     */
    private boolean singleton = true;
    /**
     * 是否支持读取XML文件，默认不支持
     */
    private boolean isSupportXmlFile = false;
    
    /**
     * 文件的编码，默认是UTF-8
     */
    private String fileEncoding = "UTF-8";
    
    /**
     * 最后加载的文件的前缀
     */
    private String fileLastLoadPrefix = "config.";
    /**
     * 默认的寻找配置文件的key
     */
    private String defaultConfigKey = "config.root";
    /**
     * spring的文件解析器
     */
    private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
    
    
    //以下为默认系统常量
    /**
	 * 默认的配置文件路径,如果不指定<code>propertiesFilePath</code>,则使用这个路径
	 */
	private static final String DEFAULT_PROPERTIES_PATH = "/config/message/root.properties";

	/**
	 * properties文件的后缀名
	 */
    private static final String PROPERTIES_FILE_EXTENSION = ".properties";
    /**
     * html文件的后缀名
     */
    private static final String HTML_FILE_EXTENSION = ".html";
    /**
     * htm文件的后缀名
     */
    private static final String HTML_FILE_EXTENSION_SHORT = ".htm";
    /**
     * txt文件的后缀名
     */
    private static final String TEXT_FILE_EXTENSION = ".txt";
    /**
     * XML文件的后缀名
     */
    private static final String XML_FILE_EXTENSION = ".xml";

	/**
	 * 生成实例，返回的其实是一个Map
	 * 
	 * @return
	 * @throws Exception
	 */
	private Object createInstance() throws Exception {
		Properties result = new Properties();
		Properties fileProp = new Properties();
		Properties rootProp = new Properties();
		
		initLocations(result, fileProp, rootProp);
		
		fileProp.putAll(result);
		return fileProp;
	}

	/**
     * 读取配置文件的规则
     *
     * @param result    存放配置读取的map
     * @param fileProps 存放读取文件内容的map
     * @param rootProps 存放配置文件root的最基础的配置信息
     * @throws IOException IOException
     */
    @SuppressWarnings("unchecked")
	private void initLocations(Properties result, Properties fileProps, Properties rootProps) throws IOException {
    	List locs = new ArrayList();
    	
    	try {
			//this.loadPropByPath(filePath, locs, fileProps, false);
    		
    		if(StringUtils.isNotEmpty(this.getPropertiesFilePath())){
    			File file = new File(this.getPropertiesFilePath());
    			
    			//判读文件存在并且是文件并且可读
    			if(file.exists() && file.isFile() && file.canRead()){
    				Properties config = new Properties();
    				FileInputStream fs = FileUtils.openInputStream(file);
    				config.load(fs);
    				fs.close();		//关闭流
    				rootProps.putAll(config);
    				result.putAll(rootProps);
    				
    				String secondPath = StringUtils.trimToNull(config.getProperty(this.defaultConfigKey));
    				
    				if(StringUtils.isNotEmpty(secondPath)){
    					secondPath += "properties/";
    					if(!secondPath.startsWith("file://")){
    						this.loadPropByPath("file://" + secondPath, locs, fileProps, true);
    					} else {
    						this.loadPropByPath(secondPath, locs, fileProps, true);
    					}
    				} else {
    					logger.error("the '{}' is not found!", this.defaultConfigKey);
    				}
    			} else {
    				logger.error("this project config files is not found with path:'{}' given!", this.getPropertiesFilePath());
    			}
    		}
    		this.loadProperties(result, (Resource[]) locs.toArray(new Resource[locs.size()]));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("can not load config file from given path '{}'!", this.getPropertiesFilePath());
		}
    }
    
    /**
     * 根据路径将配置文件列表放到路径列表中
     *
     * @param location          文件的目录
     * @param locations         返回的文件具体路径
     * @param fileProps         文件内容存放的容器
     * @param isSupportLastFile 是否支持最后文件加载
     * @throws IOException 异常
     */
	@SuppressWarnings("unchecked")
	private void loadPropByPath(String location, List locations, Properties fileProps, boolean isSupportLastFile) throws IOException {
		UrlResource ur = new UrlResource(location);
		Collection files = FileUtils.listFiles(ur.getFile(), null, false);
		
		if(!files.isEmpty()){
			Iterator it = files.iterator();
			File file;
			String fileName;
			File lastFile = null;
			
			while(it.hasNext()){
				file = (File) it.next();
				fileName = StringUtils.trimToNull(file.getName());
				
				if(fileName.endsWith(PROPERTIES_FILE_EXTENSION)){
					if(isSupportLastFile && fileName.startsWith(this.fileLastLoadPrefix)){
						lastFile = file;
					} else {
						locations.add(new FileSystemResource(file));
					}
				} else if (fileName.endsWith(HTML_FILE_EXTENSION)
						|| fileName.endsWith(HTML_FILE_EXTENSION_SHORT)
						|| fileName.endsWith(TEXT_FILE_EXTENSION)) {
					//如果文件是html、htm、txt类型的文件，则将文件名作为key，文件内容作为value
					fileProps.setProperty(this.getMainFileName(fileName), this.getFileContent(file));
					logger.info("the file named '{}' is loading!the content is '{}'!",
							new Object[] { this.getMainFileName(fileName), this.getFileContent(file) });
				} else {
					logger.info("the file named '{}' is ignore!", fileName);
				}
			}
			if(lastFile != null){
				locations.add(new FileSystemResource(lastFile));
			}
		}
	}
	
	private void loadProperties(Properties props, Resource[] locs) throws IOException {
		if(locs != null && locs.length > 0){
			Resource location;
			for(int i = 0; i < locs.length; i++){
				location = locs[i];
				logger.info("loading config from {}", location);
				InputStream is = null;
				
				try {
					is = location.getInputStream();
					if(this.isSupportXmlFile && location.getFilename().endsWith(XML_FILE_EXTENSION)){
						this.propertiesPersister.loadFromXml(props, is);
					} else {
						if(this.fileEncoding != null){
							this.propertiesPersister.load(props, new InputStreamReader(is, fileEncoding));
						} else {
							this.propertiesPersister.load(props, is);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Could not load properties from '{}' : {}", new Object[]{location, e.getMessage()});
				} finally {
					if(is != null){
						is.close();
					}
				}
			}
		} else {
			logger.error("the resources is null!");
		}
	}
	
	/**
     * 获取文件名除去扩展名的部分.
     *
     * @param fileName 文件名.
     * @return 文件名除去扩展名的部分.
     */
    private String getMainFileName(final String fileName) {
    	int pos = fileName.lastIndexOf(".");
    	String mainName = fileName;
    	
    	if(pos > 0){
    		mainName = fileName.substring(0, pos);
    	}
    	
    	return mainName;
    }
    
    /**
     * 读取文件内容.
     *
     * @param file 文件.
     * @return 文件内容.
     * @throws IOException IO错误.
     */
    private String getFileContent(final File file) throws IOException {
    	return FileUtils.readFileToString(file, fileEncoding);
    }

	private void loadProperties(Properties result) {
		
	}

	public void afterPropertiesSet() throws Exception {
		this.configProperties = createInstance();
	}

	public Object getObject() throws Exception {
		return this.configProperties;
	}

	public Class getObjectType() {
		return Properties.class;
	}

	public boolean isSingleton() {
		return this.singleton;
	}

	public String getPropertiesFilePath() {
		return propertiesFilePath;
	}

	public void setPropertiesFilePath(String propertiesFilePath) {
		if(StringUtils.isEmpty(propertiesFilePath)){
			this.propertiesFilePath = DEFAULT_PROPERTIES_PATH;
			return;
		}
		logger.info("the propertiesFilePath is '{}'.", propertiesFilePath);
		this.propertiesFilePath = propertiesFilePath;
	}

	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	public void setSupportXmlFile(boolean isSupportXmlFile) {
		this.isSupportXmlFile = isSupportXmlFile;
	}

	public void setPropertiesPersister(PropertiesPersister propertiesPersister) {
		this.propertiesPersister = (propertiesPersister == null ? 
				new DefaultPropertiesPersister() : propertiesPersister);
	}

	public void setFileLastLoadPrefix(String fileLastLoadPrefix) {
		this.fileLastLoadPrefix = fileLastLoadPrefix == null ? "config." : fileLastLoadPrefix;
	}

	public void setDefaultConfigKey(String defaultConfigKey) {
		this.defaultConfigKey = defaultConfigKey == null ? "config.root" : defaultConfigKey;
	}
    
}
