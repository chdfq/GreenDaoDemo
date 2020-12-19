package com.marten.greendaodemo.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marten.greendaodemo.R;
import com.marten.greendaodemo.adapter.RightUserAdapter;
import com.marten.greendaodemo.bean.UserInfo;
import com.marten.greendaodemo.green_dao.DaoManager;
import com.marten.greendaodemo.green_dao.UserInfoDao;

import java.util.List;

public class SearchActivity extends BaseActivity {

    private ImageView mIvBack;
    private EditText mEtSearch;
    private RecyclerView mRvList;
    private UserInfoDao userInfoDao;
    private RightUserAdapter adapter;

    @Override
    public void initView() {
        contentView(R.layout.marten_search_page);
        setBackImage();
        hideTitleBar();
        mIvBack = findViewById(R.id.iv_back_search);
        mEtSearch = findViewById(R.id.et_search);
        mRvList = findViewById(R.id.rv_list);
        userInfoDao = DaoManager.getInstance().getDaoSession().getUserInfoDao();

        mRvList.setLayoutManager(new LinearLayoutManager(context));

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String etStr = s.toString();
                //模糊查询
                List<UserInfo> userInfoList = userInfoDao.queryBuilder()
                        .whereOr(UserInfoDao.Properties.UserName.like("%" + etStr + "%")
                                , UserInfoDao.Properties.UserPhone.eq(etStr))
                        .build().list();
                adapter = new RightUserAdapter(userInfoList, context);
                adapter.setItemClickListener(new RightUserAdapter.OnItemClickListener() {
                    @Override
                    public void itemClick() {
                        return;
                    }
                });
                mRvList.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEtSearch.getText().toString().isEmpty()) {
                    adapter.cleanAll();
                }
            }
        });
    }
}
