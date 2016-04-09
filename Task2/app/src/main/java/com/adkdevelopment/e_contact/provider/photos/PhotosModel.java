package com.adkdevelopment.e_contact.provider.photos;

import com.adkdevelopment.e_contact.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
