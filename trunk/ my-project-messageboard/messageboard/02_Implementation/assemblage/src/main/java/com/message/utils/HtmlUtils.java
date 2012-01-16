package com.message.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {
	
	public static String subHtmlCode(final int length, final String html, final String suffix) {
        if (StringUtils.isBlank(html)) {
            return StringUtils.EMPTY;
        }

        StringBuffer result = new StringBuffer(html.length());
        // remove XML tag.
        Pattern pattern = Pattern.compile("(?s)<xml>(.*?)</xml>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            matcher.appendReplacement(result, StringUtils.EMPTY);
        }

        matcher.appendTail(result);
        // style tag.
        pattern = Pattern.compile("(?s)<style[^>]*>(.*?)</style>", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(result.toString());
        result.setLength(0);

        while (matcher.find()) {
            matcher.appendReplacement(result, StringUtils.EMPTY);
        }

        matcher.appendTail(result);
        // script tag.
        pattern = Pattern.compile("(?s)<script[^>]*>(.*?)</script>", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(result.toString());
        result.setLength(0);

        while (matcher.find()) {
            matcher.appendReplacement(result, StringUtils.EMPTY);
        }

        matcher.appendTail(result);
        // all the tag.
        pattern = Pattern.compile("(?s)<[^>]+>", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(result.toString());
        result.setLength(0);

        while (matcher.find()) {
            matcher.appendReplacement(result, StringUtils.EMPTY);
        }

        matcher.appendTail(result);
        // remove HTML characters, such as: &xxx;
        pattern = Pattern.compile("&[\\w]+;", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(result.toString());
        result.setLength(0);

        while (matcher.find()) {
            matcher.appendReplacement(result, StringUtils.EMPTY);
        }

        matcher.appendTail(result);
        // replace multi-space character with single space.
        pattern = Pattern.compile("(?s)[\\s]{2,}");
        matcher = pattern.matcher(result.toString());
        result.setLength(0);

        while (matcher.find()) {
            matcher.appendReplacement(result, " ");
        }

        matcher.appendTail(result);
        // first letter.
        pattern = Pattern.compile("^[\\s]+");
        matcher = pattern.matcher(result.toString());
        result.setLength(0);

        if (matcher.find()) {
            matcher.appendReplacement(result, StringUtils.EMPTY);
        }

        matcher.appendTail(result);

        if (length < 1 || result.length() < (length / 2)) {
            return result.toString();
        } else {
            StringBuffer ret = new StringBuffer(length + 2);
            int count = 0;

            for (int i = 0; i < result.length(); i++) {
                count++;
                char c = result.charAt(i);

                // for non-ASCCI characters.
                if (c < 0 || c > 128) {
                    count++;
                }

                if (count > length) {
                    ret.append(suffix);

                    break;
                }

                ret.append(c);
            }

            return ret.toString();
        }
    }
}
