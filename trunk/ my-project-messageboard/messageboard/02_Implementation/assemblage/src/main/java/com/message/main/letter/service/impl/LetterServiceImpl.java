package com.message.main.letter.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.utils.NumberUtils;
import com.message.base.utils.StringUtils;
import com.message.main.ResourceType;
import com.message.main.letter.dao.LetterDAO;
import com.message.main.letter.pojo.Letter;
import com.message.main.letter.pojo.LetterUserRelation;
import com.message.main.letter.service.LetterService;
import com.message.main.login.pojo.LoginUser;

/**
 * 站内信的service实现.
 * 
 * @author Danny(sunhao)(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-10-22 下午09:21:01
 */
public class LetterServiceImpl implements LetterService {
	private final static Logger logger = LoggerFactory.getLogger(LetterServiceImpl.class);
	
	private LetterDAO letterDAO;
	
	public void setLetterDAO(LetterDAO letterDAO) {
		this.letterDAO = letterDAO;
	}

	public boolean send(String receiverIds, Letter letter, LoginUser loginUser) throws Exception {
		if(StringUtils.isEmpty(receiverIds) || letter == null || loginUser == null){
			logger.warn("receivers is null! or letter is null! or not login!");
			return false;
		}
		String[] ids = StringUtils.split(receiverIds, ",");
		
		//先保存主题信息
		letter.setCreatorId(loginUser.getPkId());
		letter.setDeleteFlag(ResourceType.DELETE_NO);
		letter.setSendTime(new Date());
		letter.setIsReply(ResourceType.IS_LETTER_YES);
		
		this.letterDAO.save(letter);
		
		//保存站内信与用户的关系
		for(String id : ids){
			if(NumberUtils.isNumber(id)){
				Long userId = Long.valueOf(id);
				LetterUserRelation relation = new LetterUserRelation();
				relation.setReceiverId(userId);
				relation.setDeleteFlag(ResourceType.DELETE_NO);
				relation.setLetterId(letter.getPkId());
				relation.setRead(ResourceType.READ_NO);
				
				this.letterDAO.save(relation);
			}
		}
		
		return true;
	}

}
