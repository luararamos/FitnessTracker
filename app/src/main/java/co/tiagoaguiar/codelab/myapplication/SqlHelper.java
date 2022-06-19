package co.tiagoaguiar.codelab.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqlHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "fitness_tracker.db";
    private static final int DB_VERSION = 1;
    private static SqlHelper INSTANCE;

    public static SqlHelper getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SqlHelper(context);
        return INSTANCE;
    }

    private SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                // nome da tabela é calc ** (ideal é armazenar numa constante)
                "CREATE TABLE calc(id INTEGER primary key, type_calc TEXT, res DECIMAL, created_date DATETIME)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Teste", "on Upgrade disparado");
    }

    List<Register> getRegisterBY(String type) {
        List<Register> registers = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM calc WHERE type_calc = ?", new String[]{type});

        try {
            if (cursor.moveToFirst()) {
                do {
                    Register register = new Register();
                    register.id = cursor.getInt(cursor.getColumnIndex("id"));
                    register.type = cursor.getString(cursor.getColumnIndex("type_calc"));
                    register.response = cursor.getDouble(cursor.getColumnIndex("res"));
                    register.createdDate = cursor.getString(cursor.getColumnIndex("created_date"));

                    registers.add(register);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("SQL", e.getMessage(), e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

        }
        return registers;
    }



    long addItem(String type, double response) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long calcId = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("type_calc", type);
            values.put("res", response);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
            String now = simpleDateFormat.format(new Date());

            values.put("created_date", now);
            // calc vem daqui **
            //INSERT INTO(ID, ) VALUES (SLDKJ)
            calcId = db.insertOrThrow("calc", null, values);
            values.put("id", calcId);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("SQLite", e.getMessage(), e);
        } finally {
            if (db.isOpen())
                db.endTransaction();

        }
        return calcId;
    }

    long updateItem(String type, double response, int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long calcId= 0;
        try {
            ContentValues values = new ContentValues();
            values.put("type_calc", type);
            values.put("res", response);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
            String now = simpleDateFormat.format(new Date());

            values.put("created_date", now);

            String[] stUpdate = new String[]{String.valueOf(id),type};
            calcId = db.update("calc", values,"id=? and type_calc=?", stUpdate);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("SQLite", e.getMessage(), e);
        } finally {
            if (db.isOpen())
                db.endTransaction();

        }
        return calcId;
    }

    public long removeItem(String type, int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long calcId= 0;
        try {
            String[] stRemove = new String[]{String.valueOf(id),type};
            calcId = db.delete("calc", "id=? and type_calc=?", stRemove);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("SQLite", e.getMessage(), e);
        } finally {
            if (db.isOpen())
                db.endTransaction();

        }
        return calcId;
    }

}
