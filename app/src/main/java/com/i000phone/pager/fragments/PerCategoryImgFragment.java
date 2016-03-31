package com.i000phone.pager.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.i000phone.pager.R;
import com.i000phone.pager.adapters.PerCategoryImgAdapter;
import com.i000phone.pager.entities.AllGallery;
import com.i000phone.pager.utils.HttpTask;
import com.i000phone.pager.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerCategoryImgFragment extends Fragment implements HttpTask.Callback<AllGallery> {

    private PerCategoryImgAdapter adapter;
    public PerCategoryImgFragment() {
    }

    public static PerCategoryImgFragment newInstance(int id, String title) {

        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("title",title);
        PerCategoryImgFragment fragment = new PerCategoryImgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_per_category_img, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        GridView gridView = (GridView) view.findViewById(R.id.fragment_grid);
        adapter = new PerCategoryImgAdapter(getContext(),new ArrayList<AllGallery.Gallery>());
        gridView.setAdapter(adapter);
        HttpTask<AllGallery> task = new HttpTask<>(AllGallery.class, this);
        task.execute(UrlUtil.getPerCategoryUrl(1,1,20));
    }

    @Override
    public void doResponse(AllGallery allGallery) {
        adapter.addAll(allGallery.getList());
    }

    @Override
    public void doFailure(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
