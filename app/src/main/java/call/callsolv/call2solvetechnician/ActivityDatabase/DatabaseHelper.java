package call.callsolv.call2solvetechnician.ActivityDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME ="TECH";
    private static final int DB_VERSION = 1;
    public static final String TABLE_FLAGTABLE ="flag";
    public static final String TABLE_ID ="id";
    public static final String TABLE_PENDINGCOUNT="pendingcount";
    ////////////////////////////////////////////////////////////
    ///////////////////SECOND TABLE///////////////////////////
    public static final String TABLE_FLAGTABLE1 = "flag1";
    public static final String TABLE_ID1 = "id1";
    public static final String TABLE_PENDINGCOUNT1 = "pendingcount1";
    /////////////////////////////////////////////////////
    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_FLAGTABLE
                + "(" + TABLE_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_PENDINGCOUNT + " VARCHAR);";
        db.execSQL(sql);
        String sql2 = "CREATE TABLE " + TABLE_FLAGTABLE1
                + "(" + TABLE_ID1 +
                " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_PENDINGCOUNT1 + " VARCHAR);";
        db.execSQL(sql2);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS TABLE_FLAGTABLE";
        db.execSQL(sql);
        String sql1 = "DROP TABLE IF EXISTS TABLE_FLAGTABLE1";
        db.execSQL(sql1);
    }
    ///////////////////////////////////////////////////////////////////////////////
    public boolean addpending(int pendingcount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_PENDINGCOUNT,pendingcount);
        db.insert(TABLE_FLAGTABLE, null, contentValues);
        db.close();
        return true;
    }
    public boolean updateDatapending(String id,int pendingcount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_ID,id);
        contentValues.put(TABLE_PENDINGCOUNT,pendingcount);
        db.update(TABLE_FLAGTABLE, contentValues, "id = ?",new String[] { id });
        return true;
    }
    public Cursor getAllLoginData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_FLAGTABLE,null);
        return res;
    }
///////////////////////////////////////////////////////////////////////////////////////////////
public boolean addpending1(int pendingcount1){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(TABLE_PENDINGCOUNT1,pendingcount1);
    db.insert(TABLE_FLAGTABLE1, null, contentValues);
    db.close();
    return true;
}
    public boolean updateDatapending1(String id1,int pendingcount1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_ID1,id1);
        contentValues.put(TABLE_PENDINGCOUNT1,pendingcount1);
        db.update(TABLE_FLAGTABLE1, contentValues, "id1 = ?",new String[] { id1 });
        return true;
    }
    public Cursor getAllLoginData1() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_FLAGTABLE1,null);
        return res;
    }

}
