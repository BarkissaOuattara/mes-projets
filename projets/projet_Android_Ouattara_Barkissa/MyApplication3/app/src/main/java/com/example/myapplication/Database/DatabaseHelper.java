package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.Model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "budget_manager.db";
    private static final int DATABASE_VERSION = 1;

    // Table Utilisateurs
    private static final String TABLE_USERS = "CREATE TABLE users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT UNIQUE NOT NULL, " +
            "email TEXT UNIQUE NOT NULL, " +
            "password_hash TEXT NOT NULL);";

    // Table Catégories
    private static final String TABLE_CATEGORIES = "CREATE TABLE categories (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "type TEXT CHECK( type IN ('revenu', 'depense') ) NOT NULL, " +
            "name TEXT UNIQUE NOT NULL);";

    // Table Transactions
    private static final String TABLE_TRANSACTIONS = "CREATE TABLE transactions (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id INTEGER NOT NULL, " +
            "category_id INTEGER NOT NULL, " +
            "amount REAL NOT NULL, " +
            "date TEXT NOT NULL, " +
            "description TEXT, " +
            "FOREIGN KEY (user_id) REFERENCES users(id), " +
            "FOREIGN KEY (category_id) REFERENCES categories(id));";

    // Table Budgets
    private static final String TABLE_BUDGETS = "CREATE TABLE budgets (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id INTEGER NOT NULL, " +
            "amount_limit REAL NOT NULL, " +
            "budgetname TEXT UNIQUE NOT NULL, " +
            "date TEXT NOT NULL, " +
            "month TEXT NOT NULL, " +
            "FOREIGN KEY (user_id) REFERENCES users(id)); ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Database", "Creating tables...");
        db.execSQL(TABLE_USERS);
        db.execSQL(TABLE_CATEGORIES);
        db.execSQL(TABLE_TRANSACTIONS);
        db.execSQL(TABLE_BUDGETS);

        // Vérifier si la table users est créée
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='users'", null);
        if (cursor.getCount() > 0) {
            Log.d("Database", "Table 'users' créée avec succès !");
        } else {
            Log.e("Database", "Erreur : la table 'users' n'a pas été créée.");
        }
        cursor.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Database", "Mise à jour de la base de données : suppression des anciennes tables.");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL("DROP TABLE IF EXISTS transactions");
        db.execSQL("DROP TABLE IF EXISTS budgets");
        onCreate(db);
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;
        try {
            cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
            exists = (cursor.getCount() > 0);
        } catch (Exception e) {
            Log.e("Database", "Error checking email: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return exists;
    }


    public boolean insertTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase(); // Accès à la base via l'instance 'this'
        ContentValues values = new ContentValues();

        values.put("user_id", transaction.getUserId());
        values.put("category_id", transaction.getCategoryId());
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());
        values.put("description", transaction.getDescription());

        long result = db.insert("transactions", null, values);
        return result != -1;
    }

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); // ✅ Plus d'erreur

        String query = "SELECT * FROM transactions";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                transactionList.add(new Transaction(id, userId, categoryId, amount, date, description));

            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return transactionList;
    }





    public boolean insertUser(String username, String email, String passwordHash) {
        if (checkEmailExists(email)) {
            Log.e("Database", "Email already exists: " + email);
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password_hash", passwordHash);

        try {
            long result = db.insertOrThrow("users", null, values);
            if (result == -1) {
                Log.e("Database", "Échec de l'insertion de l'utilisateur: " + username);
                return false;
            } else {
                Log.d("Database", "Utilisateur inséré avec succès: " + username);
                return true;
            }
        } catch (Exception e) {
            Log.e("Database", "Erreur SQL lors de l'insertion: " + e.getMessage());
            return false;
        }
    }

}
