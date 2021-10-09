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
import com.bumptech.glide.request.RequestOptions;
import com.teesofttech.mkremit.R;
import com.teesofttech.mkremit.models.Funding;
import com.teesofttech.mkremit.models.TransactionModel;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.MyViewHolder> {

    private List<Funding> bookingSearchModelList;
    Bitmap bitmap;
    private LayoutInflater mInflater;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView email, orderDate, status, orderStatus, orderDescription;
        public TextView orderId, orderPrice;
        public ImageView imageView, orderIsRepeat;
        public CardView leftLayout;

        public MyViewHolder(View view) {
            super(view);
            orderId = (TextView) view.findViewById(R.id.orderId);
            orderIsRepeat = (ImageView) view.findViewById(R.id.orderIsRepeat);
            //  email = (TextView) view.findViewById(R.id.email);
            orderDescription = (TextView) view.findViewById(R.id.orderDescription);
            orderDate = (TextView) view.findViewById(R.id.orderDate);
            orderPrice = (TextView) view.findViewById(R.id.orderPrice);
            orderStatus = (TextView) view.findViewById(R.id.orderStatus);
            imageView = (ImageView) view.findViewById(R.id.orderImage);

        }
    }


    public HistoryListAdapter(Context mContext, List<Funding> bookingSearchModelList) {
        context = mContext;
        this.bookingSearchModelList = bookingSearchModelList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.funding_trans_item, parent, false);
        return new HistoryListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Funding album = bookingSearchModelList.get(position);
        holder.orderId.setText(album.getReference().toUpperCase());
        // holder.phonenumber.setText(album.getPhonenumber());
        //holder.email.setText(album.getEmail());
        holder.orderDate.setText(album.getDate());
        holder.orderPrice.setText("₦" + album.getAmount());
        //holder.orderDescription.setText(album.getDescription());
        if (album.getStatus().equals("Processed") || album.getStatus().equals("credit")) {
            holder.orderStatus.setText("Your payment was successful");
            holder.orderIsRepeat.setImageResource(R.mipmap.success);

        } else {
            holder.orderStatus.setText("Your payment was not successful");
            holder.orderIsRepeat.setImageResource(R.mipmap.fail);
        }

        //Glide.with(this).load(image_url).apply(options).into(imageView);
        Glide.with(context).load(R.mipmap.imm).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bookingSearchModelList.size();
    }
}