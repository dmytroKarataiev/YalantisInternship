/*
 * MIT License
 *
 * Copyright (c) 2016. Dmytro Karataiev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.adkdevelopment.e_contact.data.local;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Class to store photos from the API
 * Created by karataev on 5/11/16.
 */
public class PhotoRealm extends RealmObject implements Parcelable {

    public static final int WIDTH_SCALE = 800;
    public static final int HEIGHT_SCALE = 600;

    private static final String PHOTO_URL = "http://dev-contact.yalantis.com/files/ticket/";

    public String getImageUrl() {
        return PHOTO_URL + imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
    }

    public PhotoRealm() {
    }

    protected PhotoRealm(Parcel in) {
        this.imageUrl = in.readString();
    }

    public static final Parcelable.Creator<PhotoRealm> CREATOR = new Parcelable.Creator<PhotoRealm>() {
        @Override
        public PhotoRealm createFromParcel(Parcel source) {
            return new PhotoRealm(source);
        }

        @Override
        public PhotoRealm[] newArray(int size) {
            return new PhotoRealm[size];
        }
    };
}
