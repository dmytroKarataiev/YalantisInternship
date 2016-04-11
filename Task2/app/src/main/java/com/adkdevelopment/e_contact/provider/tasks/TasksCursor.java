package com.adkdevelopment.e_contact.provider.tasks;

import android.database.Cursor;
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
        return getIntegerOrNull(TasksColumns.STATUS);
    }

    /**
     * Type
     * Can be {@code null}.
     */
    @Nullable
    public Integer getType() {
        return getIntegerOrNull(TasksColumns.TYPE);
    }

    /**
     * Description
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        return getStringOrNull(TasksColumns.DESCRIPTION);
    }

    /**
     * Address
     * Can be {@code null}.
     */
    @Nullable
    public String getAddress() {
        return getStringOrNull(TasksColumns.ADDRESS);
    }

    /**
     * Responsible
     * Can be {@code null}.
     */
    @Nullable
    public String getResponsible() {
        return getStringOrNull(TasksColumns.RESPONSIBLE);
    }

    /**
     * Date Created
     * Can be {@code null}.
     */
    @Nullable
    public Long getDateCreated() {
        return getLongOrNull(TasksColumns.DATE_CREATED);
    }

    /**
     * Date Registered
     * Can be {@code null}.
     */
    @Nullable
    public Long getDateRegistered() {
        return getLongOrNull(TasksColumns.DATE_REGISTERED);
    }

    /**
     * Date Assigned
     * Can be {@code null}.
     */
    @Nullable
    public Long getDateAssigned() {
        return getLongOrNull(TasksColumns.DATE_ASSIGNED);
    }

    /**
     * Longitude
     * Can be {@code null}.
     */
    @Nullable
    public Double getLongitude() {
        return getDoubleOrNull(TasksColumns.LONGITUDE);
    }

    /**
     * Latitude
     * Can be {@code null}.
     */
    @Nullable
    public Double getLatitude() {
        return getDoubleOrNull(TasksColumns.LATITUDE);
    }

    /**
     * Likes
     * Can be {@code null}.
     */
    @Nullable
    public Integer getLikes() {
        return getIntegerOrNull(TasksColumns.LIKES);
    }
}
