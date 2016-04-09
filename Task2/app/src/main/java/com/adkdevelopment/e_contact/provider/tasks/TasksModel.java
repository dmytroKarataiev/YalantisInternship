package com.adkdevelopment.e_contact.provider.tasks;

import com.adkdevelopment.e_contact.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A task object
 */
public interface TasksModel extends BaseModel {

    /**
     * Task Id
     */
    int getIdTask();

    /**
     * Status
     * Can be {@code null}.
     */
    @Nullable
    Integer getStatus();

    /**
     * Type
     * Can be {@code null}.
     */
    @Nullable
    Integer getType();

    /**
     * Description
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();

    /**
     * Address
     * Can be {@code null}.
     */
    @Nullable
    String getAddress();

    /**
     * Responsible
     * Can be {@code null}.
     */
    @Nullable
    String getResponsible();

    /**
     * Date Created
     * Can be {@code null}.
     */
    @Nullable
    Long getDateCreated();

    /**
     * Date Registered
     * Can be {@code null}.
     */
    @Nullable
    Long getDateRegistered();

    /**
     * Date Assigned
     * Can be {@code null}.
     */
    @Nullable
    Long getDateAssigned();

    /**
     * Longitude
     * Can be {@code null}.
     */
    @Nullable
    Double getLongitude();

    /**
     * Likes
     * Can be {@code null}.
     */
    @Nullable
    Integer getLikes();
}
