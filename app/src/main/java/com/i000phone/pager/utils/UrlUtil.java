package com.i000phone.pager.utils;

/**
 * Created by jash on 16-3-16.
 */
public class UrlUtil {
    public static final String IMAGE_CATEGORY_URL = "http://www.tngou.net/tnfs/api/classify";
    private static final String PER_CATEGORY_URL = "http://www.tngou.net/tnfs/api/list?id=%d&page=%d&rows=%d";
    private static final String IMAGE_DETAIL_URL = "http://www.tngou.net/tnfs/api/show?id=%d";

    public static String getPerCategoryUrl(int id, int page, int rows){
        return String.format(PER_CATEGORY_URL, id, page, rows);
    }
    public static String getImageDetailUrl(int id){
        return String.format(IMAGE_DETAIL_URL, id);
    }
}
