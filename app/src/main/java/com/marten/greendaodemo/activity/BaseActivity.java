package com.marten.greendaodemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.marten.greendaodemo.R;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLlBase;
    private TextView mTvTitle, mIvBack, mIvAdd;
    private RelativeLayout mRlEdit;
    private View view;
    private String title;
    public Context context;

    public RelativeLayout getEditText() {
        return mRlEdit;
    }

    public TextView getBackImage() {
        return mIvBack;
    }

    public void setBackImage() {
        mIvBack.setVisibility(View.VISIBLE);
    }

    public TextView getAddImage() {
        return mIvAdd;
    }

    public void setTitle(String title) {
        this.title = title;
        if (mTvTitle != null) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_page);
        context = BaseActivity.this;
        mLlBase = findViewById(R.id.ll_base);
        mTvTitle = findViewById(R.id.tv_title);
        mRlEdit = findViewById(R.id.rl_search);
        mIvBack = findViewById(R.id.tv_back);
        mIvAdd = findViewById(R.id.tv_add);
        initView();
        mIvBack.setOnClickListener(this);
    }

    public abstract void initView();

    protected void contentView(int layoutId) {
        view = getLayoutInflater().inflate(layoutId, null);
        if (view != null) {
            mLlBase.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
