package com.message.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.message.base.utils.StringUtils;

/**
 * 进行投票结果的处理
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-20 下午09:59:24
 */
public class FormatVoteAnswer extends TagSupport {
	private static final long serialVersionUID = -1696268477782286341L;
	
	//对于此投票的选择项，是以","分割的字符串
	private String voteAnswer;

	public void setVoteAnswer(String voteAnswer) {
		this.voteAnswer = voteAnswer;
	}

	public int doEndTag() throws JspException {
		if(StringUtils.isEmpty(voteAnswer)){
			return EVAL_PAGE;
		}
		
		//分割回答
		String[] answers = voteAnswer.split(",");
		
		//待显示的html
		StringBuffer answerDiv = new StringBuffer();
		
		for(int i = 0; i < answers.length; i++){
			String answer = answers[i];
			//组装待显示的html
			answerDiv.append("<li class=\"vote-color-" + i % 10 + "\"");
			answerDiv.append("style=\"margin-top: 2px; padding-top: 0px; border-bottom-width: 0px; height: 18px;\">");
			answerDiv.append(answer);
			answerDiv.append("</li>");
			
		}
		
		print(answerDiv.toString());
		
		return super.doEndTag();
	}
	
	/**
	 * 打印出待显示的html
	 * 
	 * @param answerDiv		待显示的html
	 */
	private void print(String answerDiv){
		try {
			this.pageContext.getOut().print(answerDiv);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
