package com.zibengou.awful.backend.utils;

import org.apache.http.client.methods.HttpHead;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NetUtil {

    public static Boolean head(String url) {
        try {
            HttpHead h = new HttpHead(url);
            String type = h.getFirstHeader("content-type").getValue();
            return type == null;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean checkUrl(String url) {
        try {
            URL u = new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static String checkUrls(List<String> urls) {
        if (urls == null) {
            return null;
        }
        for (String url : urls) {
            try {
                new URL(url);
            } catch (Exception e) {
                return url;
            }
        }
        return null;
    }
}
