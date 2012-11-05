package com.message.main.letter.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.pagination.PaginationSupport;
import com.message.base.pagination.PaginationUtils;
import com.message.base.utils.NumberUtils;
import com.message.base.utils.StringUtils;
import com.message.main.ResourceType;
import com.message.main.letter.dao.LetterDAO;
import com.message.main.letter.pojo.Letter;
import com.message.main.letter.pojo.LetterUserRelation;
import com.message.main.letter.service.LetterService;
import com.message.main.login.pojo.LoginUser;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

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
	private UserService userService;
	
	public void setLetterDAO(LetterDAO letterDAO) {
		this.letterDAO = letterDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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

	public PaginationSupport getInbox(LoginUser loginUser, int start, int num) throws Exception {
		if(loginUser == null || loginUser.getPkId() == null){
			logger.warn("not login!");
			return PaginationUtils.getNullPagination();
		}
		
		PaginationSupport ps = this.letterDAO.getInbox(loginUser.getPkId(), start, num);
		List relations = ps.getItems();
		for(int i = 0; i < relations.size(); i++){
			LetterUserRelation r = (LetterUserRelation) relations.get(i);
			if(r.getLetterId() == null)
				continue;
			
			Letter l = this.getLetter(r.getLetterId());
			
			if(l != null)
				r.setLetter(l);
		}
		
		return ps;
	}

	public Letter getLetter(Long pkId) throws Exception {
		if(pkId == null || Long.valueOf(-1).equals(pkId)){
			logger.warn("given no letter id!");
			return null;
		}
		
		Letter letter = this.letterDAO.loadObject(Letter.class, pkId);
		if(letter != null){
			User user = this.userService.getUserById(letter.getCreatorId());
			letter.setCreator(user);
		}
		
		return letter;
	}

	public List<LetterUserRelation> getReleationByLetter(Long letterId) throws Exception {
		if(letterId == null || Long.valueOf(-1).equals(letterId)){
			logger.warn("given no letter id!");
			return Collections.emptyList();
		}
		
		List<LetterUserRelation> lurs = this.letterDAO.getReleationByLetter(letterId);
		for(LetterUserRelation lur : lurs){
			if(lur == null || lur.getReceiverId() == null)
				continue;
			
			User receiver = this.userService.getUserById(lur.getReceiverId());
			if(receiver != null)
				lur.setReceiver(receiver);
			
		}
		
		return lurs;
	}

	public boolean setReadOrUnRead(String letterIds, LoginUser loginUser, boolean setRead) throws Exception {
		if(StringUtils.isEmpty(letterIds) || loginUser == null){
			logger.warn("letter id is null or not login!");
			return false;
		}
		
		String[] ids = StringUtils.split(letterIds, ",");
		
		for(String id : ids){
			if(StringUtils.isEmpty(id) || !NumberUtils.isNumber(id)){
				continue;
			}
			
			Long letterId = Long.valueOf(id);
			LetterUserRelation lur = this.letterDAO.getRelation(letterId, loginUser.getPkId());
			if(lur == null){
				logger.warn("can't get any relation by given letterId:'{}' and receiverId:'{}'!", 
						letterId, loginUser.getPkId());
				continue;
			}
			
			if(setRead && ResourceType.READ_NO.equals(lur.getRead()))
				lur.setRead(ResourceType.READ_YES);
			else if(!setRead && ResourceType.READ_YES.equals(lur.getRead()))
				lur.setRead(ResourceType.READ_NO);
			else
				return false;
			
			this.letterDAO.updateObject(lur);
		}
		
		return true;
	}

	public boolean delete(String letterIds, boolean isInbox, LoginUser loginUser) throws Exception {
		if(StringUtils.isEmpty(letterIds) || loginUser == null){
			logger.warn("given no letter ids!");
			return false;
		}
		
		String[] letterIdsTmp = StringUtils.split(letterIds, ",");
		List<Long> pkIds = new ArrayList<Long>();
		for(String id : letterIdsTmp){
			if(StringUtils.isNotEmpty(id) && NumberUtils.isNumber(id))
				pkIds.add(Long.valueOf(id));
		}
		
		if(isInbox)
			return this.letterDAO.deleteInbox(pkIds, loginUser.getPkId());
		else
			return false;	//删除发件箱
	}

}
