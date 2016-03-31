package com.i000phone.pager.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.i000phone.pager.R;
import com.i000phone.pager.entities.AllGallery;
import com.i000phone.pager.utils.NetWorkDrawable;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class PerCategoryImgAdapter extends BaseAdapter {
    private Context context;
    private List<AllGallery.Gallery> list;

    public PerCategoryImgAdapter(Context context, List<AllGallery.Gallery> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gallery,parent,false);
            ViewHolder vh = new ViewHolder(convertView);
            Resources resources = context.getResources();
            Bitmap loading = BitmapFactory.decodeResource(resources, R.mipmap.coming);
            Bitmap failure = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher);
            NetWorkDrawable netWorkDrawable = new NetWorkDrawable(resources, loading, failure);
            vh.item_pic.setImageDrawable(netWorkDrawable);
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        AllGallery.Gallery gallery = list.get(position);
        vh.item_title.setText(gallery.getTitle());
        NetWorkDrawable drawable = (NetWorkDrawable) vh.item_pic.getDrawable();
        drawable.setImageUrl("http://tnfs.tngou.net/image"+gallery.getImg()+"_120x120");
        return convertView;
    }
    public static class ViewHolder{
        private final ImageView item_pic;
        private final TextView item_title;

        public ViewHolder(View itemView) {
            item_pic = (ImageView) itemView.findViewById(R.id.item_pic);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
        }
    }
    public void addAll(Collection<? extends AllGallery.Gallery> collection){
        list.addAll(collection);
        this.notifyDataSetChanged();
    }
}
