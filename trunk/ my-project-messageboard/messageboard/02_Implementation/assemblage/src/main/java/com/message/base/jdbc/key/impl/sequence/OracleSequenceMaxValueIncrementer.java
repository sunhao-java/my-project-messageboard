package com.message.base.jdbc.key.impl.sequence;


/**
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2012-4-11 上午08:57:30
 */
public class OracleSequenceMaxValueIncrementer extends AbstractSequenceMaxValueIncrementer {

	public String getQuerySequence(String name) {
		return "select " + name + ".nextval from dual";
	}

}
