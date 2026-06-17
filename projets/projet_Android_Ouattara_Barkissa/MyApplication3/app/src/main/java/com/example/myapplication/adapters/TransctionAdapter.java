package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Transaction;
import com.example.myapplication.R;

import java.util.ArrayList;

public class TransctionAdapter extends RecyclerView.Adapter<TransctionAdapter.ViewHolder> {

    private ArrayList<Transaction> transactions;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView transactionCardTypeTv;
        private final TextView transactionCardCategory;
        private final TextView transactionCardDate;
        private final TextView transactionCardAmountTv;

        public ViewHolder(View view) {

            super(view);

            transactionCardTypeTv = view.findViewById(R.id.transactionCardTypeTv);
            transactionCardCategory = view.findViewById(R.id.transactionCardCategoryTv);
            transactionCardDate = view.findViewById(R.id.transactionCardDateTv);
            transactionCardAmountTv = view.findViewById(R.id.transactionCardAmountTv);
        }
    }



    public  TransctionAdapter(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.transaction_card, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.transactionCardTypeTv.setText("unkown");
        holder.transactionCardDate.setText(transactions.get(position).getDate());
        holder.transactionCardAmountTv.setText(String.valueOf(transactions.get(position).getAmount()));
        holder.transactionCardCategory.setText(String.valueOf(transactions.get(position).getCategoryId()));

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }
}
