package gravedigger.example.ra127_2014.com.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jelena on 6/1/2017.
 */

public class TaskDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "taskovi.db";
    public static final int DATABASE_VERSION = 1;
    public String TAG = "TaskDatabase";

    public static final String TABLE_NAME = "Tasks";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TASK_NAME = "TaskName";
    public static final String COLUMN_DESC = "Description";
    public static final String COLUMN_PRIORITY = "Priority";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_TIME = "Time";
    public static final String COLUMN_REMINDER = "Reminder";
    public static final String COLUMN_CHECKED = "TaskDone";

    public TaskDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: usao");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TASK_NAME + " TEXT, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_PRIORITY + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_REMINDER + " TEXT, " +
                COLUMN_CHECKED + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: usao");
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(Zadatak zadatak){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TASK_NAME, zadatak.getIme());
        contentValues.put(COLUMN_DESC, zadatak.getOpis());
        contentValues.put(COLUMN_PRIORITY, zadatak.getVaznost());
        contentValues.put(COLUMN_DATE, zadatak.getDatum());
        contentValues.put(COLUMN_TIME, zadatak.getVreme());
        contentValues.put(COLUMN_REMINDER, String.valueOf(zadatak.isPodsetnik()));
        contentValues.put(COLUMN_CHECKED, String.valueOf(zadatak.isZavrsen()));

        long result = db.insert(TABLE_NAME, null, contentValues);
        close();

        if(result == -1)
            return false;
        else
            return true;
    }

    public void updateTask(Zadatak zadatak, int id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TASK_NAME, zadatak.getIme());
        contentValues.put(COLUMN_DESC, zadatak.getOpis());
        contentValues.put(COLUMN_PRIORITY, zadatak.getVaznost());
        contentValues.put(COLUMN_DATE, zadatak.getDatum());
        contentValues.put(COLUMN_TIME, zadatak.getVreme());
        contentValues.put(COLUMN_REMINDER, String.valueOf(zadatak.isPodsetnik()));
        contentValues.put(COLUMN_CHECKED, String.valueOf(zadatak.isZavrsen()));

        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[] {String.valueOf(id+1)});
        close();
    }

    public Zadatak[] readTasks(){
        Log.d(TAG, "readTasks");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        if(cursor.getCount() <= 0){
            return null;
        }

        Zadatak[] zadaci = new Zadatak[cursor.getCount()];
        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            zadaci[i] = createTask(cursor);
            i++;
        }

        cursor.close();
        return zadaci;
    }

    public Zadatak readTask(int id){
        Log.d(TAG, "readTask");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[] {String.valueOf(id+1)}, null, null, null);
        cursor.moveToFirst();
        Zadatak zadatak = createTask(cursor);

        cursor.close();
        return zadatak;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_NAME,null,null);
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

    public void deleteTask(int id){
        Log.d(TAG, "deleteTask: " + id);
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] {String.valueOf(id+1)});
        db.close();
    }

    private Zadatak createTask(Cursor cursor) {
        Log.d(TAG, "createTask: ");
        Log.d(TAG, "cursor " + cursor.getCount());
        String imeZadatka = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
        String opisZadatka = cursor.getString(cursor.getColumnIndex(COLUMN_DESC));
        String prioritet = cursor.getString(cursor.getColumnIndex(COLUMN_PRIORITY));
        String datum = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
        String vreme = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
        String podsetnik = cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER));
        String zavrsen = cursor.getString(cursor.getColumnIndex(COLUMN_CHECKED));
        int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));

        return new Zadatak(imeZadatka, opisZadatka, Boolean.parseBoolean(podsetnik), prioritet, datum, vreme, Boolean.parseBoolean(zavrsen), id);
    }
}
