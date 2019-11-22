 package com.longge.common.util;

import com.google.common.base.CharMatcher;

/**
 * @author roger yang
 * @date 6/25/2019
 */
public class TrimUtils {
    /**
     * trim left and right char to "",chars like tab/chinese space/enter/english space
     * @return
     */
    public static String trimAnyBlank(String str) {
        if(null != str) {
        	// String phone = "‭15868139680"; 该字符串实际的字符长度是 12, 其中15前面有一个看不见的字符, 既不是中文空格也不是英文空格.
    		// System.out.println(TrimUtils.trimAnyBlank(phone).length());
            return CharMatcher.anyOf("\r\n\t \u00A0　‭").trimFrom(str);
        }
        return str;
    }
}
