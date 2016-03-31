package com.i000phone.pager.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.i000phone.pager.entities.AllCategory;
import com.i000phone.pager.fragments.PerCategoryImgFragment;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class AllCategoryAdapter extends FragmentPagerAdapter {
    private List<AllCategory.PerCategory> list;
    public AllCategoryAdapter(FragmentManager fm,List<AllCategory.PerCategory> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        AllCategory.PerCategory perCategory = list.get(position);
        return PerCategoryImgFragment.newInstance(perCategory.getId(),perCategory.getTitle());
}

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
    public void addAll(Collection<? extends AllCategory.PerCategory> collection){
        list.addAll(collection);
        this.notifyDataSetChanged();
    }
}
