package com.adkdevelopment.e_contact.provider.photos;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adkdevelopment.e_contact.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code photos} table.
 */
public class PhotosContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PhotosColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable PhotosSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable PhotosSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * URL
     */
    public PhotosContentValues putUrl(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("url must not be null");
        mContentValues.put(PhotosColumns.URL, value);
        return this;
    }


    /**
     * Foreign Key
     */
    public PhotosContentValues putTaskId(long value) {
        mContentValues.put(PhotosColumns.TASK_ID, value);
        return this;
    }

}
