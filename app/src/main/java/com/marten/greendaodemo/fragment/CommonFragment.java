package com.marten.greendaodemo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marten.greendaodemo.R;
import com.marten.greendaodemo.adapter.LeftUserAdapter;
import com.marten.greendaodemo.adapter.RightUserAdapter;
import com.marten.greendaodemo.bean.UserInfo;
import com.marten.greendaodemo.green_dao.DaoManager;
import com.marten.greendaodemo.green_dao.UserInfoDao;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class CommonFragment extends Fragment {

    private int level, nextLevel;
    private int pageLeft = 1;
    private int pageRight = 1;
    private int pageSize = 20;
    private String defaultUpUser = "";

    private View view;
    private SmartRefreshLayout mSrlLeft, mSrlRight;
    private RecyclerView mRvLeft, mRvRight;
    private LeftUserAdapter leftAdapter;
    private RightUserAdapter rightAdapter;
    private UserInfoDao userInfoDao;
    private List<UserInfo> userInfoListLeft = new ArrayList<>();
    private List<UserInfo> userInfoListRight = new ArrayList<>();

    public static CommonFragment newInstance(int level) {
        //存等级
        CommonFragment fragment = new CommonFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("level", level);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.marten_fragment_common, container, false);
        mSrlLeft = view.findViewById(R.id.srl_left);
        mSrlRight = view.findViewById(R.id.srl_right);
        mRvLeft = view.findViewById(R.id.rv_left);
        mRvRight = view.findViewById(R.id.rv_right);

        userInfoDao = DaoManager.getInstance().getDaoSession().getUserInfoDao();
        mRvLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvRight.setLayoutManager(new LinearLayoutManager(getContext()));

        //左边SmartRefreshLayout上拉刷新
        mSrlLeft.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageLeft = 1;
                initDataLeft(pageLeft);
            }
        });

        //左边SmartRefreshLayout下拉加载
        mSrlLeft.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageLeft++;
                initDataLeft(pageLeft);
            }
        });

        //右边SmartRefreshLayout上拉刷新
        mSrlRight.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageRight = 1;
                initDataRight(pageRight, defaultUpUser);
            }
        });

        //右边SmartRefreshLayout下拉加载
        mSrlRight.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageRight++;
                initDataRight(pageRight, defaultUpUser);
            }
        });

        //取等级
        Bundle bundle = getArguments();
        if (bundle != null) {
            level = bundle.getInt("level");
        }
        nextLevel = level + 1;

        initDataLeft(pageLeft);
        return view;
    }

    public void initDataLeft(int page) {
        userInfoListLeft = userInfoDao.queryBuilder()
                .where(UserInfoDao.Properties.Level.eq(level))
                .offset((page - 1) * pageSize)   //分页查询，从0开始
                .limit(pageSize)
                .build().list();
        if (page == 1) {
            leftAdapter = new LeftUserAdapter(userInfoListLeft, getContext());
            mRvLeft.setAdapter(leftAdapter);
            //一级列表刷新二级列表
            if (userInfoListLeft.size() > 0) {
                UserInfo userInfo = userInfoListLeft.get(0);
                defaultUpUser = userInfo.getUserPhone();
                initDataRight(pageRight, defaultUpUser);
                userInfoListLeft.get(0).setSelected(true);
            }
            //一级列表点击事件刷新二级列表
            leftAdapter.setItemClickListener(new LeftUserAdapter.OnItemClickListener() {
                @Override
                public void itemClick(int position, UserInfo user2) {
                    pageRight = 1;
                    defaultUpUser = user2.getUserPhone();
                    initDataRight(pageRight, defaultUpUser);
                    leftAdapter.updateItem(position);
                }
            });

        } else {
            if (leftAdapter != null) {
                leftAdapter.addAll(userInfoListLeft);
            }
        }
        mSrlLeft.finishRefresh();
        mSrlLeft.finishLoadMore();

    }

    public void initDataRight(int page, String upUserPhone) {
        userInfoListRight = userInfoDao.queryBuilder()
                .where(UserInfoDao.Properties.Level.eq(nextLevel), UserInfoDao.Properties.UpUser.eq(upUserPhone))
                .offset((page - 1) * pageSize)   //分页查询，从0开始
                .limit(pageSize)
                .build().list();
        if (page == 1) {
            rightAdapter = new RightUserAdapter(userInfoListRight, getContext());
            rightAdapter.setItemClickListener(new RightUserAdapter.OnItemClickListener() {
                @Override
                public void itemClick() {
                    return;
                }
            });
            mRvRight.setAdapter(rightAdapter);
        } else {
            if (rightAdapter != null) {
                rightAdapter.addAll(userInfoListRight);
            }
        }

        mSrlRight.finishRefresh();
        mSrlRight.finishLoadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
        pageLeft = 1;
        initDataLeft(pageLeft);
    }
}
