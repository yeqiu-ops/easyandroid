package com.yeqiu.easyandroid.utils;

/**
 * @project: LearningMachine
 * @author: 小卷子
 * @date: 2022/10/28
 * @describe:
 * @fix:
 */
public class StringUtil {


    // 手机号码中间四位脱敏
    public static String encryptPhone(String phone) {
        if (CommonUtil.isEmpty(phone) || (phone.length() != 11)) {
            return phone;
        }
        return phone.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
    }

    // 身份证号码脱敏
    public static String encryptIdentity(String identity) {
        if (CommonUtil.isEmpty(identity) || (identity.length() != 18)) {
            return identity;
        }
        return identity.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
    }


    public static String encryptName(String name) {
        if (CommonUtil.isEmpty(name) || name.length() <= 1) {
            return name;
        }
        if (name.length() == 2) {
            return name.substring(0, 1) + "*";//截取name 字符串截取第一个字符，
        } else if (name.length() == 3) {
            return name.substring(0, 1) + "*" + name.substring(2, 3);//截取第一个和第三个字符
        } else if (name.length() == 4) {
            return "**" + name.substring(2, 4);//截取第一个和大于第4个字
        } else if (name.length() > 4) {
            return name.substring(0, 2) + "*" + '*' + name.substring(name.length() - 1);//截取第一个和大于第4个字
        }
        return "";
    }

}
