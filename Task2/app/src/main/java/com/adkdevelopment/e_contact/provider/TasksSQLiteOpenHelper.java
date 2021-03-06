package com.adkdevelopment.e_contact.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.adkdevelopment.e_contact.BuildConfig;
import com.adkdevelopment.e_contact.provider.photos.PhotosColumns;
import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;

public class TasksSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = TasksSQLiteOpenHelper.class.getSimpleName();

    private static final String DATABASE_FILE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;
    private static TasksSQLiteOpenHelper sInstance;

    // @formatter:off
    private static final String SQL_CREATE_TABLE_PHOTOS = "CREATE TABLE IF NOT EXISTS "
            + PhotosColumns.TABLE_NAME + " ( "
            + PhotosColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PhotosColumns.URL + " TEXT NOT NULL, "
            + PhotosColumns.TASK_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_task_id FOREIGN KEY (" + PhotosColumns.TASK_ID + ") REFERENCES tasks (_id) ON DELETE CASCADE"
            + ", CONSTRAINT unique_id UNIQUE (task_id, url) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_TASKS = "CREATE TABLE IF NOT EXISTS "
            + TasksColumns.TABLE_NAME + " ( "
            + TasksColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TasksColumns.ID_TASK + " INTEGER NOT NULL UNIQUE, "
            + TasksColumns.STATUS + " INTEGER, "
            + TasksColumns.TYPE + " INTEGER, "
            + TasksColumns.DESCRIPTION + " TEXT, "
            + TasksColumns.ADDRESS + " TEXT, "
            + TasksColumns.RESPONSIBLE + " TEXT, "
            + TasksColumns.DATE_CREATED + " INTEGER, "
            + TasksColumns.DATE_REGISTERED + " INTEGER, "
            + TasksColumns.DATE_ASSIGNED + " INTEGER, "
            + TasksColumns.LONGITUDE + " REAL, "
            + TasksColumns.LATITUDE + " REAL, "
            + TasksColumns.LIKES + " INTEGER, "
            + TasksColumns.TITLE + " TEXT"
            + ", CONSTRAINT unique_id UNIQUE (id_task) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static TasksSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static TasksSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }

    /*
     * Pre Honeycomb.
     */
    private static TasksSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new TasksSQLiteOpenHelper(context);
    }

    private TasksSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static TasksSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new TasksSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private TasksSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        db.execSQL(SQL_CREATE_TABLE_PHOTOS);
        db.execSQL(SQL_CREATE_TABLE_TASKS);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TasksColumns.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + PhotosColumns.TABLE_NAME);
            db.execSQL(SQL_CREATE_TABLE_TASKS);
            db.execSQL(SQL_CREATE_TABLE_PHOTOS);
        }
    }
}
