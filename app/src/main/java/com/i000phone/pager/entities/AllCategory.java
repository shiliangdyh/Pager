package com.i000phone.pager.entities;

import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class AllCategory extends Response{
    private List<PerCategory> list;

    public AllCategory(String json) {
        super(json);
        try {
            list = new ArrayList<>();
            PerCategory perCategory = null;
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray("tngou");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                perCategory = new PerCategory();
                perCategory.setId(jsonObject1.optInt("id"));
                perCategory.setTitle(jsonObject1.optString("title"));
                list.add(perCategory);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<PerCategory> getList() {
        return list;
    }

    public void setList(List<PerCategory> list) {
        this.list = list;
    }

    public static class PerCategory{
        private int id;
        private String title;

        public PerCategory() {
        }

        public PerCategory(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
