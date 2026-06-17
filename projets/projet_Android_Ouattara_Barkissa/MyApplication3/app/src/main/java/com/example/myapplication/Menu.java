package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Fragments.Home_budgets;
import com.example.myapplication.Fragments.Home_transactions;
import com.google.android.material.tabs.TabLayout;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TabLayout menuTl = findViewById(R.id.menuTl);
        menuTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab != null){
                    switch (tab.getPosition()) {
                        case 0:
                            showFragment(new Home_transactions());
                            break;
                        case 1:
                            showFragment(new Home_budgets());
                            break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        LinearLayout transactionLL = findViewById(R.id.menuLstTransaction);
        transactionLL.setOnClickListener(e -> {
            Intent transactionIntent = new Intent(this, TransctionsList.class);
            startActivity(transactionIntent);
        });

        LinearLayout budgetLL = findViewById(R.id.menuLstBudgets);
        budgetLL.setOnClickListener(e -> {
            Intent transactionIntent = new Intent(this, BudgetList.class);
            startActivity(transactionIntent);
        });
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}