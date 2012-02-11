package com.message.main.vote.pojo;

import java.io.Serializable;

/**
 * 投票问题答案的选项
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-10 下午11:24:16
 */
public class VoteOption implements Serializable {
	private static final long serialVersionUID = -9041495326728492236L;

	private Long pkId; 				// 主键
	private Long voteId; 			// 对应投票的ID
	private String optionContent; 	// 答案选项的内容

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getVoteId() {
		return voteId;
	}

	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}

}
