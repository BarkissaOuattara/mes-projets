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

import com.example.myapplication.Controller.CategorieDAO;
import com.example.myapplication.Model.Categorie;
import com.example.myapplication.R;
import com.example.myapplication.selectors.SelectorDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheetCategory extends BottomSheetDialogFragment {

    private EditText nomCategoryEt, descriptionCategoryEt, categoryTypeEt;
    private Button okButton;
    private ImageView categoryTypeAdder;
    CategorieDAO categorieDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categorieDAO = new CategorieDAO(getContext());

        nomCategoryEt = view.findViewById(R.id.nomCategoryEt);
        categoryTypeEt = view.findViewById(R.id.categoryTypeEt);
        descriptionCategoryEt = view.findViewById(R.id.descriptionCategoryEt);
        okButton = view.findViewById(R.id.okButton);
        categoryTypeAdder = view.findViewById(R.id.categoryTypeAdder);

        okButton.setOnClickListener(v -> {
            String nom = nomCategoryEt.getText().toString().trim();
            String type = categoryTypeEt.getText().toString().trim();

            Categorie categorie = new Categorie(nom, type);
            try {
               long res = categorieDAO.addCategory(categorie);
                Toast.makeText(getContext(), "ajout succes", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        });

        categoryTypeAdder.setOnClickListener(v -> {
            List<String> types = List.of("revenu", "depense");
            SelectorDialog dialog = new SelectorDialog(getContext(), types, false);

            dialog.showCategorySelectionDialog(selectedCategories -> {
                categoryTypeEt.setText(String.join(", ", selectedCategories)); // Afficher les catégories sélectionnées
            });
        });
    }

    public interface listener{
        public void onSave();
    }
}
