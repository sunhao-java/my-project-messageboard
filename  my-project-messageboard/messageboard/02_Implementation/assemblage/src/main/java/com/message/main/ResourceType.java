package com.message.main;

import com.message.base.properties.SystemConfig;

/**
 * 资源类型类
 * @author sunhao(sunhao.java@gmail.com)
 */
public final class ResourceType {
	
	/**
	 * 没有被删除
	 */
	public static final Long DELETE_NO = 0L;
	public static final Integer DELETE_NO_INTEGER = Integer.valueOf(0);
	/**
	 * 已删除
	 */
	public static final Long DELETE_YES = 1L;
	public static final Integer DELETE_YES_INTEGER = Integer.valueOf(1);
	
	/**
	 * 未审核
	 */
	public static final Long AUDIT_NOAUDIT = 0L;
	/**
	 * 审核通过
	 */
	public static final Long AUDIT_YES = 1L;
	/**
	 * 未通过审核
	 */
	public static final Long AUDIT_NO = 2L;
	
	/**
	 * 每页显示的条数
	 */
	public static final int PAGE_NUM = 10;
	
	/**
	 * session中登录者的key
	 */
	public static final String LOGIN_USER_KEY_IN_SESSION = "loginUser";
	
	/**
	 * 登录页面的URL
	 */
	public static final String LOGIN_PAGE_URL = "/user/inLogin.do";
	
	/**
	 * 是管理员的标识
	 */
	public static final Long IS_ADMIN_YES = 1L;
	/**
	 * 不是管理员的标识
	 */
	public static final Long IS_ADMIN_NO = 0L;
	
	public static final String AJAX_STATUS = "status";
	/**
	 * ajax请求成功
	 */
	public static final String AJAX_SUCCESS = "1";
	/**
	 * ajax请求失败
	 */
	public static final String AJAX_FAILURE = "0";
	
	/**
	 * 标准的日期格式
	 */
	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm";
	/**
	 * 日期格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	//下面是操作类型的标识
	/**
	 * 增加
	 */
	public static final Long EVENT_ADD = 1L;
	/**
	 * 删除
	 */
	public static final Long EVENT_DELETE = 2L;
	/**
	 * 编辑
	 */
	public static final Long EVENT_EDIT = 3L;
	
	//下面是模块的标识
	/**
	 * 留言模块
	 */
	public static final int MESSAGE_TYPE = 1;
	/**
	 * 用户模块
	 */
	public static final int USER_TYPE = 2;
	/**
	 * 留言板信息模块
	 */
	public static final int INFO_TYPE = 3;
	/**
	 * 回复模块
	 */
	public static final int REPLY_TYPE = 4;
	
	/**
	 * 初始默认密码
	 */
	public static final String DEFAULT_PASSWORD = "123456";
	
	/**
	 * 已进行邮箱验证标识
	 */
	public static final Long MAIL_CHECK_YES = Long.valueOf(1);
	
	/**
	 * 未进行邮箱验证标识
	 */
	public static final Long MAIL_CHECK_NO = Long.valueOf(0);
	
	//下面是用户个人隐私设置的标识
	/**
	 * 所有人可见
	 */
	public static final Long LOOK_ALL_PROPLE = Long.valueOf(0L);
	/**
	 * 只有自己可见
	 */
	public static final Long LOOK_ONLY_SELF = Long.valueOf(1L);
	/**
	 * 只有好友可见
	 */
	public static final Long LOOK_ONLY_FRIENDS = Long.valueOf(2L);
	
	//用户头像的一些常量
	/**
	 * 用户上传头像放置的路径
	 */
	public static final String USER_IMAGE_FOLDER_PATH = SystemConfig.getProperty("user.image.upload.dir", "/opt/application/data/head/");
	/**
	 * 大头像
	 */
	public static final String IMAGE_SIZE_BIG = "200x200";
	/**
	 * 普通头像
	 */
	public static final String IMAGE_SIZE_NORMAL = "100x100";
	/**
	 * 小头像
	 */
	public static final String IMAGE_SIZE_SMALL ="48x48";
	/**
	 * 头像尺寸的数组
	 */
	public static final String[] IMAGE_SIZE_LIST = new String[]{IMAGE_SIZE_BIG, IMAGE_SIZE_NORMAL, IMAGE_SIZE_SMALL};

    /**
     * 留言的资源类型
     */
    public static final Integer RESOURCE_TYPE_MESSAGE = Integer.valueOf(1);
    /**
     * 相册图片的资源类型
     */
    public static final Integer RESOURCE_TYPE_PHOTO = Integer.valueOf(2);
    /**
     * 水印的资源类型
     */
    public static final Integer RESOURCE_TYPE_MARK = Integer.valueOf(3);
    /**
     * 吐槽的资源类型
     */
    public static final Integer RESOURCE_TYPE_TWEET = Integer.valueOf(4);
    /**
     * 相册模块的资源类型
     */
    public static final Integer RESOURCE_TYPE_ALBUM = Integer.valueOf(5);

    /**
     * 验证码在session中默认的ID
     */
    public static final String VERITY_CODE_KEY = "verityCode";
    
    //水印配置
    /**
     * 无水印
     */
    public static final Integer WATER_MASK_NO = Integer.valueOf(0);
    /**
     * 文字水印
     */
    public static final Integer CHARACTER_MASK = Integer.valueOf(1);
    /**
     * 图片水印
     */
    public static final Integer IMAGE_MASK = Integer.valueOf(2);
    /**
     * 文字水印
     */
    public static final String CHARACTER_MARK_STRING = "word";
    
    //好友
    /**
     * 目标用户是否同意(0未回答1同意2拒绝)
     */
    public static final Integer AGREE_NOANSWER = Integer.valueOf(0);
    public static final Integer AGREE_YES = Integer.valueOf(1);
    public static final Integer AGREE_NO = Integer.valueOf(2);
	
    //返回类型
    //返回PkId
    public static final Integer RETURN_PKID = Integer.valueOf(1);
    //返回其他
    public static final Integer RETURN_OTHER = Integer.valueOf(2);
    
    //站内信标识
    /**
     * 是否是站内信回复(1站内信0站内信回复)
     */
    public static final Long IS_LETTER_YES = Long.valueOf(1);
    public static final Long IS_LETTER_NO = Long.valueOf(0);
    /**
     * 是否是已读(1已读0未读)
     */
    public static final Integer READ_YES = Integer.valueOf(1);
    public static final Integer READ_NO = Integer.valueOf(0);
}
