package project.isep.com.opendays.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by fahad on 13-01-2016.
 */
public class OpenContract {

    public static final String CONTENT_AUTHORITY = "project.isep.com.opendays";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CONFERENCE = "conference";

    public static final class ConferenceEntry implements BaseColumns {
        public static final String TABLE_NAME = "conference";

        public static final String COLUMN_CONF_TITLE = "conf_title";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONFERENCE).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONFERENCE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONFERENCE;

        public static Uri buildConferenceUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
