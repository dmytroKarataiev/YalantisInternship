package com.adkdevelopment.e_contact.provider.tasks;

import android.net.Uri;
import android.provider.BaseColumns;

import com.adkdevelopment.e_contact.provider.TasksProvider;
import com.adkdevelopment.e_contact.provider.photos.PhotosColumns;
import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;

/**
 * A task object
 */
public class TasksColumns implements BaseColumns {
    public static final String TABLE_NAME = "tasks";
    public static final Uri CONTENT_URI = Uri.parse(TasksProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Task Id
     */
    public static final String ID_TASK = "id_task";

    /**
     * Status
     */
    public static final String STATUS = "status";

    /**
     * Type
     */
    public static final String TYPE = "type";

    /**
     * Description
     */
    public static final String DESCRIPTION = "description";

    /**
     * Address
     */
    public static final String ADDRESS = "address";

    /**
     * Responsible
     */
    public static final String RESPONSIBLE = "responsible";

    /**
     * Date Created
     */
    public static final String DATE_CREATED = "date_created";

    /**
     * Date Registered
     */
    public static final String DATE_REGISTERED = "date_registered";

    /**
     * Date Assigned
     */
    public static final String DATE_ASSIGNED = "date_assigned";

    /**
     * Longitude
     */
    public static final String LONGITUDE = "longitude";

    /**
     * Latitude
     */
    public static final String LATITUDE = "latitude";

    /**
     * Likes
     */
    public static final String LIKES = "likes";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ID_TASK,
            STATUS,
            TYPE,
            DESCRIPTION,
            ADDRESS,
            RESPONSIBLE,
            DATE_CREATED,
            DATE_REGISTERED,
            DATE_ASSIGNED,
            LONGITUDE,
            LATITUDE,
            LIKES
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(ID_TASK) || c.contains("." + ID_TASK)) return true;
            if (c.equals(STATUS) || c.contains("." + STATUS)) return true;
            if (c.equals(TYPE) || c.contains("." + TYPE)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(ADDRESS) || c.contains("." + ADDRESS)) return true;
            if (c.equals(RESPONSIBLE) || c.contains("." + RESPONSIBLE)) return true;
            if (c.equals(DATE_CREATED) || c.contains("." + DATE_CREATED)) return true;
            if (c.equals(DATE_REGISTERED) || c.contains("." + DATE_REGISTERED)) return true;
            if (c.equals(DATE_ASSIGNED) || c.contains("." + DATE_ASSIGNED)) return true;
            if (c.equals(LONGITUDE) || c.contains("." + LONGITUDE)) return true;
            if (c.equals(LATITUDE) || c.contains("." + LATITUDE)) return true;
            if (c.equals(LIKES) || c.contains("." + LIKES)) return true;
        }
        return false;
    }

}
