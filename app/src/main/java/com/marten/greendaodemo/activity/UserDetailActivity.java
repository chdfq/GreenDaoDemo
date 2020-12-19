package com.marten.greendaodemo.activity;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marten.greendaodemo.R;
import com.marten.greendaodemo.adapter.RightUserAdapter;
import com.marten.greendaodemo.bean.UserInfo;
import com.marten.greendaodemo.green_dao.DaoManager;
import com.marten.greendaodemo.green_dao.UserInfoDao;

import java.util.List;

public class UserDetailActivity extends BaseActivity {

    private TextView mTvUserName, mTvLevel, mTvUserPhone, mTvUserAddress, mTvUserService, mTvUserGift, mTvUpUser;
    private RecyclerView mRvDetailLeft, mRvDetailRight;
    private Long userId;
    private UserInfo userInfo;
    private UserInfoDao userInfoDao;
    private RightUserAdapter adapterLeft;
    private RightUserAdapter adapterRight;

    @Override
    public void initView() {
        contentView(R.layout.marten_user_detail_page);
        setBackImage();

        mTvUserName = findViewById(R.id.tv_user_name);
        mTvLevel = findViewById(R.id.tv_level);
        mTvUserPhone = findViewById(R.id.tv_phone);
        mTvUserAddress = findViewById(R.id.tv_address);
        mTvUserService = findViewById(R.id.tv_service);
        mTvUserGift = findViewById(R.id.tv_gift);
        mTvUpUser = findViewById(R.id.tv_up_user);
        mRvDetailLeft = findViewById(R.id.rv_detail_left);
        mRvDetailRight = findViewById(R.id.rv_detail_right);

        userInfoDao = DaoManager.getInstance().getDaoSession().getUserInfoDao();
        userId = getIntent().getLongExtra("userId", 0);
        userInfo = userInfoDao.queryBuilder()
                .where(UserInfoDao.Properties.Id.eq(userId))
                .unique();

        setTitle(userInfo.getUserName());

        mTvUserName.setText(userInfo.getUserName());
        mTvLevel.setText(String.format("(%s%s)", userInfo.getLevel(), "级"));
        mTvUserPhone.setText(userInfo.getUserPhone());
        if (userInfo.getUserAddress() != null && !userInfo.getUserAddress().equals("")) {
            mTvUserAddress.setText(userInfo.getUserAddress());
        } else {
            mTvUserAddress.setText("无");
        }
        if (userInfo.getUserService() != null && !userInfo.getUserService().equals("")) {
            mTvUserService.setText(userInfo.getUserService());
        } else {
            mTvUserService.setText("无");
        }
        if (userInfo.getUserGift() != null && !userInfo.getUserGift().equals("")) {
            mTvUserGift.setText(userInfo.getUserGift());
        } else {
            mTvUserGift.setText("无");
        }
        if (userInfo.getUpUser() != null && !userInfo.getUpUser().equals("")) {
            mTvUpUser.setText(userInfo.getUpUser());
        } else {
            mTvUpUser.setText("无");
        }

        mRvDetailLeft.setLayoutManager(new LinearLayoutManager(context));
        mRvDetailRight.setLayoutManager(new LinearLayoutManager(context));

        List<UserInfo> userInfoListLeft = userInfoDao
                .queryBuilder()
                .where(UserInfoDao.Properties.UserPhone.eq(userInfo.getUpUser()))
                .build()
                .list();
        adapterLeft = new RightUserAdapter(userInfoListLeft, context);
        adapterLeft.setItemClickListener(new RightUserAdapter.OnItemClickListener() {
            @Override
            public void itemClick() {
                finish();
            }
        });
        List<UserInfo> userInfoListRight = userInfoDao
                .queryBuilder()
                .where(UserInfoDao.Properties.UpUser.eq(userInfo.getUserPhone()))
                .build()
                .list();
        adapterRight = new RightUserAdapter(userInfoListRight, context);
        adapterRight.setItemClickListener(new RightUserAdapter.OnItemClickListener() {
            @Override
            public void itemClick() {
                finish();
            }
        });

        mRvDetailLeft.setAdapter(adapterLeft);
        mRvDetailRight.setAdapter(adapterRight);
    }
}
