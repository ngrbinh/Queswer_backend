package com.bdt.queswer.security;

public class Constants {
    public static final String JWT_SECRET = "bdtvl";
    public static final long JWT_EXPIRATION = 604800000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String[] PERMIT_ALL = {
            "/user/signup","/user/all",
            "question/id",
    };
    public static final String[] POST_PERMIT_ALL = {
            "/user/signup",
    };
    public static final String[] GET_PERMIT_ALL = {
            "/user/all","/user/{id:\\d+}",
            "/post/question/all","/post/question/{id:\\d+}",
            "/address/all","/subject/all","/grade/all",
    };
}
