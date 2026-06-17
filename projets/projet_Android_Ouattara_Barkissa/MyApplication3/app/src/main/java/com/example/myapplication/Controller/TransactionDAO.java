package com.example.myapplication.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Model.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper; // ✅ Garder l'instance de DatabaseHelper

    public TransactionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // ✅ Ajouter une transaction
    public long addTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put("user_id", transaction.getUserId()); // ✅ Vérifier le nom exact des colonnes
        values.put("category_id", transaction.getCategoryId());
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());
        values.put("description", transaction.getDescription());

        return db.insert("transactions", null, values);
    }

    // ✅ Récupérer les transactions par utilisateur
    public List<Transaction> getTransactionsByUser(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM transactions WHERE user_id=?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                transactions.add(new Transaction(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("category_id")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

    public boolean insertTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put("user_id", transaction.getUserId());
        values.put("category_id", transaction.getCategoryId());
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());
        values.put("description", transaction.getDescription());

        long result = db.insert("transactions", null, values);
        return result != -1;
    }



    // ✅ Récupérer toutes les transactions
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM transactions", null);

        if (cursor.moveToFirst()) {
            do {
                transactionList.add(new Transaction(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("category_id")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactionList;
    }

    // ✅ Supprimer une transaction
    public void deleteTransaction(int id) {
        db.delete("transactions", "id=?", new String[]{String.valueOf(id)});
    }

    // ✅ Fermer la base de données
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
