package project.isep.com.opendays.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fahad on 13-01-2016.
 */
public class OpenDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "conference.db";

    public OpenDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CONFERENCE_TABLE = "CREATE TABLE " + OpenContract.ConferenceEntry.TABLE_NAME + " (" +
                OpenContract.ConferenceEntry._ID + " INTEGER PRIMARY KEY, " +
                OpenContract.ConferenceEntry.COLUMN_CONF_TITLE + " TEXT NOT NULL );";

        db.execSQL(SQL_CREATE_CONFERENCE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OpenContract.ConferenceEntry.TABLE_NAME);
        onCreate(db);
    }
}
