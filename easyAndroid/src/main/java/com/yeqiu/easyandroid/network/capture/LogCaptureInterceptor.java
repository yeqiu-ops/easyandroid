package com.yeqiu.easyandroid.network.capture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @project: Answer
 * @author: 小卷子
 * @date: 2022/5/5
 * @describe:
 * @fix:
 */
public class LogCaptureInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request request = chain.request();

        NetLog netLog = new NetLog();

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        netLog.requestMethod = request.method() + "   " + protocol;
        netLog.requestUrl = request.url().toString();


        StringBuffer headerBuffer = new StringBuffer();
        if (hasRequestBody) {
            if (requestBody.contentType() != null) {
                headerBuffer.append("Content-Type: " + requestBody.contentType()).append("\n");
            }
            if (requestBody.contentLength() != -1) {
                headerBuffer.append("Content-Length: " + requestBody.contentLength()).append("\n");
            }
        }

        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                headerBuffer.append(name + ": " + headers.value(i)).append("\n");
            }
        }
        netLog.requestHeader = headerBuffer.toString();

        if ((!bodyEncoded(request.headers()))) {
            Buffer buffer = new Buffer();
            if (requestBody != null) {
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                StringBuffer requestBodyBuffer = new StringBuffer();
                if (isPlaintext(buffer)) {
                    requestBodyBuffer.append(buffer.readString(charset)).append("\n");
                    requestBodyBuffer.append("(" + requestBody.contentLength() + "-byte body)").append("\n");
                } else {
                    requestBodyBuffer.append(request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)").append("\n");
                }
                netLog.requestBody = requestBodyBuffer.toString();
            }
        }

        long startMillis = System.currentTimeMillis();
        Response response;

        response = chain.proceed(request);


        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();


        netLog.statusCode = response.code();
        netLog.isSuccess = response.isSuccessful();
        long tookTime = 0;
        long ReceivedIn = System.currentTimeMillis() - startMillis;
        if (ReceivedIn > 1000) {
            tookTime = TimeUnit.MILLISECONDS.toSeconds(ReceivedIn);
            netLog.time = tookTime + "s";
        } else {
            tookTime = TimeUnit.MILLISECONDS.toMillis(ReceivedIn);
            netLog.time = tookTime + "ms";
        }

        Headers respHeaders = response.headers();
        StringBuffer responseHeader = new StringBuffer();
        for (int i = 0, count = respHeaders.size(); i < count; i++) {
            responseHeader.append(respHeaders.name(i) + ": " + respHeaders.value(i)).append("\n");
        }
        netLog.responseHeader = responseHeader.toString();

        if (!bodyEncoded(response.headers())) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (!isPlaintext(buffer)) {
                netLog.responseBody = "非文本信息";
            } else {
                if (contentLength != 0) {
                    netLog.responseBody = unicodeToString(buffer.clone().readString(charset));
                }
            }
        }


        NetLogDao.addLog(netLog);


        return response;
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }


    private String getJsonString(String str) {
        String json = "";
        try {
            if (str.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(str);
                json = jsonObject.toString(3);

            } else if (str.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(str);
                json = jsonArray.toString(3);
            } else {
                json = str;
            }

            json = json.replace("\\/", "/");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

}
