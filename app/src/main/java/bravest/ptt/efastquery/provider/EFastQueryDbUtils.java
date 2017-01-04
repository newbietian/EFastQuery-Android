package bravest.ptt.efastquery.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by root on 1/4/17.
 */

public class EFastQueryDbUtils {

    private static final String AUTHORITY = "bravest.ptt.efastquery";

    private static final String TAG = "EFastQueryDbUtils";

    public static abstract interface History extends BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final Uri CONTENT_URI = Uri.parse("content://bravest.ptt.efastquery/history");
        public static final String DATE = "date";
        public static final String REQUEST = "request";
        public static final String RESULT = "result";
    }
}