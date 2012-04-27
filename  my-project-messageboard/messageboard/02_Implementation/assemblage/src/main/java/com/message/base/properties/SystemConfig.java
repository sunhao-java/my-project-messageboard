package com.message.base.properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Locale;
import java.util.Map;

/**
 * 国际化资源文件工具类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-8 上午12:17:21
 */
public final class SystemConfig implements InitializingBean {
    private static Logger log = LoggerFactory.getLogger(SystemConfig.class);

	private static Map systemProperties;

    public void setConfiguration(final Map properties) {
        systemProperties = properties;
    }

    public static String getProperty(final String key) {
        return (String) systemProperties.get(key);
    }

    public static String getTrimProperty(final String key) {
        return StringUtils.trimToEmpty((String) systemProperties.get(key));
    }

    public static String getProperty(final String key, final String defaultString) {
        String prop = getProperty(key);
        return StringUtils.isBlank(prop) ? defaultString : prop;
    }

    /**
     * 获取配置文件中的参数值.
     *
     * @param key 参数名称.
     * @return 参数值.
     */
    public static int getIntProperty(final String key) {
        return getIntProperty(key, 0);
    }

    /**
     * 获取配置文件中的参数值.
     *
     * @param key          参数名称.
     * @param defaultValue 默认值.
     * @return 参数值.
     */
    public static int getIntProperty(final String key, int defaultValue) {
        String property = StringUtils.trimToNull(getProperty(key));
        if (property == null) return defaultValue;
        int value;
        try {
            value = Integer.parseInt(property);
        } catch (NumberFormatException e) {
            value = defaultValue;
            log.warn(e.getMessage());
        }
        return value;
    }


    /**
     * 获取配置文件中的参数值.
     *
     * @param key          参数名称.
     * @param defaultValue 默认值.
     * @return 参数值.
     */
    public static long getLongProperty(final String key, long defaultValue) {
        String property = StringUtils.trimToNull(getProperty(key));
        if (property == null) {
            return defaultValue;
        }
        long value;
        try {
            value = Long.parseLong(property);
        } catch (NumberFormatException e) {
            value = defaultValue;
            log.warn(e.getMessage());
        }
        return value;
    }

    /**
     * 获取配置文件中的整型参数值.
     *
     * @param key 参数名称.
     * @return 整型参数值.
     */
    public static Integer getIntegerProperty(final String key) {
        return getIntegerProperty(key, null);
    }

    /**
     * 获取配置文件中的整型参数值.
     *
     * @param key          参数名称.
     * @param defaultValue 缺省值.
     * @return 整型参数值.
     */
    public static Integer getIntegerProperty(final String key, final Integer defaultValue) {
        String strVal = StringUtils.trimToNull(getProperty(key));
        Integer value = defaultValue;
        if (strVal != null) {
            try {
                value = Integer.parseInt(strVal);
            } catch (NumberFormatException e) {
                log.warn(e.getMessage());
            }
        }
        return value;
    }

    /**
     * 获取配置文件中的布尔参数值.
     *
     * @param key 参数名称.
     * @return 布尔参数值.
     */
    public static boolean getBooleanProperty(final String key) {
        return getBooleanProperty(key, false);
    }

    /**
     * 获取配置文件中的布尔参数.如果文件中没有该参数就返回defaultValue.
     *
     * @param key          参数名称.
     * @param defaultValue 参数默认值.
     * @return 布尔参数值.
     */
    public static boolean getBooleanProperty(final String key, final boolean defaultValue) {
        if (systemProperties == null) {
            return defaultValue;
        }

        Object value = systemProperties.get(key);

        if (null != value) {
            String strValue = StringUtils.trimToNull(value.toString());

            return "true".equalsIgnoreCase(strValue) || "on".equalsIgnoreCase(strValue)
                    || "yes".equalsIgnoreCase(strValue) || "1".equalsIgnoreCase(strValue);
        } else {
            return defaultValue;
        }
    }

    public static Map getProperties() {
        return systemProperties;
    }

    /**
     * 检查系统必须的配置参数是否设置
     *
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        //设置应用默认locale
        String localeFlag = StringUtils.trimToNull((String) systemProperties.get("ccs.defaultLocale"));
        Locale defaultLoacle = null;
        if (localeFlag != null) {
            defaultLoacle = org.springframework.util.StringUtils.parseLocaleString(localeFlag);
        }
        if (defaultLoacle == null) {
            defaultLoacle = Locale.CHINA;
        }
        Locale.setDefault(defaultLoacle);
        log.info("ccs default locale is '{}'", defaultLoacle);
    }
}
