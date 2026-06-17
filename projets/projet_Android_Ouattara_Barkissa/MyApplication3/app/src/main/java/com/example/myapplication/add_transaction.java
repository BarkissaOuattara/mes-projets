package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Controller.TransactionDAO;
import com.example.myapplication.Model.Transaction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class add_transaction extends AppCompatActivity {
    private EditText amountEt, descriptionEt;
    private Spinner categorySpinner, typeSpinner;
    private TransactionDAO transactionDAO;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        amountEt = findViewById(R.id.amountEt);
        descriptionEt = findViewById(R.id.budgetDescriptionEt);
        categorySpinner = findViewById(R.id.categorySpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        transactionDAO = new TransactionDAO(this);

        // Remplir les spinners
        String[] categories = {"Alimentation", "Transport", "Loisirs", "Logement"};
        categorySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));

        String[] types = {"income", "expense"};
        typeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types));
    }

    public void onAddTransaction(View view) {
        try {
            Log.d("DEBUG", "Ajout de transaction démarré");

            if (amountEt.getText().toString().isEmpty()) {
                Toast.makeText(this, "Veuillez entrer un montant", Toast.LENGTH_SHORT).show();
                Log.e("ERROR", "Montant vide !");
                return;
            }

            int userId = 1;
            int categoryId = categorySpinner.getSelectedItemPosition() + 1;
            String type = typeSpinner.getSelectedItem().toString();
            double amount = Double.parseDouble(amountEt.getText().toString());
            String description = descriptionEt.getText().toString();
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            Transaction transaction = new Transaction(0, userId, categoryId, amount, date, description);
            Log.d("DEBUG", "Transaction créée : " + transaction.toString());

            boolean success = transactionDAO.insertTransaction(transaction);

            if (success) {
                Log.d("DEBUG", "Transaction insérée avec succès !");
                Toast.makeText(this, "Transaction ajoutée !", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.e("ERROR", "Échec de l'insertion en base de données");
                Toast.makeText(this, "Erreur d'ajout", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Montant invalide", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "Format du montant incorrect", e);
        } catch (Exception e) {
            Toast.makeText(this, "Erreur inconnue", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "Crash détecté", e);
        }
    }

}
