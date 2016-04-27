package com.adkdevelopment.e_contact.provider;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.adkdevelopment.e_contact.BuildConfig;
import com.adkdevelopment.e_contact.provider.base.BaseContentProvider;
import com.adkdevelopment.e_contact.provider.photos.PhotosColumns;
import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;

public class TasksProvider extends BaseContentProvider {
    private static final String TAG = TasksProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.adkdevelopment.e_contact.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_PHOTOS = 0;
    private static final int URI_TYPE_PHOTOS_ID = 1;

    private static final int URI_TYPE_TASKS = 2;
    private static final int URI_TYPE_TASKS_ID = 3;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, PhotosColumns.TABLE_NAME, URI_TYPE_PHOTOS);
        URI_MATCHER.addURI(AUTHORITY, PhotosColumns.TABLE_NAME + "/#", URI_TYPE_PHOTOS_ID);
        URI_MATCHER.addURI(AUTHORITY, TasksColumns.TABLE_NAME, URI_TYPE_TASKS);
        URI_MATCHER.addURI(AUTHORITY, TasksColumns.TABLE_NAME + "/#", URI_TYPE_TASKS_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return TasksSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_PHOTOS:
                return TYPE_CURSOR_DIR + PhotosColumns.TABLE_NAME;
            case URI_TYPE_PHOTOS_ID:
                return TYPE_CURSOR_ITEM + PhotosColumns.TABLE_NAME;

            case URI_TYPE_TASKS:
                return TYPE_CURSOR_DIR + TasksColumns.TABLE_NAME;
            case URI_TYPE_TASKS_ID:
                return TYPE_CURSOR_ITEM + TasksColumns.TABLE_NAME;

        }
        return null;
    }

    /* Save for the debug in the future
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));

        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }
    */

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_PHOTOS:
            case URI_TYPE_PHOTOS_ID:
                res.table = PhotosColumns.TABLE_NAME;
                res.idColumn = PhotosColumns._ID;
                res.tablesWithJoins = PhotosColumns.TABLE_NAME;
                if (TasksColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + TasksColumns.TABLE_NAME + " AS " + PhotosColumns.PREFIX_TASKS + " ON " + PhotosColumns.TABLE_NAME + "." + PhotosColumns.TASK_ID + "=" + PhotosColumns.PREFIX_TASKS + "." + TasksColumns._ID;
                }
                res.orderBy = PhotosColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TASKS:
            case URI_TYPE_TASKS_ID:
                res.table = TasksColumns.TABLE_NAME;
                res.idColumn = TasksColumns._ID;
                res.tablesWithJoins = TasksColumns.TABLE_NAME;
                res.orderBy = TasksColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_PHOTOS_ID:
            case URI_TYPE_TASKS_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
