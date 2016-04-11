package com.adkdevelopment.e_contact.provider.photos;

import android.net.Uri;
import android.provider.BaseColumns;

import com.adkdevelopment.e_contact.provider.TasksProvider;
import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;

/**
 * News
 */
public class PhotosColumns implements BaseColumns {
    public static final String TABLE_NAME = "photos";
    public static final Uri CONTENT_URI = Uri.parse(TasksProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * URL
     */
    public static final String URL = "url";

    /**
     * Foreign Key
     */
    public static final String TASK_ID = "task_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            URL,
            TASK_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(URL) || c.contains("." + URL)) return true;
            if (c.equals(TASK_ID) || c.contains("." + TASK_ID)) return true;
        }
        return false;
    }

    public static final String PREFIX_TASKS = TABLE_NAME + "__" + TasksColumns.TABLE_NAME;
}
