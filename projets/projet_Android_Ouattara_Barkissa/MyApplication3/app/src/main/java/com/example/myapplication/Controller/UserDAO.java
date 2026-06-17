package com.example.myapplication.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Model.User;

public class UserDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context); // Initialisation du DatabaseHelper
        database = dbHelper.getWritableDatabase(); // Récupération de la base de données en écriture
    }

    // Vérifier si un email existe déjà dans la base de données
    public boolean checkEmailExists(String email) {
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean exists = (cursor.getCount() > 0);
        Log.d("UserDAO", "Email " + email + " existe : " + exists);
        cursor.close();
        return exists;
    }


    // Insérer un nouvel utilisateur dans la base de données
    public boolean insertUser(User user) {
        if (checkEmailExists(user.getEmail())) {
            Log.e("UserDAO", "Email déjà utilisé: " + user.getEmail());
            return false; // L'email est déjà utilisé
        }

        try {
            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("email", user.getEmail());
            values.put("password_hash", user.getPasswordHash());

            Log.d("UserDAO", "Insertion dans la table users: " + values);

            SQLiteDatabase db = dbHelper.getWritableDatabase(); // Assurez-vous que dbHelper.getWritableDatabase() est correct
            long result = db.insert("users", null, values);

            if (result == -1) {
                Log.e("UserDAO", "Erreur lors de l'insertion dans la base de données.");
                return false;
            } else {
                Log.d("UserDAO", "Utilisateur inséré avec succès. ID inséré: " + result);
                return true;
            }
        } catch (Exception e) {
            Log.e("UserDAO", "Erreur lors de l'insertion : " + e.getMessage());
            return false;
        }
    }


    // Récupérer un utilisateur par email
    public User getUserByEmail(String email) {
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String passwordHash = cursor.getString(cursor.getColumnIndexOrThrow("password_hash"));
            cursor.close();
            return new User(id, username, email, passwordHash);
        }
        cursor.close();
        return null; // Aucun utilisateur trouvé
    }

    // Fermer la connexion à la base de données
    public void close() {
        database.close();
    }
}
