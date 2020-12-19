package com.marten.greendaodemo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class UserInfo {
    @Id
    private Long id;    //自增

    @Property(nameInDb = "user_name")
    private String userName;

    @Property(nameInDb = "user_phone")
    private String userPhone;

    @Property(nameInDb = "up_user")
    private String upUser;

    @Property(nameInDb = "user_address")
    private String userAddress;

    @Property(nameInDb = "user_service")
    private String userService;

    @Property(nameInDb = "user_gift")
    private String userGift;

    private int level;

    private boolean selected;

    @Generated(hash = 508738292)
    public UserInfo(Long id, String userName, String userPhone, String upUser,
            String userAddress, String userService, String userGift, int level,
            boolean selected) {
        this.id = id;
        this.userName = userName;
        this.userPhone = userPhone;
        this.upUser = upUser;
        this.userAddress = userAddress;
        this.userService = userService;
        this.userGift = userGift;
        this.level = level;
        this.selected = selected;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return this.userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUpUser() {
        return this.upUser;
    }

    public void setUpUser(String upUser) {
        this.upUser = upUser;
    }

    public String getUserAddress() {
        return this.userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserService() {
        return this.userService;
    }

    public void setUserService(String userService) {
        this.userService = userService;
    }

    public String getUserGift() {
        return this.userGift;
    }

    public void setUserGift(String userGift) {
        this.userGift = userGift;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}