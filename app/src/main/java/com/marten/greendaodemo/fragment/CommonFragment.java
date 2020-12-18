package com.marten.greendaodemo.fragment;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class CommonFragment extends Fragment {

    private View view;
    private SmartRefreshLayout mSrlLeft, mSrlRight;
    private RecyclerView mRvLeft, mRvRight;
    private LeftUserAdapter leftAdapter;
    private RightUserAdapter rightAdapter;
    private UserInfoDao userInfoDao;
    private List<UserInfo> userInfoList = new ArrayList<>();

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
        initData();
        return view;
    }

    public void initData(){
        userInfoList = userInfoDao.queryBuilder().build().list();
        leftAdapter = new LeftUserAdapter(userInfoList,getContext());
        //rightAdapter = new RightUserAdapter(userInfoList,getContext());
        mRvLeft.setAdapter(leftAdapter);
        //mRvRight.setAdapter(rightAdapter);
    }
}
