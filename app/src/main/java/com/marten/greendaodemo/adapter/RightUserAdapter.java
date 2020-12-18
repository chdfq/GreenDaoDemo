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

public class RightUserAdapter extends RecyclerView.Adapter<RightUserAdapter.ViewHolder> {

    private List<UserInfo> list = new ArrayList<>();
    private Context context;

    public RightUserAdapter(List<UserInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void addAll(List<UserInfo> users) {
        if (users != null && users.size() > 0) {
            list.addAll(users);
            notifyItemChanged(list.size() - users.size(), users.size());
        }
    }

    @NonNull
    @Override
    public RightUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.marten_user_item_right, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RightUserAdapter.ViewHolder holder, int position) {
        final UserInfo user = list.get(position);

        holder.mTvUserName.setText(user.getUserName());
        holder.mTvLevel.setText(String.format("(%s%s)", user.getLevel(), "级"));
        holder.mTvUserPhone.setText(user.getUserPhone());
        if (user.getUserAddress() != null) {
            holder.mTvUserAddress.setText(user.getUserAddress());
        }
        if (user.getUserService() != null) {
            holder.mTvUserService.setText(user.getUserService());
        }
        if (user.getUserGift() != null) {
            holder.mTvUserGift.setText(user.getUserGift());
        }
        if (user.getUpUser() != null) {
            holder.mTvUpUser.setText(user.getUpUser());
        } else {
            holder.mTvUpUser.setText("无");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        private LinearLayout mLlRight;
        private TextView mTvUserName, mTvLevel, mTvUserPhone, mTvUserAddress, mTvUserService, mTvUserGift, mTvUpUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLlRight = itemView.findViewById(R.id.ll_item);
            mTvUserName = itemView.findViewById(R.id.tv_user_name);
            mTvLevel = itemView.findViewById(R.id.tv_level);
            mTvUserPhone = itemView.findViewById(R.id.tv_phone);
            mTvUserAddress = itemView.findViewById(R.id.tv_address);
            mTvUserService = itemView.findViewById(R.id.tv_service);
            mTvUserGift = itemView.findViewById(R.id.tv_gift);
            mTvUpUser = itemView.findViewById(R.id.tv_up_user);
        }
    }

    public void cleanAll() {
        list.clear();
        notifyDataSetChanged();
    }
}
