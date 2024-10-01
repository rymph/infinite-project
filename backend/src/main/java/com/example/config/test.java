package com.example.config;

import com.example.common.util.CookieUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String REGEX = "(.*)(ee)(.*)?";
        System.out.println(Pattern.UNIX_LINES);
        System.out.println(Pattern.CASE_INSENSITIVE);
        System.out.println(Pattern.COMMENTS);
        System.out.println(Pattern.MULTILINE);
        System.out.println(Pattern.LITERAL);
        System.out.println(Pattern.DOTALL);
        System.out.println(Pattern.UNICODE_CASE);
        System.out.println(Pattern.CANON_EQ);
        System.out.println(Pattern.UNICODE_CHARACTER_CLASS);
        StringBuilder sb = new StringBuilder();
        sb.append(URLEncoder.encode("email=" + "ws0501urm@naver.com", "UTF-8"));
        sb.append("&");
        sb.append(URLEncoder.encode("provider=" + "kakao", "UTF-8"));
        String value = sb.toString();
        System.out.println(value);
    }
}
