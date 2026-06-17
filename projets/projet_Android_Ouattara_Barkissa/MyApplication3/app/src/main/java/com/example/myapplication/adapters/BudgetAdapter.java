package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fragments.BottomSheetBudget;
import com.example.myapplication.Model.Budget;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private List<Budget> budgets;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView budgetCardNameTv;
        private final TextView budgetCardDate;
        private final TextView budgetCardAmountTv;

        private ConstraintLayout card;

        private Budget budget = null;


        public ViewHolder(View view) {
            super(view);
            budgetCardNameTv = view.findViewById(R.id.budgetCardNameTv);
            budgetCardDate = view.findViewById(R.id.budgetCardDateTv);
            budgetCardAmountTv = view.findViewById(R.id.budgetCardAmountTv);
            card = view.findViewById(R.id.card);
        }

        public void setBudget(Budget budget){
            this.budget = budget;
        }

        public Budget getBudget(){
            return budget;
        }
    }

    public BudgetAdapter(Context context, List<Budget> budgets) {
        this.context = context;
        this.budgets = budgets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.budget_card, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Budget currentBudget = budgets.get(position);
        if (currentBudget != null) {
            holder.budgetCardNameTv.setText(currentBudget.getBudgetName());
            holder.budgetCardDate.setText(currentBudget.getDate());
            holder.budgetCardAmountTv.setText(String.valueOf(currentBudget.getAmountLimit()));
            holder.setBudget(currentBudget);

            holder.card.setOnClickListener(e->{
                BottomSheetBudget bottomSheetBudget = new BottomSheetBudget();
                //bottomSheetBudget.setListener((BottomSheetBudget.Listener) context);
            });
        }
    }

    @Override
    public int getItemCount() {
        return budgets != null ? budgets.size() : 0;
    }

    public void setBudgets(ArrayList<Budget> budgets){
        this.budgets = budgets;
        notifyDataSetChanged();
    }
}
