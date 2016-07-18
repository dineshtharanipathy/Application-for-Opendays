package project.isep.com.opendays.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by fahad on 13-01-2016.
 */
public class OpenProvider extends ContentProvider {

    static final int CONFERENCE = 100;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private OpenDbHelper mOpenHelper;

    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = OpenContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, OpenContract.PATH_CONFERENCE, CONFERENCE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new OpenDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        Cursor refCursor;
        switch(match){
            case CONFERENCE:
                refCursor = db.query(OpenContract.ConferenceEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException("UNkonown URI : " + uri);
        }
        refCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return refCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match){
            case CONFERENCE:
                return OpenContract.ConferenceEntry.CONTENT_TYPE;
            default: throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch(match){
            case CONFERENCE:
                long _id = db.insert(OpenContract.ConferenceEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri = OpenContract.ConferenceEntry.buildConferenceUri(_id);
                }else{
                    throw new android.database.SQLException("Failed to insert row in " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI : " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
