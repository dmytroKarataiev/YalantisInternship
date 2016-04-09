package com.adkdevelopment.e_contact.provider.tasks;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.adkdevelopment.e_contact.provider.base.AbstractSelection;

/**
 * Selection for the {@code tasks} table.
 */
public class TasksSelection extends AbstractSelection<TasksSelection> {
    @Override
    protected Uri baseUri() {
        return TasksColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TasksCursor} object, which is positioned before the first entry, or null.
     */
    public TasksCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TasksCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public TasksCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TasksCursor} object, which is positioned before the first entry, or null.
     */
    public TasksCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TasksCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public TasksCursor query(Context context) {
        return query(context, null);
    }


    public TasksSelection id(long... value) {
        addEquals("tasks." + TasksColumns._ID, toObjectArray(value));
        return this;
    }

    public TasksSelection idNot(long... value) {
        addNotEquals("tasks." + TasksColumns._ID, toObjectArray(value));
        return this;
    }

    public TasksSelection orderById(boolean desc) {
        orderBy("tasks." + TasksColumns._ID, desc);
        return this;
    }

    public TasksSelection orderById() {
        return orderById(false);
    }

    public TasksSelection idTask(int... value) {
        addEquals(TasksColumns.ID_TASK, toObjectArray(value));
        return this;
    }

    public TasksSelection idTaskNot(int... value) {
        addNotEquals(TasksColumns.ID_TASK, toObjectArray(value));
        return this;
    }

    public TasksSelection idTaskGt(int value) {
        addGreaterThan(TasksColumns.ID_TASK, value);
        return this;
    }

    public TasksSelection idTaskGtEq(int value) {
        addGreaterThanOrEquals(TasksColumns.ID_TASK, value);
        return this;
    }

    public TasksSelection idTaskLt(int value) {
        addLessThan(TasksColumns.ID_TASK, value);
        return this;
    }

    public TasksSelection idTaskLtEq(int value) {
        addLessThanOrEquals(TasksColumns.ID_TASK, value);
        return this;
    }

    public TasksSelection orderByIdTask(boolean desc) {
        orderBy(TasksColumns.ID_TASK, desc);
        return this;
    }

    public TasksSelection orderByIdTask() {
        orderBy(TasksColumns.ID_TASK, false);
        return this;
    }

    public TasksSelection status(Integer... value) {
        addEquals(TasksColumns.STATUS, value);
        return this;
    }

    public TasksSelection statusNot(Integer... value) {
        addNotEquals(TasksColumns.STATUS, value);
        return this;
    }

    public TasksSelection statusGt(int value) {
        addGreaterThan(TasksColumns.STATUS, value);
        return this;
    }

    public TasksSelection statusGtEq(int value) {
        addGreaterThanOrEquals(TasksColumns.STATUS, value);
        return this;
    }

    public TasksSelection statusLt(int value) {
        addLessThan(TasksColumns.STATUS, value);
        return this;
    }

    public TasksSelection statusLtEq(int value) {
        addLessThanOrEquals(TasksColumns.STATUS, value);
        return this;
    }

    public TasksSelection orderByStatus(boolean desc) {
        orderBy(TasksColumns.STATUS, desc);
        return this;
    }

    public TasksSelection orderByStatus() {
        orderBy(TasksColumns.STATUS, false);
        return this;
    }

    public TasksSelection type(Integer... value) {
        addEquals(TasksColumns.TYPE, value);
        return this;
    }

    public TasksSelection typeNot(Integer... value) {
        addNotEquals(TasksColumns.TYPE, value);
        return this;
    }

    public TasksSelection typeGt(int value) {
        addGreaterThan(TasksColumns.TYPE, value);
        return this;
    }

    public TasksSelection typeGtEq(int value) {
        addGreaterThanOrEquals(TasksColumns.TYPE, value);
        return this;
    }

    public TasksSelection typeLt(int value) {
        addLessThan(TasksColumns.TYPE, value);
        return this;
    }

    public TasksSelection typeLtEq(int value) {
        addLessThanOrEquals(TasksColumns.TYPE, value);
        return this;
    }

    public TasksSelection orderByType(boolean desc) {
        orderBy(TasksColumns.TYPE, desc);
        return this;
    }

    public TasksSelection orderByType() {
        orderBy(TasksColumns.TYPE, false);
        return this;
    }

    public TasksSelection description(String... value) {
        addEquals(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public TasksSelection descriptionNot(String... value) {
        addNotEquals(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public TasksSelection descriptionLike(String... value) {
        addLike(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public TasksSelection descriptionContains(String... value) {
        addContains(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public TasksSelection descriptionStartsWith(String... value) {
        addStartsWith(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public TasksSelection descriptionEndsWith(String... value) {
        addEndsWith(TasksColumns.DESCRIPTION, value);
        return this;
    }

    public TasksSelection orderByDescription(boolean desc) {
        orderBy(TasksColumns.DESCRIPTION, desc);
        return this;
    }

    public TasksSelection orderByDescription() {
        orderBy(TasksColumns.DESCRIPTION, false);
        return this;
    }

    public TasksSelection address(String... value) {
        addEquals(TasksColumns.ADDRESS, value);
        return this;
    }

    public TasksSelection addressNot(String... value) {
        addNotEquals(TasksColumns.ADDRESS, value);
        return this;
    }

    public TasksSelection addressLike(String... value) {
        addLike(TasksColumns.ADDRESS, value);
        return this;
    }

    public TasksSelection addressContains(String... value) {
        addContains(TasksColumns.ADDRESS, value);
        return this;
    }

    public TasksSelection addressStartsWith(String... value) {
        addStartsWith(TasksColumns.ADDRESS, value);
        return this;
    }

    public TasksSelection addressEndsWith(String... value) {
        addEndsWith(TasksColumns.ADDRESS, value);
        return this;
    }

    public TasksSelection orderByAddress(boolean desc) {
        orderBy(TasksColumns.ADDRESS, desc);
        return this;
    }

    public TasksSelection orderByAddress() {
        orderBy(TasksColumns.ADDRESS, false);
        return this;
    }

    public TasksSelection responsible(String... value) {
        addEquals(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public TasksSelection responsibleNot(String... value) {
        addNotEquals(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public TasksSelection responsibleLike(String... value) {
        addLike(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public TasksSelection responsibleContains(String... value) {
        addContains(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public TasksSelection responsibleStartsWith(String... value) {
        addStartsWith(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public TasksSelection responsibleEndsWith(String... value) {
        addEndsWith(TasksColumns.RESPONSIBLE, value);
        return this;
    }

    public TasksSelection orderByResponsible(boolean desc) {
        orderBy(TasksColumns.RESPONSIBLE, desc);
        return this;
    }

    public TasksSelection orderByResponsible() {
        orderBy(TasksColumns.RESPONSIBLE, false);
        return this;
    }

    public TasksSelection dateCreated(Long... value) {
        addEquals(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public TasksSelection dateCreatedNot(Long... value) {
        addNotEquals(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public TasksSelection dateCreatedGt(long value) {
        addGreaterThan(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public TasksSelection dateCreatedGtEq(long value) {
        addGreaterThanOrEquals(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public TasksSelection dateCreatedLt(long value) {
        addLessThan(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public TasksSelection dateCreatedLtEq(long value) {
        addLessThanOrEquals(TasksColumns.DATE_CREATED, value);
        return this;
    }

    public TasksSelection orderByDateCreated(boolean desc) {
        orderBy(TasksColumns.DATE_CREATED, desc);
        return this;
    }

    public TasksSelection orderByDateCreated() {
        orderBy(TasksColumns.DATE_CREATED, false);
        return this;
    }

    public TasksSelection dateRegistered(Long... value) {
        addEquals(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public TasksSelection dateRegisteredNot(Long... value) {
        addNotEquals(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public TasksSelection dateRegisteredGt(long value) {
        addGreaterThan(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public TasksSelection dateRegisteredGtEq(long value) {
        addGreaterThanOrEquals(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public TasksSelection dateRegisteredLt(long value) {
        addLessThan(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public TasksSelection dateRegisteredLtEq(long value) {
        addLessThanOrEquals(TasksColumns.DATE_REGISTERED, value);
        return this;
    }

    public TasksSelection orderByDateRegistered(boolean desc) {
        orderBy(TasksColumns.DATE_REGISTERED, desc);
        return this;
    }

    public TasksSelection orderByDateRegistered() {
        orderBy(TasksColumns.DATE_REGISTERED, false);
        return this;
    }

    public TasksSelection dateAssigned(Long... value) {
        addEquals(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public TasksSelection dateAssignedNot(Long... value) {
        addNotEquals(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public TasksSelection dateAssignedGt(long value) {
        addGreaterThan(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public TasksSelection dateAssignedGtEq(long value) {
        addGreaterThanOrEquals(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public TasksSelection dateAssignedLt(long value) {
        addLessThan(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public TasksSelection dateAssignedLtEq(long value) {
        addLessThanOrEquals(TasksColumns.DATE_ASSIGNED, value);
        return this;
    }

    public TasksSelection orderByDateAssigned(boolean desc) {
        orderBy(TasksColumns.DATE_ASSIGNED, desc);
        return this;
    }

    public TasksSelection orderByDateAssigned() {
        orderBy(TasksColumns.DATE_ASSIGNED, false);
        return this;
    }

    public TasksSelection longitude(Double... value) {
        addEquals(TasksColumns.LONGITUDE, value);
        return this;
    }

    public TasksSelection longitudeNot(Double... value) {
        addNotEquals(TasksColumns.LONGITUDE, value);
        return this;
    }

    public TasksSelection longitudeGt(double value) {
        addGreaterThan(TasksColumns.LONGITUDE, value);
        return this;
    }

    public TasksSelection longitudeGtEq(double value) {
        addGreaterThanOrEquals(TasksColumns.LONGITUDE, value);
        return this;
    }

    public TasksSelection longitudeLt(double value) {
        addLessThan(TasksColumns.LONGITUDE, value);
        return this;
    }

    public TasksSelection longitudeLtEq(double value) {
        addLessThanOrEquals(TasksColumns.LONGITUDE, value);
        return this;
    }

    public TasksSelection orderByLongitude(boolean desc) {
        orderBy(TasksColumns.LONGITUDE, desc);
        return this;
    }

    public TasksSelection orderByLongitude() {
        orderBy(TasksColumns.LONGITUDE, false);
        return this;
    }

    public TasksSelection likes(Integer... value) {
        addEquals(TasksColumns.LIKES, value);
        return this;
    }

    public TasksSelection likesNot(Integer... value) {
        addNotEquals(TasksColumns.LIKES, value);
        return this;
    }

    public TasksSelection likesGt(int value) {
        addGreaterThan(TasksColumns.LIKES, value);
        return this;
    }

    public TasksSelection likesGtEq(int value) {
        addGreaterThanOrEquals(TasksColumns.LIKES, value);
        return this;
    }

    public TasksSelection likesLt(int value) {
        addLessThan(TasksColumns.LIKES, value);
        return this;
    }

    public TasksSelection likesLtEq(int value) {
        addLessThanOrEquals(TasksColumns.LIKES, value);
        return this;
    }

    public TasksSelection orderByLikes(boolean desc) {
        orderBy(TasksColumns.LIKES, desc);
        return this;
    }

    public TasksSelection orderByLikes() {
        orderBy(TasksColumns.LIKES, false);
        return this;
    }
}
