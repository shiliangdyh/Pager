package com.i000phone.pager.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class AllGallery extends Response {
    private int total;
    private List<Gallery> list;
    public AllGallery(String json) {
        super(json);
        try {
            list = new ArrayList<>();
            Gallery gallery = null;
            JSONObject jsonObject = new JSONObject(json);
            total = jsonObject.optInt("total");
            JSONArray jsonArray = jsonObject.optJSONArray("tngou");
            for (int i = 0; i < jsonArray.length(); i++) {
                gallery = new Gallery();
                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                gallery.setId(jsonObject1.optInt("id"));
                gallery.setImg(jsonObject1.optString("img"));
                gallery.setTitle(jsonObject1.optString("title"));
                gallery.setSize(jsonObject1.optInt("size"));
                list.add(gallery);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Gallery> getList() {
        return list;
    }

    public void setList(List<Gallery> list) {
        this.list = list;
    }

    public static class Gallery{
        private int id;
        private String img;
        private String title;
        private int size;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
