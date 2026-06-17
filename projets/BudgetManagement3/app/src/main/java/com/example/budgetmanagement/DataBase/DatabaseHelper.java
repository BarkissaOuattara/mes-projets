package com.example.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom et version de la base de données
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
            "name TEXT UNIQUE NOT NULL);";

    // Table Transactions
    private static final String TABLE_TRANSACTIONS = "CREATE TABLE transactions (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id INTEGER NOT NULL, " +
            "category_id INTEGER NOT NULL, " +
            "amount REAL NOT NULL, " +
            "type TEXT CHECK( type IN ('income', 'expense') ) NOT NULL, " +
            "date TEXT NOT NULL, " +
            "description TEXT, " +
            "FOREIGN KEY (user_id) REFERENCES users(id), " +
            "FOREIGN KEY (category_id) REFERENCES categories(id));";

    // Table Budgets
    private static final String TABLE_BUDGETS = "CREATE TABLE budgets (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id INTEGER NOT NULL, " +
            "category_id INTEGER NOT NULL, " +
            "amount_limit REAL NOT NULL, " +
            "month TEXT NOT NULL, " +
            "FOREIGN KEY (user_id) REFERENCES users(id), " +
            "FOREIGN KEY (category_id) REFERENCES categories(id));";

    // Table Alertes

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USERS);
        db.execSQL(TABLE_CATEGORIES);
        db.execSQL(TABLE_TRANSACTIONS);
        db.execSQL(TABLE_BUDGETS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL("DROP TABLE IF EXISTS transactions");
        db.execSQL("DROP TABLE IF EXISTS budgets");
        onCreate(db);
    }
}
