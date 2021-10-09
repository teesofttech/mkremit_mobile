package com.teesofttech.mkremit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.models.TransactionModel;

import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.MyViewHolder> {

    private List<TransactionModel> bookingSearchModelList;
    Bitmap bitmap;
    private LayoutInflater mInflater;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView phonenumber, email, orderDate, status, orderStatus, orderDescription;
        public TextView orderId, orderPrice;
        public ImageView imageView;
        public CardView leftLayout;

        public MyViewHolder(View view) {
            super(view);
            orderId = (TextView) view.findViewById(R.id.orderId);
            //phonenumber = (TextView) view.findViewById(R.id.phonenumber);
            //  email = (TextView) view.findViewById(R.id.email);
            orderDescription = (TextView) view.findViewById(R.id.orderDescription);
            orderDate = (TextView) view.findViewById(R.id.orderDate);
            orderPrice = (TextView) view.findViewById(R.id.orderPrice);
            orderStatus = (TextView) view.findViewById(R.id.orderStatus);
            imageView = (ImageView) view.findViewById(R.id.orderImage);

        }
    }


    public TransactionListAdapter(Context mContext, List<TransactionModel> bookingSearchModelList) {
        context = mContext;
        this.bookingSearchModelList = bookingSearchModelList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trans_item, parent, false);
        return new TransactionListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final TransactionModel album = bookingSearchModelList.get(position);
        holder.orderId.setText(album.getReference().toUpperCase());
        // holder.phonenumber.setText(album.getPhonenumber());
        //holder.email.setText(album.getEmail());
        holder.orderDate.setText(album.getDate());
        holder.orderPrice.setText("₦" + album.getAmount());
        holder.orderDescription.setText(album.getDescription());
        if (album.getPaid().equals("1")) {
            holder.orderStatus.setText("Your order is successful");
        }
        Glide.with(context).load(album.getLogo()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bookingSearchModelList.size();
    }
}