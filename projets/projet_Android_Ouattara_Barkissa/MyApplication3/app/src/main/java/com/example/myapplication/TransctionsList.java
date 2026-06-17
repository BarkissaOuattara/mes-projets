package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Controller.CategorieDAO;
import com.example.myapplication.Controller.TransactionDAO;
import com.example.myapplication.Fragments.BottomSheetCategory;
import com.example.myapplication.Fragments.BottomSheetTransaction;
import com.example.myapplication.Model.Categorie;
import com.example.myapplication.Model.Transaction;
import com.example.myapplication.adapters.TransctionAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransctionsList extends AppCompatActivity implements BottomSheetTransaction.Listener {

    TransctionAdapter transctionAdapter;
    TransactionDAO transactionDAO;
    CategorieDAO categorieDAO;
    Toolbar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transctions_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialisation de la Toolbar
        topBar = findViewById(R.id.topBar);
        setSupportActionBar(topBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Activer le bouton retour
        }

        // Initialisation des données
        transactionDAO = new TransactionDAO(this);
        categorieDAO = new CategorieDAO(this);
        ArrayList<Transaction> transactions = new ArrayList<>(transactionDAO.getAllTransactions());
        transctionAdapter = new TransctionAdapter(transactions);

        TabLayout tabLayout = findViewById(R.id.transactionTab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                ArrayList<Transaction> transactions = new ArrayList<>(transactionDAO.getAllTransactions());;
                List<Transaction> filtered = null;;

                switch (tab.getPosition()){
                    case 0:
                        Toast.makeText(TransctionsList.this, "0", Toast.LENGTH_SHORT).show();
                        filtered = transactions.stream()
                                .filter(transaction -> {
                                    Categorie categorie = categorieDAO.getCategorieById(transaction.getCategoryId());
                                    return categorie.getType().equals("depense");
                                }).collect(Collectors.toList());
                        transctionAdapter.setTransactions(new ArrayList<>(filtered));
                       break;
                    case 1:
                        filtered = transactions.stream()
                                .filter(transaction -> {
                                    Categorie categorie = categorieDAO.getCategorieById(transaction.getCategoryId());
                                    return categorie.getType().equals("revenu");
                                }).collect(Collectors.toList());
                        transctionAdapter.setTransactions(new ArrayList<>(filtered));
                        break;
                    case 2:
                        transctionAdapter.setTransactions(new ArrayList<>(transactions));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        RecyclerView listRv = findViewById(R.id.budgetRv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listRv.setLayoutManager(layoutManager);
        transctionAdapter.setTransactions(new ArrayList<>(transactions));
        listRv.setAdapter(transctionAdapter);

        // Gestion du FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            BottomSheetTransaction bottomSheet = new BottomSheetTransaction();
            bottomSheet.setListener(this);
            bottomSheet.show(getSupportFragmentManager(), "TransactionBottomSheet");
        });
    }

    // Ajout du menu à la Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);
        return true;
    }

    // Gestion du clic sur les éléments du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            // Ouvre le BottomSheet pour ajouter une transaction
            BottomSheetCategory bottomSheet = new BottomSheetCategory();
            bottomSheet.show(getSupportFragmentManager(), "CategoryBottomSheet");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSave() {
        ArrayList<Transaction> transactions = new ArrayList<>(transactionDAO.getAllTransactions());
        transctionAdapter.setTransactions(transactions);
    }
}
