package com.adkdevelopment.e_contact.provider.photos;


import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adkdevelopment.e_contact.provider.base.AbstractCursor;
import com.adkdevelopment.e_contact.provider.tasks.*;

/**
 * Cursor wrapper for the {@code photos} table.
 */
public class PhotosCursor extends AbstractCursor implements PhotosModel {
    public PhotosCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(PhotosColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * URL
     * Cannot be {@code null}.
     */
    @NonNull
    public String getUrl() {
        String res = getStringOrNull(PhotosColumns.URL);
        if (res == null)
            throw new NullPointerException("The value of 'url' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Foreign Key
     */
    public long getTaskId() {
        Long res = getLongOrNull(PhotosColumns.TASK_ID);
        if (res == null)
            throw new NullPointerException("The value of 'task_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Task Id
     */
    public int getTasksIdTask() {
        Integer res = getIntegerOrNull(TasksColumns.ID_TASK);
        if (res == null)
            throw new NullPointerException("The value of 'id_task' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Status
     * Can be {@code null}.
     */
    @Nullable
    public Integer getTasksStatus() {
        return getIntegerOrNull(TasksColumns.STATUS);
    }

    /**
     * Type
     * Can be {@code null}.
     */
    @Nullable
    public Integer getTasksType() {
        return getIntegerOrNull(TasksColumns.TYPE);
    }

    /**
     * Description
     * Can be {@code null}.
     */
    @Nullable
    public String getTasksDescription() {
        return getStringOrNull(TasksColumns.DESCRIPTION);
    }

    /**
     * Address
     * Can be {@code null}.
     */
    @Nullable
    public String getTasksAddress() {
        return getStringOrNull(TasksColumns.ADDRESS);
    }

    /**
     * Responsible
     * Can be {@code null}.
     */
    @Nullable
    public String getTasksResponsible() {
        return getStringOrNull(TasksColumns.RESPONSIBLE);
    }

    /**
     * Date Created
     * Can be {@code null}.
     */
    @Nullable
    public Long getTasksDateCreated() {
        return getLongOrNull(TasksColumns.DATE_CREATED);
    }

    /**
     * Date Registered
     * Can be {@code null}.
     */
    @Nullable
    public Long getTasksDateRegistered() {
        return getLongOrNull(TasksColumns.DATE_REGISTERED);
    }

    /**
     * Date Assigned
     * Can be {@code null}.
     */
    @Nullable
    public Long getTasksDateAssigned() {
        return getLongOrNull(TasksColumns.DATE_ASSIGNED);
    }

    /**
     * Longitude
     * Can be {@code null}.
     */
    @Nullable
    public Double getTasksLongitude() {
        return getDoubleOrNull(TasksColumns.LONGITUDE);
    }

    /**
     * Latitude
     * Can be {@code null}.
     */
    @Nullable
    public Double getTasksLatitude() {
        return getDoubleOrNull(TasksColumns.LATITUDE);
    }

    /**
     * Likes
     * Can be {@code null}.
     */
    @Nullable
    public Integer getTasksLikes() {
        return getIntegerOrNull(TasksColumns.LIKES);
    }
}
