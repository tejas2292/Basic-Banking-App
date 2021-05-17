package com.example.sparksbanking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListTransactionAdapter extends RecyclerView.Adapter<ListTransactionAdapter.ViewHolder> {
    private ArrayList<UploadTransaction> transactionArrayList;

    public ListTransactionAdapter(Context context, ArrayList<UploadTransaction> list) {
        transactionArrayList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fromName, toName, amountTransferred, date, time;
        CardView cardView;
        LinearLayout toUserInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fromName = itemView.findViewById(R.id.t_from_name);
            toName = itemView.findViewById(R.id.t_to_name);
            amountTransferred = itemView.findViewById(R.id.t_amount);
            cardView = itemView.findViewById(R.id.row_transaction_card_view);
            toUserInfo = itemView.findViewById(R.id.to_user_info);
            date = itemView.findViewById(R.id.t_date);
            time = itemView.findViewById(R.id.t_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // still to be implemented
                }
            });
        }
    }

    @NonNull
    @Override
    public ListTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from (viewGroup.getContext()).inflate(R.layout.row_transaction, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(transactionArrayList.get(position));
        viewHolder.fromName.setText(transactionArrayList.get(position).getFromUser());
        viewHolder.toName.setText(transactionArrayList.get(position).getToUser());
        viewHolder.amountTransferred.setText(String.format("%d", transactionArrayList.get(position).getAmountTransferred()));


        if (transactionArrayList.get(position).getStatus() == 1) {
            viewHolder.cardView.getBackground().setTint(Color.parseColor("#9CEE9A"));
        }
        else {
            viewHolder.cardView.setCardBackgroundColor(Color.argb(100, 239, 100, 100));
        }
    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }
}
