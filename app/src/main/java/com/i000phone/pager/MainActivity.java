package com.i000phone.pager;

import android.graphics.Color;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.i000phone.pager.adapters.AllCategoryAdapter;
import com.i000phone.pager.entities.AllCategory;
import com.i000phone.pager.utils.HttpTask;
import com.i000phone.pager.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HttpTask.Callback<AllCategory>, View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager pager;
    private AllCategoryAdapter adapter;
    private LinearLayout linearLayout;
    private int[] imgs;
    private int selectColor = 0x00ff00;
    private int normalColor = 0xffffff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = ((ViewPager) findViewById(R.id.main_pager));
        pager.setAdapter(new AllCategoryAdapter(getSupportFragmentManager(), new ArrayList<AllCategory.PerCategory>()));
        HttpTask<AllCategory> task = new HttpTask<>(AllCategory.class, this);
        task.execute(UrlUtil.IMAGE_CATEGORY_URL);
        linearLayout = ((LinearLayout) findViewById(R.id.main_linear));
        imgs = new int[]{
                R.mipmap.ic_1,
                R.mipmap.ic_2,
                R.mipmap.ic_3,
                R.mipmap.ic_4,
                R.mipmap.ic_5,
                R.mipmap.ic_6,
                R.mipmap.ic_7
        };
        for (int i = 0; i < imgs.length; i++) {
            ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.item_linear, linearLayout, false);
            imageView.setId(i);
            imageView.setImageResource(imgs[i]);
            DrawableCompat.setTint(imageView.getDrawable(), Color.WHITE);
            imageView.setOnClickListener(this);
            linearLayout.addView(imageView);
        }
        ImageView imageView = (ImageView) linearLayout.findViewById(0);
        DrawableCompat.setTint(imageView.getDrawable(), selectColor);
        pager.addOnPageChangeListener(this);
    }

    @Override
    public void doResponse(AllCategory allCategory) {
        adapter = new AllCategoryAdapter(getSupportFragmentManager(), allCategory.getList());
        pager.setAdapter(adapter);
    }

    @Override
    public void doFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        pager.setCurrentItem(v.getId(),false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        ImageView lv = (ImageView) linearLayout.findViewById(position);
        ImageView rv = (ImageView) linearLayout.findViewById(position+1);
//        int rgb = (int) (normalColor - selectColor*positionOffset);
        int r = (int) ((Color.red(normalColor) - Color.red(selectColor)) * positionOffset) ;
        int g = (int) ((Color.green(normalColor) - Color.green(selectColor)) * positionOffset) ;
        int b = (int) ((Color.blue(normalColor) - Color.blue(selectColor)) * positionOffset) ;
//        DrawableCompat.setTint(lv.getDrawable(),selectColor+rgb);
        DrawableCompat.setTint(lv.getDrawable(), Color.rgb(
                Color.red(selectColor) + r,
                Color.green(selectColor) + g,
                Color.blue(selectColor) + b));
        if (rv != null) {
//            DrawableCompat.setTint(rv.getDrawable(),normalColor-rgb);
            DrawableCompat.setTint(rv.getDrawable(), Color.rgb(
                    Color.red(normalColor) - r,
                    Color.green(normalColor) - g,
                    Color.blue(normalColor) - b));
        }
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < imgs.length; i++) {
            ImageView imageView = (ImageView) linearLayout.findViewById(i);
            if (i == position) {
                DrawableCompat.setTint(imageView.getDrawable(), selectColor);
            } else {
                DrawableCompat.setTint(imageView.getDrawable(), normalColor);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
