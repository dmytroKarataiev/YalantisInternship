package com.adkdevelopment.e_contact.provider.photos;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.adkdevelopment.e_contact.provider.base.AbstractSelection;
import com.adkdevelopment.e_contact.provider.tasks.*;

/**
 * Selection for the {@code photos} table.
 */
public class PhotosSelection extends AbstractSelection<PhotosSelection> {
    @Override
    protected Uri baseUri() {
        return PhotosColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PhotosCursor} object, which is positioned before the first entry, or null.
     */
    public PhotosCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PhotosCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public PhotosCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PhotosCursor} object, which is positioned before the first entry, or null.
     */
    public PhotosCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PhotosCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public PhotosCursor query(Context context) {
        return query(context, null);
    }


    public PhotosSelection id(long... value) {
        addEquals("photos." + PhotosColumns._ID, toObjectArray(value));
        return this;
    }

    public PhotosSelection idNot(long... value) {
        addNotEquals("photos." + PhotosColumns._ID, toObjectArray(value));
        return this;
    }

    public PhotosSelection orderById(boolean desc) {
        orderBy("photos." + PhotosColumns._ID, desc);
        return this;
    }

    public PhotosSelection orderById() {
        return orderById(false);
    }

    public PhotosSelection url(String... value) {
        addEquals(PhotosColumns.URL, value);
        return this;
    }

    public PhotosSelection urlNot(String... value) {
        addNotEquals(PhotosColumns.URL, value);
        return this;
    }

    public PhotosSelection urlLike(String... value) {
        addLike(PhotosColumns.URL, value);
        return this;
    }

    public PhotosSelection urlContains(String... value) {
        addContains(PhotosColumns.URL, value);
        return this;
    }

    public PhotosSelection urlStartsWith(String... value) {
        addStartsWith(PhotosColumns.URL, value);
        return this;
    }

    public PhotosSelection urlEndsWith(String... value) {
        addEndsWith(PhotosColumns.URL, value);
        return this;
    }

    public PhotosSelection orderByUrl(boolean desc) {
        orderBy(PhotosColumns.URL, desc);
        return this;
    }

    public PhotosSelection orderByUrl() {
        orderBy(PhotosColumns.URL, false);
        return this;
    }

    public PhotosSelection taskId(long... value) {
        addEquals(PhotosColumns.TASK_ID, toObjectArray(value));
        return this;
    }

    public PhotosSelection taskIdNot(long... value) {
        addNotEquals(PhotosColumns.TASK_ID, toObjectArray(value));
        return this;
    }

    public PhotosSelection taskIdGt(long value) {
        addGreaterThan(PhotosColumns.TASK_ID, value);
        return this;
    }

    public PhotosSelection taskIdGtEq(long value) {
        addGreaterThanOrEquals(PhotosColumns.TASK_ID, value);
        return this;
    }

    public PhotosSelection taskIdLt(long value) {
        addLessThan(PhotosColumns.TASK_ID, value);
        return this;
    }

    public PhotosSelection taskIdLtEq(long value) {
        addLessThanOrEquals(PhotosColumns.TASK_ID, value);
        return this;
    }

    public PhotosSelection orderByTaskId(boolean desc) {
        orderBy(PhotosColumns.TASK_ID, desc);
        return this;
    }

    public PhotosSelection orderByTaskId() {
        orderBy(PhotosColumns.TASK_ID, false);
        return this;
    }

    public PhotosSelection tasksIdTask(int... value) {
        addEquals(TasksColumns.ID_TASK, toObjectArray(value));
        return this;
    }

    public PhotosSelection tasksIdTaskNot(int... value) {
        addNotEquals(TasksColumns.ID_TASK, toObjectArray(value));
        return this;
    }

    public PhotosSelection tasksIdTaskGt(int value) {
        addGreaterThan(TasksColumns.ID_TASK, value);
        return this;
    }

    public PhotosSelection tasksIdTaskGtEq(int value) {
        addGreaterThanOrEquals(TasksColumns.ID_TASK, value);
        return this;
    }

    public PhotosSelection tasksIdTaskLt(int value) {
        addLessThan(TasksColumns.ID_TASK, value);
        return this;
    }

    public PhotosSelection tasksIdTaskLtEq(int value) {
        addLessThanOrEquals(TasksColumns.ID_TASK, value);
        return this;
    }

    public PhotosSelection orderByTasksIdTask(boolean desc) {
        orderBy(TasksColumns.ID_TASK, desc);
        return this;
    }

    public PhotosSelection orderByTasksIdTask() {
        orderBy(TasksColumns.ID_TASK, false);
        return this;
    }

    public PhotosSelection tasksStatus(Integer... value) {
        addEquals(TasksColumns.STATUS, value);
        return this;
    }

    public PhotosSelection tasksStatusNot(Integer... value) {
        addNotEquals(TasksColumns.STATUS, value);
        return this;
    }

    public PhotosSelection tasksStatusGt(int value) {
        addGreaterThan(TasksColumns.STATUS, value);
        return this;
    }

    public PhotosSelection tasksStatusGtEq(int value) {
        addGreaterThanOrEquals(TasksColumns.STATUS, value);
        return this;
    }

    public PhotosSelection tasksStatusLt(int value) {
        addLessThan(TasksColumns.STATUS, value);
        return this;
    }

    public PhotosSelection tasksStatusLtEq(int value) {
        addLessThanOrEquals(TasksColumns.STATUS, value);
        return this;
    }

    public PhotosSelection orderByTasksStatus(boolean desc) {
        orderBy(TasksColumns.STATUS, desc);
        return this;
    }

    public PhotosSelection orderByTasksStatus() {
        orderBy(TasksColumns.STATUS, false);
        return this;
    }

    public PhotosSelection tasksType(Integer... value) {
        addEquals(TasksColumns.TYPE, value);
        return this;
    }

    public PhotosSelection tasksTypeNot(Integer... value) {
        addNotEquals(TasksColumns.TYPE, value);
        return this;
    }

    public PhotosSelection tasksTypeGt(int value) {
        addGreaterThan(TasksColumns.TYPE, value);
        return this;
    }

    public PhotosSelection tasksTypeGtEq(int value) {
        addGreaterThanOrEquals(TasksColumns.TYPE, value);
        return this;
    }

    public PhotosSelection tasksTypeLt(int value) {
        addLessThan(TasksColumns.TYPE, value);
        return this;
    }

    public PhotosSelection tasksTypeLtEq(int value) {
        addLessThanOrEquals(TasksColumns.TYPE, value);
        return this;
    }

    public PhotosSelection orderByTasksType(boolean desc) {
        orderBy(TasksColumns.TYPE, desc);
        return this;
    }

    public PhotosSelection orderByTasksType() {
        orderBy(TasksColumns.TYPE, false);
        return this;
    }

    public PhotosSelection tasksDescription(String... value) {
        addEquals(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public PhotosSelection tasksDescriptionNot(String... value) {
        addNotEquals(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public PhotosSelection tasksDescriptionLike(String... value) {
        addLike(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public PhotosSelection tasksDescriptionContains(String... value) {
        addContains(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public PhotosSelection tasksDescriptionStartsWith(String... value) {
        addStartsWith(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public PhotosSelection tasksDescriptionEndsWith(String... value) {
        addEndsWith(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public PhotosSelection orderByTasksDescription(boolean desc) {
        orderBy(TasksColumns.DESCRIPTION, desc);
        return this;
    }

    public PhotosSelection orderByTasksDescription() {
        orderBy(TasksColumns.DESCRIPTION, false);
        return this;
    }

    public PhotosSelection tasksAddress(String... value) {
        addEquals(TasksColumns.ADDRESS, value);
        return this;
    }

    public PhotosSelection tasksAddressNot(String... value) {
        addNotEquals(TasksColumns.ADDRESS, value);
        return this;
    }

    public PhotosSelection tasksAddressLike(String... value) {
        addLike(TasksColumns.ADDRESS, value);
        return this;
    }

    public PhotosSelection tasksAddressContains(String... value) {
        addContains(TasksColumns.ADDRESS, value);
        return this;
    }

    public PhotosSelection tasksAddressStartsWith(String... value) {
        addStartsWith(TasksColumns.ADDRESS, value);
        return this;
    }

    public PhotosSelection tasksAddressEndsWith(String... value) {
        addEndsWith(TasksColumns.ADDRESS, value);
        return this;
    }

    public PhotosSelection orderByTasksAddress(boolean desc) {
        orderBy(TasksColumns.ADDRESS, desc);
        return this;
    }

    public PhotosSelection orderByTasksAddress() {
        orderBy(TasksColumns.ADDRESS, false);
        return this;
    }

    public PhotosSelection tasksResponsible(String... value) {
        addEquals(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public PhotosSelection tasksResponsibleNot(String... value) {
        addNotEquals(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public PhotosSelection tasksResponsibleLike(String... value) {
        addLike(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public PhotosSelection tasksResponsibleContains(String... value) {
        addContains(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public PhotosSelection tasksResponsibleStartsWith(String... value) {
        addStartsWith(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public PhotosSelection tasksResponsibleEndsWith(String... value) {
        addEndsWith(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public PhotosSelection orderByTasksResponsible(boolean desc) {
        orderBy(TasksColumns.RESPONSIBLE, desc);
        return this;
    }

    public PhotosSelection orderByTasksResponsible() {
        orderBy(TasksColumns.RESPONSIBLE, false);
        return this;
    }

    public PhotosSelection tasksDateCreated(Long... value) {
        addEquals(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public PhotosSelection tasksDateCreatedNot(Long... value) {
        addNotEquals(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public PhotosSelection tasksDateCreatedGt(long value) {
        addGreaterThan(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public PhotosSelection tasksDateCreatedGtEq(long value) {
        addGreaterThanOrEquals(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public PhotosSelection tasksDateCreatedLt(long value) {
        addLessThan(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public PhotosSelection tasksDateCreatedLtEq(long value) {
        addLessThanOrEquals(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public PhotosSelection orderByTasksDateCreated(boolean desc) {
        orderBy(TasksColumns.DATE_CREATED, desc);
        return this;
    }

    public PhotosSelection orderByTasksDateCreated() {
        orderBy(TasksColumns.DATE_CREATED, false);
        return this;
    }

    public PhotosSelection tasksDateRegistered(Long... value) {
        addEquals(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public PhotosSelection tasksDateRegisteredNot(Long... value) {
        addNotEquals(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public PhotosSelection tasksDateRegisteredGt(long value) {
        addGreaterThan(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public PhotosSelection tasksDateRegisteredGtEq(long value) {
        addGreaterThanOrEquals(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public PhotosSelection tasksDateRegisteredLt(long value) {
        addLessThan(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public PhotosSelection tasksDateRegisteredLtEq(long value) {
        addLessThanOrEquals(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public PhotosSelection orderByTasksDateRegistered(boolean desc) {
        orderBy(TasksColumns.DATE_REGISTERED, desc);
        return this;
    }

    public PhotosSelection orderByTasksDateRegistered() {
        orderBy(TasksColumns.DATE_REGISTERED, false);
        return this;
    }

    public PhotosSelection tasksDateAssigned(Long... value) {
        addEquals(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public PhotosSelection tasksDateAssignedNot(Long... value) {
        addNotEquals(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public PhotosSelection tasksDateAssignedGt(long value) {
        addGreaterThan(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public PhotosSelection tasksDateAssignedGtEq(long value) {
        addGreaterThanOrEquals(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public PhotosSelection tasksDateAssignedLt(long value) {
        addLessThan(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public PhotosSelection tasksDateAssignedLtEq(long value) {
        addLessThanOrEquals(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public PhotosSelection orderByTasksDateAssigned(boolean desc) {
        orderBy(TasksColumns.DATE_ASSIGNED, desc);
        return this;
    }

    public PhotosSelection orderByTasksDateAssigned() {
        orderBy(TasksColumns.DATE_ASSIGNED, false);
        return this;
    }

    public PhotosSelection tasksLongitude(Double... value) {
        addEquals(TasksColumns.LONGITUDE, value);
        return this;
    }

    public PhotosSelection tasksLongitudeNot(Double... value) {
        addNotEquals(TasksColumns.LONGITUDE, value);
        return this;
    }

    public PhotosSelection tasksLongitudeGt(double value) {
        addGreaterThan(TasksColumns.LONGITUDE, value);
        return this;
    }

    public PhotosSelection tasksLongitudeGtEq(double value) {
        addGreaterThanOrEquals(TasksColumns.LONGITUDE, value);
        return this;
    }

    public PhotosSelection tasksLongitudeLt(double value) {
        addLessThan(TasksColumns.LONGITUDE, value);
        return this;
    }

    public PhotosSelection tasksLongitudeLtEq(double value) {
        addLessThanOrEquals(TasksColumns.LONGITUDE, value);
        return this;
    }

    public PhotosSelection orderByTasksLongitude(boolean desc) {
        orderBy(TasksColumns.LONGITUDE, desc);
        return this;
    }

    public PhotosSelection orderByTasksLongitude() {
        orderBy(TasksColumns.LONGITUDE, false);
        return this;
    }

    public PhotosSelection tasksLikes(Integer... value) {
        addEquals(TasksColumns.LIKES, value);
        return this;
    }

    public PhotosSelection tasksLikesNot(Integer... value) {
        addNotEquals(TasksColumns.LIKES, value);
        return this;
    }

    public PhotosSelection tasksLikesGt(int value) {
        addGreaterThan(TasksColumns.LIKES, value);
        return this;
    }

    public PhotosSelection tasksLikesGtEq(int value) {
        addGreaterThanOrEquals(TasksColumns.LIKES, value);
        return this;
    }

    public PhotosSelection tasksLikesLt(int value) {
        addLessThan(TasksColumns.LIKES, value);
        return this;
    }

    public PhotosSelection tasksLikesLtEq(int value) {
        addLessThanOrEquals(TasksColumns.LIKES, value);
        return this;
    }

    public PhotosSelection orderByTasksLikes(boolean desc) {
        orderBy(TasksColumns.LIKES, desc);
        return this;
    }

    public PhotosSelection orderByTasksLikes() {
        orderBy(TasksColumns.LIKES, false);
        return this;
    }
}
