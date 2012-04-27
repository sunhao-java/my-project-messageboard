/*
 * Author: nzhou
 *
 * Workfile: SimplePropertiesFactoryBean.java
 *
 * Feature NO.: None
 *
 * Copyright (c) 2009 Wiscom. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 *
 * Issued by Wiscom Ltd.
 *
 */

package com.message.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

/**
 *
 * 读取ccs系统的配置文件,默认从/WEB-INF/properties/下加载，配置文件的加载顺序为
 * /WEB-INF/properties/，System.getProperty,/opt/wiscom/config/ccs/seu/properties/（配置）,数据库
 * Author: nzhou (nzhou@wiscom.com.cn) 
 * Date: 2009-12-2 
 * Version: V1.0
 * History:
 */
public final class GenericPropertiesFactoryBean
        implements FactoryBean, InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(GenericPropertiesFactoryBean.class);
    private boolean singleton = true;

    //存放配置文件的配置项
    private Object configProperties;

    public static final String PROPERTIES_FILE_EXTENSION = ".properties";
    public static final String HTML_FILE_EXTENSION = ".html";
    public static final String HTML_FILE_EXTENSION_SHORT = ".htm";
    public static final String TXT_FILE_EXTENSION = ".txt";

    public static final String XML_FILE_EXTENSION = ".xml";
    //最后加载的文件的前缀
    public static final String FILE_LAST_LOAD_PREFIX = "config.";

    //public static final String SECOND_CONFIG_BASE_PATH = "spring.config.root";

    private String defaultPropertiesLocation = "/WEB-INF/properties/";

    private String ccsPropertiesLocation;

    private String projectRootConfigFilePath;
    private boolean isSupportXmlFile =false;

    private Properties[] localProperties;
    private Resource[] locations;
    private boolean localOverride = false;
    private boolean ignoreResourceNotFound = false;
    private String fileEncoding = null;//"UTF-8";
    private String templateEncoding="UTF-8";
    private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();


    /**
	 * @param templateEncoding the templateEncoding to set
	 */
	public void setTemplateEncoding(String templateEncoding) {
		this.templateEncoding = templateEncoding;
	}


	/**
     * Set the ccsPropertiesLocation value.
     *
     * @param ccsPropertiesLocation
     *            the ccsPropertiesLocation to set.
     */
    public void setCcsPropertiesLocation(String ccsPropertiesLocation) {
        this.ccsPropertiesLocation = ccsPropertiesLocation;
    }


    /**
     * CCS读取配置文件的规则
     * @param result  存放配置读取的map
     * @param fileProps  存放读取文件内容的map
     * @param rootProps  存放配置文件root的最基础的配置信息
     * @throws IOException IOException
     */
    @SuppressWarnings("unchecked")
	private void initCcsLocations(Properties result,Properties fileProps,Properties rootProps) throws IOException {

        List/*<Resource>*/ locs = new ArrayList/*<Resource>*/(32);


        String rootPath = "/message";//ApplicationHelper.getInstance().getRootPath();
        StringBuilder sb = new StringBuilder(128);
        sb.append("file:///").append(rootPath).append(this.defaultPropertiesLocation);
        //默认的配置文件路径
        String defaultLocation = sb.toString().replace('\\','/');

        if(logger.isInfoEnabled()){
            logger.info("default config path is "+defaultLocation);
        }
        //Properties fileProps = new Properties();
        try {
            //从CCS指定的路径读取配置文件列表

            this.loadPropByPath(defaultLocation,locs,fileProps,false);

            String secondConfig = StringUtils.trimToNull(System.getProperty("CCS.CONFIG.ROOT"));
            if(secondConfig==null)
            {
                File file = new File(this.projectRootConfigFilePath);
                if(file.exists() && file.isFile() && file.canRead())
                {
                    Properties p = new Properties();
                    FileInputStream fs = FileUtils.openInputStream(file);
                    p.load(fs);
                    fs.close();
                    rootProps.putAll(p);
                    result.putAll(p);
                    //CollectionUtils.mergePropertiesIntoMap(p,result);

                    secondConfig = StringUtils.trimToNull(p.getProperty("config.root"));
                    if(secondConfig!=null)
                    {
                        secondConfig+="/properties/";
                        if(!secondConfig.startsWith("file://")){
                            this.loadPropByPath("file://"+secondConfig,locs,fileProps,true);
                        }else{
                            this.loadPropByPath(secondConfig,locs,fileProps,true);
                        }
                    }else{
                        logger.error("'config.root' not found! Plz create properties file '" + this.projectRootConfigFilePath + "'");
                    }
                }else{
                    logger.warn("Project config is not found!");
                }
            }
            this.loadProperties(result, (Resource[])locs.toArray(new Resource[locs.size()]));


        } catch (IOException ex) {
            if (this.ignoreResourceNotFound) {
                logger.warn("Could not load properties from "
                        + ccsPropertiesLocation + ": " + ex.getMessage());

            } else {
                throw ex;
            }
        }
    }


    /**
     * 根据路径将配置文件列表放到路径列表中
     * @param location  文件的目录
     * @param locations  返回的文件具体路径
     * @param fileProps  文件内容存放的容器
     * @param isSupportLastFile 是否支持最后文件加载
     * @throws IOException   异常
     */
	private void loadPropByPath(String location,List locations,Properties fileProps,boolean isSupportLastFile) throws IOException{
        UrlResource ur = new UrlResource(location);
        Collection fs = FileUtils.listFiles(ur.getFile(), null, false);
        if (!fs.isEmpty()) {
            Iterator fi = fs.iterator();
            File f;
            String fileName;
            File lastFile=null;//最后加载的文件
            while (fi.hasNext()) {
                f = (File) fi.next();
                fileName = StringUtils.trimToEmpty(f.getName());
                //若以.xml或.properties文件结尾的，按照Spring读取配置文件方式读取
                if (fileName.endsWith(PROPERTIES_FILE_EXTENSION)
                        ||fileName.endsWith(XML_FILE_EXTENSION)) {
                    if(isSupportLastFile&&fileName.startsWith(FILE_LAST_LOAD_PREFIX)){
                        lastFile = f;
                    }else{
                        locations.add(new FileSystemResource(f));
                    }
                }else if (fileName.endsWith(HTML_FILE_EXTENSION)
                        ||fileName.endsWith(HTML_FILE_EXTENSION_SHORT)
                        ||fileName.endsWith(TXT_FILE_EXTENSION))  {
                    //否则，则将其文件名作key，文件的内容作value读取
                    fileProps.setProperty(getMainFileName(fileName),getFileContent(f));
                    logger.info("file '{}' content has been loaded",f.toString());
                }else{
                    logger.info("file '{}' is ignore",f.toString());
                }
            }
            if(lastFile!=null){
                locations.add(new FileSystemResource(lastFile));
            }
        }
    }

    /*
      * (non-Javadoc)
      *
      * @see
      * org.springframework.core.io.support.PropertiesLoaderSupport#mergeProperties
      * ()
      */

    /**
     * 获取文件名除去扩展名的部分.
     *
     * @param fileName 文件名.
     * @return 文件名除去扩展名的部分.
     */
    private String getMainFileName(final String fileName) {
        int pos = fileName.lastIndexOf('.');
        String mainName = fileName;

        if (pos > 0) {
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
        return FileUtils.readFileToString(file, templateEncoding);
    }

    public Object getObject() throws Exception {
        return this.configProperties;

    }

    public Class getObjectType() {
        return Properties.class;
    }

    public final boolean isSingleton() {
        return this.singleton;
    }

    public void afterPropertiesSet() throws Exception {
        this.configProperties = createInstance();
    }

    private Object createInstance() throws IOException {
        if(!this.isSupportXmlFile){
            logger.info("'css' config is not support xml properties!");
        }
        Properties result = this.mergeProperties();
        Properties fileProp = new Properties();
        Properties rootProp = new Properties();
        initCcsLocations(result, fileProp,rootProp);
        //如果系统配置为支持变量替换，则将配置中的属性替换为具体的值
        if(!"false".equals(StringUtils.trimToEmpty(result.getProperty("ccs.config.variable.replace")).toLowerCase()))
        {
            logger.info("replace the 'ccs' config variable");
            replaceValue(result,rootProp);
        }
        fileProp.putAll(result);
        return fileProp;
    }

    /**
     * 将map的值中的变量替换掉
     * @param map 要替换值的map
     * @param valueMap 提供替换值的Map
     */
	private void replaceValue(Map map,Map valueMap){
        Iterator it =map.entrySet().iterator();
        Map.Entry me;
        String key,val;
        for(;it.hasNext();){
            me = (Map.Entry)it.next();
            key =(String)me.getKey();
            val =StringUtils.trimToEmpty((String)me.getValue());
            map.put(key,Evaluator.replaceVariable(val,valueMap,true));

        }


    }

    public void setDefaultPropertiesLocation(final String dl) {
        this.defaultPropertiesLocation = dl;
    }

    /**
     * Set local properties, e.g. via the "props" tag in XML bean definitions.
     * These can be considered defaults, to be overridden by properties
     * loaded from files.
     */
    public void setProperties(Properties properties) {
        this.localProperties = new Properties[] {properties};
    }

    /**
     * Set local properties, e.g. via the "props" tag in XML bean definitions,
     * allowing for merging multiple properties sets into one.
     */
    public void setPropertiesArray(Properties[] propertiesArray) {
        this.localProperties = propertiesArray;
    }

    /**
     * Set a location of a properties file to be loaded.
     * <p>Can point to a classic properties file or to an XML file
     * that follows JDK 1.5's properties XML format.
     */
    public void setLocation(Resource location) {
        this.locations = new Resource[] {location};
    }

    /**
     * Set locations of properties files to be loaded.
     * <p>Can point to classic properties files or to XML files
     * that follow JDK 1.5's properties XML format.
     * <p>Note: Properties defined in later files will override
     * properties defined earlier files, in case of overlapping keys.
     * Hence, make sure that the most specific files are the last
     * ones in the given list of locations.
     */
    public void setLocations(Resource[] locations) {
        this.locations = locations;
    }

    /**
     * Set whether local properties override properties from files.
     * <p>Default is "false": Properties from files override local defaults.
     * Can be switched to "true" to let local properties override defaults
     * from files.
     */
    public void setLocalOverride(boolean localOverride) {
        this.localOverride = localOverride;
    }

    /**
     * Set if failure to find the property resource should be ignored.
     * <p>"true" is appropriate if the properties file is completely optional.
     * Default is "false".
     */
    public void setIgnoreResourceNotFound(boolean ignoreResourceNotFound) {
        this.ignoreResourceNotFound = ignoreResourceNotFound;
    }

    /**
     * Set the encoding to use for parsing properties files.
     * <p>Default is none, using the <code>java.util.Properties</code>
     * default encoding.
     * <p>Only applies to classic properties files, not to XML files.
     * @see org.springframework.util.PropertiesPersister#load
     */
    public void setFileEncoding(String encoding) {
        this.fileEncoding = encoding;
    }

    /**
     * Set the PropertiesPersister to use for parsing properties files.
     * The default is DefaultPropertiesPersister.
     * @see org.springframework.util.DefaultPropertiesPersister
     */
    public void setPropertiesPersister(PropertiesPersister propertiesPersister) {
        this.propertiesPersister =
                (propertiesPersister != null ? propertiesPersister : new DefaultPropertiesPersister());
    }


    /**
     * Return a merged Properties instance containing both the
     * loaded properties and properties set on this FactoryBean.
     */
    private Properties mergeProperties() throws IOException {
        Properties result = new Properties();

        if (this.localOverride) {
            // Load properties from file upfront, to let local properties override.
            loadProperties(result);
        }

        if (this.localProperties != null) {
            for (int i = 0; i < this.localProperties.length; i++) {
                CollectionUtils.mergePropertiesIntoMap(this.localProperties[i], result);
            }
        }

        if (!this.localOverride) {
            // Load properties from file afterwards, to let those properties override.
            loadProperties(result);
        }

        return result;
    }

    private void loadProperties(Properties props) throws IOException {
        loadProperties(props, null);
    }
    /**
     * Load properties into the given instance.
     * @param props the Properties instance to load into
     * @param locs location
     * @throws java.io.IOException in case of I/O errors
     * @see #setLocations
     */
    private void loadProperties(Properties props, Resource[] locs) throws IOException {
        if(locs==null){
            locs = this.locations;
        }
        if (locs != null) {
            Resource location;
            for (int i = 0,n=locs.length; i < n; i++) {
                location = locs[i];
                if (logger.isInfoEnabled()) {
                    logger.info("Loading config from " + location);
                }
                InputStream is = null;
                try {
                    is = location.getInputStream();
                    if (this.isSupportXmlFile && location.getFilename().endsWith(XML_FILE_EXTENSION)) {
                        this.propertiesPersister.loadFromXml(props, is);
                    }else {
                        if (this.fileEncoding != null) {
                            this.propertiesPersister.load(props, new InputStreamReader(is, this.fileEncoding));
                        }else {
                            this.propertiesPersister.load(props, is);
                        }
                    }
                }catch (IOException ex) {
                    if (this.ignoreResourceNotFound) {
                        if (logger.isWarnEnabled()) {
                            logger.warn("Could not load properties from " + location + ": " + ex.getMessage());
                        }
                    }else {
                        throw ex;
                    }
                }finally {
                    if (is != null) {
                        is.close();
                    }
                }
            }
        }
    }

    public String getProjectRootConfigFilePath() {
        return projectRootConfigFilePath;
    }

    public void setProjectRootConfigFilePath(String projectRootConfigFilePath) {
        this.projectRootConfigFilePath = projectRootConfigFilePath;
    }

    public void setSupportXmlFile(final boolean supportXmlFile) {
        isSupportXmlFile = supportXmlFile;
    }
}
