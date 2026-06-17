package com.example.myapplication;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Controller.BudgetDAO;
import com.example.myapplication.Fragments.BottomSheetBudget;
import com.example.myapplication.Model.Budget;
import com.example.myapplication.adapters.BudgetAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BudgetList extends AppCompatActivity implements BottomSheetBudget.Listener {

    EditText budgetSearcher;
    FloatingActionButton fab;
    RecyclerView budgetRv;
    BudgetAdapter budgetAdapter;
    BudgetDAO budgetDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        budgetDAO = new BudgetDAO(this);

        budgetSearcher = findViewById(R.id.budgetSearcher);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(E -> {
            BottomSheetBudget bottomSheet = new BottomSheetBudget();
            bottomSheet.setListener(this);
            bottomSheet.show(getSupportFragmentManager(), "BudgetBottomSheet");
        });

        budgetRv = findViewById(R.id.budgetRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        budgetRv.setLayoutManager(linearLayoutManager);

        List<Budget> budgets = budgetDAO.getBudgetsByUser(Session.getCurrentUser().getId());
        budgets.add(new Budget(
                1,
                Session.getCurrentUser().getId(),
                14000,
                "ceinture",
                "12-10-2025",
                "mai"
            )
        );
        budgetAdapter = new BudgetAdapter(this, new ArrayList<>(budgets));
        budgetRv.setAdapter(budgetAdapter);

    }

    @Override
    public void onSave() {
        List<Budget> budgets = budgetDAO.getBudgetsByUser(Session.getCurrentUser().getId());
        budgetAdapter.setBudgets(new ArrayList<>(budgets));
    }
}