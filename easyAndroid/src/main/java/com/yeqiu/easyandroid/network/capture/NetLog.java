package com.yeqiu.easyandroid.network.capture;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Stack;

/**
 * author:DingDeGao
 * time:2019-10-31-15:15
 * function: UIEntity
 */
public class NetLog extends LitePalSupport implements Serializable {


    private long id;

    public String requestMethod = "";

    public String requestUrl = "";

    public String requestHeader = "";

    public String requestBody = "";


    public String responseHeader = "";

    public String responseBody = "";

    public String time;

    public int statusCode;

    public boolean isSuccess;

    public long date;


    private String lineSeparator = System.getProperty("line.separator");
    private String doubleSeparator = lineSeparator + lineSeparator;


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("------------Request------------")
                .append(doubleSeparator)
                .append("URL: ")
                .append(requestUrl)
                .append(doubleSeparator)
                .append("Method: ")
                .append(requestMethod)
                .append(doubleSeparator)
                .append("RequestHeader: ")
                .append(lineSeparator)
                .append(requestHeader)
                .append(doubleSeparator)
                .append("RequestBody: ")
                .append(lineSeparator)
                .append(formatJson(requestBody))
                .append(doubleSeparator);


        sb.append("------------Response------------")
                .append(doubleSeparator)
                .append("Received in:")
                .append(time)
                .append(doubleSeparator)
                .append("isSuccess :")
                .append(isSuccess)
                .append(doubleSeparator)
                .append("Status Code: ")
                .append(statusCode)
                .append(doubleSeparator)
                .append("ResponseHeader:")
                .append(lineSeparator)
                .append(responseHeader)
                .append(doubleSeparator)
                .append("ResponseBody: ")
                .append(lineSeparator)
                .append(formatJson(responseBody))
                .append(doubleSeparator);

        return sb.toString();

    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * 格式化JSON字符串
     */
    public  String formatJson(String originStr) {


        try{
            if (originStr == null) {
                return null;
            }
            char[] charArray = originStr.toCharArray();
            StringBuilder sb = new StringBuilder();

            int tabCount = 0;
            // 可以不用stack,在满足 '}' 的if语句内更换 int temp = tabCount-1即可
            Stack<Integer> stack = new Stack<>();
            char c;
            for (int i = 0; i < charArray.length; i++) {
                c = charArray[i];
                // 换行
                if (c == '{') {
                    enterAndTabs(sb, tabCount);
                    stack.push(tabCount++);
                    sb.append(c);
                    continue;
                }
                // 换行
                if (c == '"'
                        && (charArray[i - 1] == '{' || charArray[i - 1] == ',')) {
                    enterAndTabs(sb, tabCount);
                    sb.append(c);
                    continue;
                }
                // 换行
                if (c == '}') {
                    enterAndTabs(sb, stack.pop());
                    sb.append(c);
                    tabCount--;
                    continue;
                }
                sb.append(c);
            }
            return sb.toString();
        }catch ( Exception e){

            return "";
        }


    }

    /**
     * 回车与制表符
     *
     * @param sb StringBuilder
     * @param tabCount 多少个制表符
     */
    private  void enterAndTabs(StringBuilder sb, int tabCount) {
        sb.append("\n");
        while (tabCount-- > 0) {
            sb.append("    ");
        }
    }



}
