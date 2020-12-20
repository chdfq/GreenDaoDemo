package com.marten.greendaodemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marten.greendaodemo.R;
import com.marten.greendaodemo.activity.MainActivity;
import com.marten.greendaodemo.activity.SearchActivity;
import com.marten.greendaodemo.activity.UserDetailActivity;
import com.marten.greendaodemo.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class RightUserAdapter extends RecyclerView.Adapter<RightUserAdapter.ViewHolder> {

    private List<UserInfo> list = new ArrayList<>();
    private Context context;
    private RightUserAdapter.OnItemClickListener listener;

    public void setItemClickListener(RightUserAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public RightUserAdapter(List<UserInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void addAll(List<UserInfo> users) {
        if (users != null && users.size() > 0) {
            list.addAll(users);
            notifyItemRangeChanged(list.size() - users.size(), users.size());
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
                Intent intent = new Intent(context,UserDetailActivity.class);
                intent.putExtra("userId",user.getId());
                context.startActivity(intent);
                listener.itemClick();
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

    public interface OnItemClickListener {
        void itemClick();
    }

    public void cleanAll() {
        list.clear();
        notifyDataSetChanged();
    }
}
