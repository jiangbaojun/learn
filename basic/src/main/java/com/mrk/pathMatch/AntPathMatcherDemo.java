package com.mrk.pathMatch;

import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

import java.util.Map;

/**
 * AntPath路径风格匹配
 */
public class AntPathMatcherDemo {

    @Test
    public void test01() {
        AntPathMatcher pathMatcher = new AntPathMatcher();

        boolean match1 = pathMatcher.match("/user/getUserById/{userId}", "/user/getUserById/3");
        Map<String, String> pathParamMap1 = pathMatcher.extractUriTemplateVariables("/user/getUserById/{userId}", "/user/getUserById/3");
        boolean match2 = pathMatcher.match("/user/getUserById/{userId}", "/user/getUserById");

        System.out.println(match1); // true
        System.out.println(pathParamMap1); // {userId=3}
        System.out.println(match2); // false
    }

    @Test
    public void test02() {
        AntPathMatcher pathMatcher = new AntPathMatcher();

        boolean match1 = pathMatcher.match("/user/getUserById/*", "/user/getUserById/3");
        Map<String, String> pathParamMap1 = pathMatcher.extractUriTemplateVariables("/user/getUserById/{userId}", "/user/getUserById/3");
        boolean match11 = pathMatcher.match("/user/getUserById/**", "/user/getUserById/3");
        Map<String, String> pathParamMap11 = pathMatcher.extractUriTemplateVariables("/user/getUserById/**", "/user/getUserById/3");
        String s11 = pathMatcher.extractPathWithinPattern("/user/getUserById/**", "/user/getUserById/3");
        boolean match12 = pathMatcher.match("/user/getUserById/**", "/user/getUserById/a/b");
        String s12 = pathMatcher.extractPathWithinPattern("/user/getUserById/**", "/user/getUserById/a/b");
        boolean match13 = pathMatcher.match("/user/getUserById/*", "/user/getUserById/a/b");
        boolean match2 = pathMatcher.match("/user/getUserById/{userId}", "/user/getUserById");

        System.out.println(match1);  // true
        System.out.println(pathParamMap1); // {userId=3}
        System.out.println(match11); // true
        System.out.println(pathParamMap11); // {}
        System.out.println(s11); // 3
        System.out.println(match12); // true
        System.out.println(s12); // a/b
        System.out.println(match13); // false
        System.out.println(match2); // false
    }

    @Test
    public void test03() {
        AntPathMatcher pathMatcher = new AntPathMatcher();

        boolean m1 = pathMatcher.match("/a/*/c", "/a/b/c");
        String s1 = pathMatcher.extractPathWithinPattern("/a/*/c", "/a/b/c");
        boolean m2 = pathMatcher.match("/a/*/c", "/a/b/bb/c");
        boolean m3 = pathMatcher.match("/a/?/c", "/a/b/c");
        boolean m4 = pathMatcher.match("/a/?/c", "/a/bb/c");
        System.out.println(m1); // true
        System.out.println(s1); // b/c
        System.out.println(m2); // false
        System.out.println(m3); // true
        System.out.println(m4); // false

    }

    @Test
    public void test04() {
        AntPathMatcher pathMatcher = new AntPathMatcher();

        boolean m1 = pathMatcher.match("/a/**", "global/a/b/c");
        boolean m2 = pathMatcher.match("/a/**", "a/b/c");
        boolean m3 = pathMatcher.match("a/**", "/a/b/c");
        boolean m4 = pathMatcher.match("a/**", "a/b/c");
        boolean m5 = pathMatcher.match("/a/**", "/a/b/c");
        boolean m6 = pathMatcher.match("/a/**", "/a");
        System.out.println(m1); // false
        System.out.println(m2); // false
        System.out.println(m3); // false
        System.out.println(m4); // true
        System.out.println(m5); // true
        System.out.println(m6); // true

    }
}
