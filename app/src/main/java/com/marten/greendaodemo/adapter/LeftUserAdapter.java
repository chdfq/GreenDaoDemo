package com.marten.greendaodemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marten.greendaodemo.R;
import com.marten.greendaodemo.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class LeftUserAdapter extends RecyclerView.Adapter<LeftUserAdapter.ViewHolder> {

    private List<UserInfo> list = new ArrayList<>();
    private Context context;
    private int nowPosition;

    private OnItemClickListener listener;

    public LeftUserAdapter(List<UserInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void addAll(List<UserInfo> users) {
        if (users != null && users.size() > 0) {
            list.addAll(users); //添加所有元素到列表中
            notifyItemRangeChanged(list.size() - users.size(), users.size());
        }
    }

    public void updateItem(int position) {
        //获取之前选中的user
        UserInfo user2 = list.get(nowPosition);
        //之前选中的user设置成未选中状态
        user2.setSelected(false);
        //之前选中的user
        notifyItemChanged(nowPosition, "update before user item");

        //获取当前点击（选中）的user
        UserInfo user3 = list.get(position);
        //点击（选中）的user设置成选中状态
        user3.setSelected(true);
        //刷新
        notifyItemChanged(position, "update after user item");
        //把之前选中的位置替换成现在（点击）选中的位置
        nowPosition = position;
    }

    @NonNull
    @Override
    public LeftUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.marten_user_item_left, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeftUserAdapter.ViewHolder holder, int position) {
        final UserInfo user = list.get(position);
        if (user.getSelected()) {
            nowPosition = position;
            holder.mLlLeft.setBackgroundResource(R.color.light_gray);
        } else {
            holder.mLlLeft.setBackgroundResource(R.color.white);
        }

        holder.mTvUserName.setText(user.getUserName());
        holder.mTvLevel.setText(String.format("(%s%s)", user.getLevel(), "级"));
        holder.mTvUserPhone.setText(user.getUserPhone());
        if (user.getUserAddress() != null && !user.getUserAddress().equals("")) {
            holder.mTvUserAddress.setText(user.getUserAddress());
        } else {
            holder.mTvUserAddress.setText("无");
        }
        if (user.getUserService() != null && !user.getUserService().equals("")) {
            holder.mTvUserService.setText(user.getUserService());
        } else {
            holder.mTvUserService.setText("无");
        }
        if (user.getUserGift() != null && !user.getUserGift().equals("")) {
            holder.mTvUserGift.setText(user.getUserGift());
        } else {
            holder.mTvUserGift.setText("无");
        }
        if (user.getUpUser() != null && !user.getUpUser().equals("")) {
            holder.mTvUpUser.setText(user.getUpUser());
        } else {
            holder.mTvUpUser.setText("无");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(position, user);
            }
        });


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLlLeft;
        private TextView mTvUserName, mTvLevel, mTvUserPhone, mTvUserAddress, mTvUserService, mTvUserGift, mTvUpUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLlLeft = itemView.findViewById(R.id.ll_item);
            mTvUserName = itemView.findViewById(R.id.tv_user_name);
            mTvLevel = itemView.findViewById(R.id.tv_level);
            mTvUserPhone = itemView.findViewById(R.id.tv_phone);
            mTvUserAddress = itemView.findViewById(R.id.tv_address);
            mTvUserService = itemView.findViewById(R.id.tv_service);
            mTvUserGift = itemView.findViewById(R.id.tv_gift);
            mTvUpUser = itemView.findViewById(R.id.tv_up_user);
        }
    }

    public interface OnItemClickListener {
        void itemClick(int position, UserInfo user2);
    }

    public void cleanAll() {
        list.clear();
        notifyDataSetChanged();
    }
}
