package com.message.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * 对文本显示处理的标签
 */
public class CutWordTag extends TagSupport {
    private static final long serialVersionUID = 1L;
    private String cutString;
    private int len;
    private String endString = "...";

    public void setCutString(String cutString) throws JspTagException {
        this.cutString = cutString;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

	public int doEndTag() throws JspException {
        String out;


        if (StringUtils.isBlank(cutString)) {
            out = "...";
        } else {
            out = cutString;

            if (len >= 1 && out.length() >= (len / 2)) {
                StringBuffer ret = new StringBuffer(len + 3);
                int count = 0;

                for (int i = 0; i < out.length(); i++) {
                    count++;
                    char c = out.charAt(i);

                    if (c < 0 || c > 128) {
                        count++;
                    }

                    if (count > len) {
                        ret.append(endString);
                        break;
                    }

                    ret.append(c);
                }

                out = ret.toString();
            }

        }
        print(out);
        return EVAL_PAGE;
    }

    private void print(String out) throws JspTagException {
        try {
            pageContext.getOut().print(out);
        } catch (IOException ioe) {
            throw new JspTagException(ioe.toString(), ioe);
        }
    }

    public void release() {
        cutString = null;
        len = 0;
        endString = "...";
    }
}
