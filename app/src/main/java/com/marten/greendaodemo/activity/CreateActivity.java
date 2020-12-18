package com.marten.greendaodemo.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marten.greendaodemo.R;
import com.marten.greendaodemo.bean.UserInfo;
import com.marten.greendaodemo.green_dao.DaoManager;
import com.marten.greendaodemo.green_dao.UserInfoDao;
import com.marten.greendaodemo.utils.TelephoneUtil;

import java.util.List;

public class CreateActivity extends BaseActivity {

    private EditText mEtName, mEtPhone, mEtAddress, mEtService, mEtGift, mEtUpUser;
    private Button mBtnCommit;
    private UserInfo userInfo;
    private UserInfoDao userInfoDao;
    private String name;
    private String phone;
    private String upUserPhone;

    @Override
    public void initView() {
        contentView(R.layout.activity_create_page);
        setTitle("添加用户信息");
        setBackImage();
        mEtName = findViewById(R.id.et_name);
        mEtPhone = findViewById(R.id.et_phone);
        mEtAddress = findViewById(R.id.et_address);
        mEtService = findViewById(R.id.et_service);
        mEtGift = findViewById(R.id.et_gift);
        mEtUpUser = findViewById(R.id.et_up_user);
        mBtnCommit = findViewById(R.id.btn_commit);

        userInfoDao = DaoManager.getInstance().getDaoSession().getUserInfoDao();

        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo = new UserInfo();
                if (checkEt()) {
                    userInfo.setUserName(name);
                    userInfo.setUserPhone(phone);
                    userInfo.setUpUser(upUserPhone);
                    userInfo.setUserAddress(mEtAddress.getText().toString().trim());
                    userInfo.setUserService(mEtService.getText().toString().trim());
                    userInfo.setUserGift(mEtGift.getText().toString().trim());

                    userInfoDao.insert(userInfo);

                    finish();
                }
            }
        });
    }

    public boolean checkEt() {
        name = mEtName.getText().toString().trim();
        phone = mEtPhone.getText().toString().trim();
        upUserPhone = mEtUpUser.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(context, "请输入姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.isEmpty()) {
            Toast.makeText(context, "请输入电话号码", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!TelephoneUtil.checkCellPhone(phone)) {
                Toast.makeText(context, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                //查询
                List<UserInfo> userInfoList = userInfoDao.queryBuilder()
                        .where(UserInfoDao.Properties.UserPhone.eq(phone))
                        //条数
                        .limit(2)
                        .build()
                        .list();
                if (userInfoList != null && userInfoList.size() > 0) {
                    Toast.makeText(context, "该电话号码已被注册", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
        }

        if (!upUserPhone.isEmpty()) {
            if (!TelephoneUtil.checkCellPhone(upUserPhone)) {
                Toast.makeText(context, "推荐人电话号码有误", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                UserInfo upUser = userInfoDao.queryBuilder()
                        .where(UserInfoDao.Properties.UserPhone.eq(upUserPhone))
                        .build()
                        .unique();
                if (upUser == null) {
                    Toast.makeText(context, "找不到推荐人信息", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }
}
