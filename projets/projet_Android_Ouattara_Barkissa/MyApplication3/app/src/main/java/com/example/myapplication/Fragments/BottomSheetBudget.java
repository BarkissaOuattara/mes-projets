package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Controller.BudgetDAO;
import com.example.myapplication.Controller.CategorieDAO;
import com.example.myapplication.Model.Budget;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.selectors.SelectorDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BottomSheetBudget extends BottomSheetDialogFragment {

    private EditText budgetNameEt, budgetMounthEt, budgetAmountEt, budgetDescriptionEt;
    private Button okButton;
    private ImageView budgetMounthAdder;
    CategorieDAO categorieDAO;
    BudgetDAO budgetDAO;

    public Listener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet_budget, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categorieDAO = new CategorieDAO(getContext());
        budgetDAO = new BudgetDAO(getContext());

        budgetNameEt = view.findViewById(R.id.budgetNameEt);
        budgetMounthEt = view.findViewById(R.id.budgetMounthEt);
        budgetAmountEt = view.findViewById(R.id.budgetAmountEt);
        budgetDescriptionEt = view.findViewById(R.id.budgetDescriptionEt);
        budgetMounthAdder = view.findViewById(R.id.budgetMounthAdder);
        okButton = view.findViewById(R.id.okButton);


        okButton.setOnClickListener(v -> {
            String budgetName = budgetNameEt.getText().toString().trim();
            String mounth = budgetMounthEt.getText().toString().trim();
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String amount = budgetAmountEt.getText().toString().trim();
            String description = budgetDescriptionEt.getText().toString().trim();

            double amountDouble=-1;

            if(budgetName.isEmpty() || mounth.isEmpty() || amount.isEmpty())
                Toast.makeText(getContext(), "remplissez tous les champs", Toast.LENGTH_SHORT).show();
            else{
                try {
                    amountDouble = Double.parseDouble(amount);
                } catch (Exception e){
                    Toast.makeText(getContext(), "montant invalide", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Budget budget = new Budget(
                    Session.getCurrentUser().getId(),
                    amountDouble,
                    budgetName,
                    date,
                    mounth
            );
            long res = budgetDAO.addBudget(budget);
            if(listener!=null)
                listener.onSave();
            Toast.makeText(getContext(), String.valueOf(res), Toast.LENGTH_SHORT).show();
            dismiss();
        });

        budgetMounthAdder.setOnClickListener(v ->{
            List<String> types = List.of("janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "decembre");
            SelectorDialog dialog = new SelectorDialog(getContext(), types, false);

            dialog.showCategorySelectionDialog(selectedCategories -> {
                budgetMounthEt.setText(String.join(", ", selectedCategories)); // Afficher les catégories sélectionnées
            });
            Toast.makeText(getContext(), "date", Toast.LENGTH_SHORT).show();
        });

    }

    public interface Listener{
        public void onSave();
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
}
