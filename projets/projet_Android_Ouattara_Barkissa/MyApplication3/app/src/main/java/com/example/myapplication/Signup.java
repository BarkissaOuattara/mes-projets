package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controller.UserDAO;
import com.example.myapplication.Model.User;

public class Signup extends AppCompatActivity {
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userDAO = new UserDAO(this);
    }

    public void onSignup(View view) {
        EditText emailEt = findViewById(R.id.emailRegisterEt);
        EditText nameEt = findViewById(R.id.nameRegisterEt);
        EditText passwordEt = findViewById(R.id.passwordRegisterEt);

        String name = nameEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();

        // Vérification des champs
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }


        Log.d("Signup", "Vérification de l'email en base de données...");
        if (userDAO.checkEmailExists(email)) {
            Log.e("Signup", "Email déjà utilisé: " + email);
            Toast.makeText(this, "Cet email est déjà utilisé", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            User newUser = new User(0, name, email, password); // id = 0 car auto-incrémenté
            Log.d("Signup", "Tentative d'inscription pour : " + name + ", " + email + ",  " + password);
            boolean success = userDAO.insertUser(newUser);

            if (success) {
                Log.d("Signup", "Utilisateur inscrit avec succès !");
                Toast.makeText(this, "Vous êtes inscrit avec succès", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Login.class));
                finish();
            } else {
                Log.e("Signup", "Échec de l'inscription !");
                Toast.makeText(this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onNoSignup(View view) {
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}
