package com.message.main.letter.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.message.main.login.web.AuthContextHelper;
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

	public PaginationSupport getInbox(LoginUser loginUser, Integer read, int start, int num) throws Exception {
		if(loginUser == null || loginUser.getPkId() == null){
			logger.warn("not login!");
			return PaginationUtils.getNullPagination();
		}
		
		PaginationSupport ps = this.letterDAO.getInbox(loginUser.getPkId(), read, start, num);
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
		if(letter == null)
			return null;
		
		if(letter.getPkId() != null){
			List<LetterUserRelation> relations = this.getReleationByLetter(letter.getPkId());
			letter.setRelations(relations);
		}
		
		if(letter.getCreatorId() != null){
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

	public boolean setReadOrUnRead(String luids, LoginUser loginUser, boolean setRead) throws Exception {
		if(StringUtils.isEmpty(luids) || loginUser == null){
			logger.warn("letter user relation id is null or not login!");
			return false;
		}
		
		String[] ids = StringUtils.split(luids, ",");
		
		for(String id : ids){
			if(StringUtils.isEmpty(id) || !NumberUtils.isNumber(id)){
				continue;
			}
			
			Long luid = Long.valueOf(id);
			LetterUserRelation lur = this.letterDAO.loadObject(LetterUserRelation.class, luid);
			if(lur == null){
				logger.warn("can't get any relation by given letterId:'{}' and receiverId:'{}'!", 
						luid, loginUser.getPkId());
				continue;
			}
			
			if(setRead && ResourceType.READ_NO.equals(lur.getRead())){
				lur.setRead(ResourceType.READ_YES);
				lur.setAcceptTime(new Date());
			} else if(!setRead && ResourceType.READ_YES.equals(lur.getRead())){
				lur.setRead(ResourceType.READ_NO);
				lur.setAcceptTime(null);
			}
			
			this.letterDAO.updateObject(lur);
		}
		
		return true;
	}

	public boolean delete(String ids, boolean isInbox) throws Exception {
		if(StringUtils.isEmpty(ids)){
			logger.warn("given no letter ids!");
			return false;
		}
		
		String[] idsTmp = StringUtils.split(ids, ",");
		List<Long> pkIds = new ArrayList<Long>();
		for(String id : idsTmp){
			if(StringUtils.isNotEmpty(id) && NumberUtils.isNumber(id))
				pkIds.add(Long.valueOf(id));
		}
		
		if(isInbox)
			return this.letterDAO.deleteInbox(pkIds);
		else
			return this.letterDAO.deleteOutBox(pkIds);	//删除发件箱
	}

	public PaginationSupport getOutBox(LoginUser loginUser, int start, int num) throws Exception {
		if(loginUser == null || loginUser.getPkId() == null){
			logger.warn("not login!");
			return PaginationUtils.getNullPagination();
		}
		
		PaginationSupport ps = this.letterDAO.getOutBox(loginUser.getPkId(), start, num);
		List<Letter> letters = ps.getItems();
		for(int i = 0; i < letters.size(); i++){
			Letter l = letters.get(i);
			if(l != null && l.getPkId() != null){
				letters.set(i, this.getLetter(l.getPkId()));
			}
		}
		
		return ps;
	}

	public boolean sendLetter(String title, String content, Long receiverId) throws Exception {
		if(StringUtils.isEmpty(title) || StringUtils.isEmpty(content) || receiverId == null){
			logger.warn("must given enough parameter!");
			return false;
		}
		
		return this.sendLetter(title, content, Arrays.asList(receiverId));
	}

	public boolean sendLetter(String title, String content, List<Long> receiverIds) throws Exception {
		if(StringUtils.isEmpty(title) || StringUtils.isEmpty(content) || receiverIds.isEmpty()){
			logger.warn("must given enough parameter!");
			return false;
		}
		LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		if(loginUser == null){
			logger.warn("send letter...not login...");
			return false;
		}
		
		Letter letter = new Letter();
		letter.setTitle(title);
		letter.setContent(content);
		
		StringBuffer ids = new StringBuffer();
		for(Long id : receiverIds){
			ids.append(id).append(",");
		}
		if(ids.length() > 0 && ids.lastIndexOf(",") != -1){
			ids.substring(0, ids.length() - 1);
		}
		
		return this.send(ids.toString(), letter, loginUser);
	}
}
