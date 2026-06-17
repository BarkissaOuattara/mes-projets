package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Controller.CategorieDAO;
import com.example.myapplication.Controller.TransactionDAO;
import com.example.myapplication.Model.Categorie;
import com.example.myapplication.Model.Transaction;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.example.myapplication.selectors.SelectorDialog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BottomSheetTransaction extends BottomSheetDialogFragment {

    private EditText montantEt, categoryEt, typeEt, descriptionEt;
    private Button okButton;
    CategorieDAO categorieDAO;
    Listener listener;

    ImageView categoryAdder, typeAdder;

    TransactionDAO transactionDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        montantEt = view.findViewById(R.id.budgetAmountEt);
        categoryEt = view.findViewById(R.id.budgetCatecoryEt);
        descriptionEt = view.findViewById(R.id.budgetDescriptionEt);
        okButton = view.findViewById(R.id.okButton);
        categoryAdder = view.findViewById(R.id.budgetCategoryAdder);
        categorieDAO = new CategorieDAO(getContext());
        transactionDAO = new TransactionDAO(getContext());

        // Ajout d'un écouteur de clic sur categoryEt

        okButton.setOnClickListener(v -> {
            String montant = montantEt.getText().toString().trim();
            String categoryName = categoryEt.getText().toString().trim();
            String description = descriptionEt.getText().toString().trim();

            Categorie categorie = categorieDAO.getCategorieByName(categoryName);
            User user = Session.getCurrentUser();

            if (montant.isEmpty() || categoryName.isEmpty() || description.isEmpty()) {
                Toast.makeText(getContext(), "Remplissez tous les champs", Toast.LENGTH_SHORT).show();

            } else {
                Transaction transaction = new Transaction(
                        user.getId(),
                        categorieDAO.getCategorieByName(categoryName).getId(),
                        Double.parseDouble(montant),
                        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        "dddddd"
                        );
                long res = transactionDAO.addTransaction(transaction);
                if(listener!=null)
                    listener.onSave();
                Toast.makeText(getContext(), "Transaction enregistrée", Toast.LENGTH_SHORT).show();
                dismiss(); // Ferme le bottom sheet
            }
        });

        categoryAdder.setOnClickListener(v -> {
            List<Categorie> allCategories = categorieDAO.getAllCategories();
            List<String> categories = categorieDAO.getCategoriesName(allCategories);
            SelectorDialog dialog = new SelectorDialog(getContext(), categories, false);

            try {
                dialog.showCategorySelectionDialog(selectedCategories -> {
                    categoryEt.setText(String.join(", ", selectedCategories)); // Afficher les catégories sélectionnées
                });
            } catch (Exception e) {
                Log.d("TAG", e.getMessage());
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public interface Listener {
        public void onSave();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

}
