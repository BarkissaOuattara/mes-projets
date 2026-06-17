package com.example.myapplication.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Model.Budget;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {
    private SQLiteDatabase db;

    public BudgetDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Ajouter un budget
    public long addBudget(Budget budget) {
        ContentValues values = new ContentValues();
        values.put("user_id", budget.getUserId());
        values.put("amount_limit", budget.getAmountLimit());
        values.put("budgetname", budget.getBudgetName());
        values.put("date", budget.getDate());
        values.put("month", budget.getMonth());

        return db.insert("budgets", null, values);
    }

    // Récupérer les budgets d'un utilisateur
    public List<Budget> getBudgetsByUser(int userId) {
        List<Budget> budgets = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM budgets WHERE user_id=?", new String[]{String.valueOf(userId)});

        while (cursor.moveToNext()) {
            Budget budget = new Budget(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("amount_limit")),
                    cursor.getString(cursor.getColumnIndexOrThrow("budgetname")),
                    cursor.getString(cursor.getColumnIndexOrThrow("date")),
                    cursor.getString(cursor.getColumnIndexOrThrow("month"))
            );
            budgets.add(budget);
        }
        cursor.close();
        return budgets;
    }

    // Supprimer un budget par ID
    public int deleteBudget(int id) {
        return db.delete("budgets", "id=?", new String[]{String.valueOf(id)});
    }
}
