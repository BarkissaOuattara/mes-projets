package com.example.myapplication.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Model.Categorie;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {
    private SQLiteDatabase db;

    public CategorieDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addCategory(Categorie category) {
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("type", category.getType());
        return db.insert("categories", null, values);
    }

    public List<Categorie> getAllCategories() {
        List<Categorie> categories = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM categories", null);
        while (cursor.moveToNext()) {
            categories.add(new Categorie(
                    cursor.getInt(0),
                    cursor.getString(2),
                    cursor.getString(1)));
        }
        cursor.close();
        return categories;
    }

    public void deleteCategory(int id) {
        db.delete("categories", "id=?", new String[]{String.valueOf(id)});
    }

    public List<String> getCategoriesName(List<Categorie> categories) {
        if(categories.isEmpty())
            return null;
        List<String> selectedCategories = new ArrayList<>();
        for(Categorie categorie : categories){
            selectedCategories.add(categorie.getName());
        }
        return selectedCategories;
    }

    public Categorie getCategorieByName(String name){
        Categorie categorie = null;
        Cursor cursor = db.rawQuery("SELECT * FROM categories where name = ?", new String[]{name});
        if (cursor.moveToFirst())
            categorie = new Categorie(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2));

        cursor.close();
        return categorie;
    }

    public Categorie getCategorieById(int id){
        Categorie categorie = null;
        Cursor cursor = db.rawQuery("SELECT * FROM categories where id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst())
            categorie = new Categorie(cursor.getInt(0),
                    cursor.getString(2),
                    cursor.getString(1));

        cursor.close();
        return categorie;
    }
}
