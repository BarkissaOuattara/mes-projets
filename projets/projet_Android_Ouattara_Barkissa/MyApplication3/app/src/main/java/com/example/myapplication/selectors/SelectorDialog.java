package com.example.myapplication.selectors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.List;

public class SelectorDialog {

    private Context context;
    private List<String> categories;
    private List<String> selectedCategories;
    private boolean isMultipleChoice;  // Paramètre pour déterminer si la sélection multiple est autorisée

    public SelectorDialog(Context context, List<String> categories, boolean isMultipleChoice) {
        this.context = context;
        this.categories = categories;
        this.selectedCategories = new ArrayList<>();
        this.isMultipleChoice = isMultipleChoice;  // Assignation du paramètre
    }

    public void showCategorySelectionDialog(CategorySelectionListener listener) {
        boolean[] checkedItems = new boolean[categories.size()];
        String[] categoryArray = categories.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sélectionnez les catégories");

        // Si la sélection multiple est autorisée
        if (isMultipleChoice) {
            builder.setMultiChoiceItems(categoryArray, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked) {
                        selectedCategories.add(categories.get(which));
                    } else {
                        selectedCategories.remove(categories.get(which));
                    }
                }
            });
        } else {
            // Si la sélection est unique, on utilise setSingleChoiceItems
            builder.setSingleChoiceItems(categoryArray, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedCategories.clear();  // On vide la liste pour une sélection unique
                    selectedCategories.add(categories.get(which));
                }
            });
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCategoriesSelected(selectedCategories);  // Renvoie les catégories sélectionnées
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public interface CategorySelectionListener {
        void onCategoriesSelected(List<String> selectedCategories);
    }
}
