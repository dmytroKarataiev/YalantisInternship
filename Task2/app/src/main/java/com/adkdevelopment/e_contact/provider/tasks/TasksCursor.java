package com.adkdevelopment.e_contact.provider.tasks;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adkdevelopment.e_contact.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code tasks} table.
 */
public class TasksCursor extends AbstractCursor implements TasksModel {
    public TasksCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(TasksColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Task Id
     */
    public int getIdTask() {
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
    public Integer getStatus() {
        Integer res = getIntegerOrNull(TasksColumns.STATUS);
        return res;
    }

    /**
     * Type
     * Can be {@code null}.
     */
    @Nullable
    public Integer getType() {
        Integer res = getIntegerOrNull(TasksColumns.TYPE);
        return res;
    }

    /**
     * Description
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(TasksColumns.DESCRIPTION);
        return res;
    }

    /**
     * Address
     * Can be {@code null}.
     */
    @Nullable
    public String getAddress() {
        String res = getStringOrNull(TasksColumns.ADDRESS);
        return res;
    }

    /**
     * Responsible
     * Can be {@code null}.
     */
    @Nullable
    public String getResponsible() {
        String res = getStringOrNull(TasksColumns.RESPONSIBLE);
        return res;
    }

    /**
     * Date Created
     * Can be {@code null}.
     */
    @Nullable
    public Long getDateCreated() {
        Long res = getLongOrNull(TasksColumns.DATE_CREATED);
        return res;
    }

    /**
     * Date Registered
     * Can be {@code null}.
     */
    @Nullable
    public Long getDateRegistered() {
        Long res = getLongOrNull(TasksColumns.DATE_REGISTERED);
        return res;
    }

    /**
     * Date Assigned
     * Can be {@code null}.
     */
    @Nullable
    public Long getDateAssigned() {
        Long res = getLongOrNull(TasksColumns.DATE_ASSIGNED);
        return res;
    }

    /**
     * Longitude
     * Can be {@code null}.
     */
    @Nullable
    public Double getLongitude() {
        Double res = getDoubleOrNull(TasksColumns.LONGITUDE);
        return res;
    }

    /**
     * Likes
     * Can be {@code null}.
     */
    @Nullable
    public Integer getLikes() {
        Integer res = getIntegerOrNull(TasksColumns.LIKES);
        return res;
    }
}
