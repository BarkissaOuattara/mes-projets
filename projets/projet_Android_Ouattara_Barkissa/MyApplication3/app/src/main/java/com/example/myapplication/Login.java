package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Ajout pour le débogage
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controller.UserDAO;
import com.example.myapplication.Model.User;

public class Login extends AppCompatActivity {
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDAO = new UserDAO(this);  // Initialisation du DAO
    }

    // Méthode appelée quand on clique sur le bouton "Connexion"
    public void onLogin(View view) {
        EditText emailEt = findViewById(R.id.emailLoginEt);
        EditText passwordEt = findViewById(R.id.passwordLoginEt);

       //emailEt.setText("admin@admin");
       //passwordEt.setText("1234");

        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();

        // Vérifier si tous les champs sont remplis
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérifier si l'utilisateur existe
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            Toast.makeText(this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
            return;
        }

        // Affichage du mot de passe récupéré (juste pour test)
        Log.d("DEBUG_LOGIN", "Mot de passe en base: " + user.getPasswordHash());

        // Vérifier le mot de passe
        if (!user.getPasswordHash().equals(password)) {
            Toast.makeText(this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

        // Connexion réussie
        Session.setCurrentUser(user);
        Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show();
        Intent welcomeIntent = new Intent(this, Welcome.class);
        startActivity(welcomeIntent);
        finish();
    }

    public void onNoAccount(View view) {
        Intent registerIntent = new Intent(this, Signup.class);
        startActivity(registerIntent);
    }
}
