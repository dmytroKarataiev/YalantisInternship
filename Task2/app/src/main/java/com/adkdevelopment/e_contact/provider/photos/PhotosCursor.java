package com.adkdevelopment.e_contact.provider.photos;

import java.util.Date;

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
        Integer res = getIntegerOrNull(TasksColumns.STATUS);
        return res;
    }

    /**
     * Type
     * Can be {@code null}.
     */
    @Nullable
    public Integer getTasksType() {
        Integer res = getIntegerOrNull(TasksColumns.TYPE);
        return res;
    }

    /**
     * Description
     * Can be {@code null}.
     */
    @Nullable
    public String getTasksDescription() {
        String res = getStringOrNull(TasksColumns.DESCRIPTION);
        return res;
    }

    /**
     * Address
     * Can be {@code null}.
     */
    @Nullable
    public String getTasksAddress() {
        String res = getStringOrNull(TasksColumns.ADDRESS);
        return res;
    }

    /**
     * Responsible
     * Can be {@code null}.
     */
    @Nullable
    public String getTasksResponsible() {
        String res = getStringOrNull(TasksColumns.RESPONSIBLE);
        return res;
    }

    /**
     * Date Created
     * Can be {@code null}.
     */
    @Nullable
    public Long getTasksDateCreated() {
        Long res = getLongOrNull(TasksColumns.DATE_CREATED);
        return res;
    }

    /**
     * Date Registered
     * Can be {@code null}.
     */
    @Nullable
    public Long getTasksDateRegistered() {
        Long res = getLongOrNull(TasksColumns.DATE_REGISTERED);
        return res;
    }

    /**
     * Date Assigned
     * Can be {@code null}.
     */
    @Nullable
    public Long getTasksDateAssigned() {
        Long res = getLongOrNull(TasksColumns.DATE_ASSIGNED);
        return res;
    }

    /**
     * Longitude
     * Can be {@code null}.
     */
    @Nullable
    public Double getTasksLongitude() {
        Double res = getDoubleOrNull(TasksColumns.LONGITUDE);
        return res;
    }

    /**
     * Likes
     * Can be {@code null}.
     */
    @Nullable
    public Integer getTasksLikes() {
        Integer res = getIntegerOrNull(TasksColumns.LIKES);
        return res;
    }
}
