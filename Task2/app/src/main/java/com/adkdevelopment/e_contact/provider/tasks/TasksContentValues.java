package com.adkdevelopment.e_contact.provider.tasks;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adkdevelopment.e_contact.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code tasks} table.
 */
public class TasksContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TasksColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable TasksSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable TasksSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Task Id
     */
    public TasksContentValues putIdTask(int value) {
        mContentValues.put(TasksColumns.ID_TASK, value);
        return this;
    }


    /**
     * Status
     */
    public TasksContentValues putStatus(@Nullable Integer value) {
        mContentValues.put(TasksColumns.STATUS, value);
        return this;
    }

    public TasksContentValues putStatusNull() {
        mContentValues.putNull(TasksColumns.STATUS);
        return this;
    }

    /**
     * Type
     */
    public TasksContentValues putType(@Nullable Integer value) {
        mContentValues.put(TasksColumns.TYPE, value);
        return this;
    }

    public TasksContentValues putTypeNull() {
        mContentValues.putNull(TasksColumns.TYPE);
        return this;
    }

    /**
     * Description
     */
    public TasksContentValues putDescription(@Nullable String value) {
        mContentValues.put(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public TasksContentValues putDescriptionNull() {
        mContentValues.putNull(TasksColumns.DESCRIPTION);
        return this;
    }

    /**
     * Address
     */
    public TasksContentValues putAddress(@Nullable String value) {
        mContentValues.put(TasksColumns.ADDRESS, value);
        return this;
    }

    public TasksContentValues putAddressNull() {
        mContentValues.putNull(TasksColumns.ADDRESS);
        return this;
    }

    /**
     * Responsible
     */
    public TasksContentValues putResponsible(@Nullable String value) {
        mContentValues.put(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public TasksContentValues putResponsibleNull() {
        mContentValues.putNull(TasksColumns.RESPONSIBLE);
        return this;
    }

    /**
     * Date Created
     */
    public TasksContentValues putDateCreated(@Nullable Long value) {
        mContentValues.put(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public TasksContentValues putDateCreatedNull() {
        mContentValues.putNull(TasksColumns.DATE_CREATED);
        return this;
    }

    /**
     * Date Registered
     */
    public TasksContentValues putDateRegistered(@Nullable Long value) {
        mContentValues.put(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public TasksContentValues putDateRegisteredNull() {
        mContentValues.putNull(TasksColumns.DATE_REGISTERED);
        return this;
    }

    /**
     * Date Assigned
     */
    public TasksContentValues putDateAssigned(@Nullable Long value) {
        mContentValues.put(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public TasksContentValues putDateAssignedNull() {
        mContentValues.putNull(TasksColumns.DATE_ASSIGNED);
        return this;
    }

    /**
     * Longitude
     */
    public TasksContentValues putLongitude(@Nullable Double value) {
        mContentValues.put(TasksColumns.LONGITUDE, value);
        return this;
    }

    public TasksContentValues putLongitudeNull() {
        mContentValues.putNull(TasksColumns.LONGITUDE);
        return this;
    }

    /**
     * Likes
     */
    public TasksContentValues putLikes(@Nullable Integer value) {
        mContentValues.put(TasksColumns.LIKES, value);
        return this;
    }

    public TasksContentValues putLikesNull() {
        mContentValues.putNull(TasksColumns.LIKES);
        return this;
    }
}
