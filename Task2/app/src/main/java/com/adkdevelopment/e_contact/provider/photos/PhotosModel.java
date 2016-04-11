package com.adkdevelopment.e_contact.provider.photos;

import android.support.annotation.NonNull;

import com.adkdevelopment.e_contact.provider.base.BaseModel;

/**
 * News
 */
public interface PhotosModel extends BaseModel {

    /**
     * URL
     * Cannot be {@code null}.
     */
    @NonNull
    String getUrl();

    /**
     * Foreign Key
     */
    long getTaskId();
}
