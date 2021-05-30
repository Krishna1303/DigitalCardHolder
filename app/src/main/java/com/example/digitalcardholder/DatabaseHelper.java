package com.example.digitalcardholder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    String tableName = "CardDetails";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        query = "CREATE TABLE " + tableName + "(CardImageId int," +
                "CardName String ," +
                "CardNumber String PRIMARY KEY," +
                "CardHolderName String," +
                "CardExpiryDate String," +
                "CardCVV String)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        onCreate(db);
    }
    public void addValuesInTable(int imageId,String name,String number,String holderName,String expiry,String cvv){
        SQLiteDatabase s = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CardImageId",imageId);
        values.put("CardName",name);
        values.put("CardNumber",number);
        values.put("CardHolderName",holderName);
        values.put("CardExpiryDate",expiry);
        values.put("CardCVV",cvv);
        s.insert(tableName,null,values);
        s.close();

    }
    public ArrayList<CardDetails> getTableValues(){
        ArrayList<CardDetails> list = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        if(c.moveToFirst()){
            do{
                CardDetails card;
                card = new CardDetails(Integer.parseInt(c.getString(0)),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5));
                list.add(card);
            }while(c.moveToNext());
        }
        return list;
    }
    public void deleteCard(String cardName){
        SQLiteDatabase s = this.getWritableDatabase();
        s.delete(tableName,"CardNumber="+cardName,null);
        s.close();
    }
}
