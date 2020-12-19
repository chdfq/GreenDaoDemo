package com.marten.greendaodemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.marten.greendaodemo.R;
import com.marten.greendaodemo.adapter.ViewPagerAdapter;
import com.marten.greendaodemo.bean.UserInfo;
import com.marten.greendaodemo.fragment.CommonFragment;
import com.marten.greendaodemo.green_dao.DaoManager;
import com.marten.greendaodemo.green_dao.UserInfoDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private UserInfo userInfo;
    private UserInfoDao userInfoDao;
    private ViewPager mVpMain;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    private RelativeLayout mRvSearch;
    private TextView mIvAdd;

    private SharedPreferences sharedPreferences;

    @Override
    public void initView() {
        contentView(R.layout.activity_main);
        mVpMain = findViewById(R.id.vp_main);

        sharedPreferences = getSharedPreferences("maxLevel", MODE_PRIVATE);

        mIvAdd = getAddImage();
        mIvAdd.setVisibility(View.VISIBLE);
        mIvAdd.setOnClickListener(v -> {
            //添加数据页面
            startActivity(new Intent(context, CreateActivity.class));
        });

        mRvSearch = getEditText();
        mRvSearch.setVisibility(View.VISIBLE);
        mRvSearch.setOnClickListener(v -> {
            //查询数据页面
            startActivity(new Intent(context, SearchActivity.class));
        });

        userInfoDao = DaoManager.getInstance().getDaoSession().getUserInfoDao();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
        }

        CommonFragment commonFragment1 = CommonFragment.newInstance(1);
        CommonFragment commonFragment2 = CommonFragment.newInstance(2);
        fragments.add(commonFragment1);
        fragments.add(commonFragment2);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mVpMain.setAdapter(viewPagerAdapter);
        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //判断当前是不是最后一个Fragment，如果是则加载一个新的Fragment
                int level = sharedPreferences.getInt("maxLevel", 1);
                if (position == fragments.size() - 1) {
                    if (level > position + 1) {
                        Log.i("ma>>", "pos:" + position + "level:" + level + "size:" + fragments.size());
                        //判断最低一级，position == level - 1
                        fragments.add(CommonFragment.newInstance(position + 2));
                        viewPagerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //返回结果（用户是否允许）
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}