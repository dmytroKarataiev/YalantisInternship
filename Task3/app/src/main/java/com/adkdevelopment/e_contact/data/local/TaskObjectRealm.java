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

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by karataev on 5/11/16.
 */
public class TaskObjectRealm extends RealmObject implements Parcelable {

    public static final String TASK_EXTRA = "task_realm_extra";
    public static final String TASK_EXTRA_TITLE = "task_title";

    public static final String STATE = "status";
    public static final int STATE_PROGRESS = 0;
    public static final int STATE_DONE = 1;
    public static final int STATE_PENDING = 2;

    // database schema
    public static final int WHERE_MODERATION = 0;
    public static final int WHERE_STILL_MODERATION = 1;
    public static final int WHERE_REJECTED = 2;
    public static final int WHERE_ACCEPTED = 3;
    public static final int WHERE_REVIEW = 4;
    public static final int WHERE_PROGRESS = 5;
    public static final int WHERE_DONE = 6;
    public static final int WHERE_UNKNOWN_7 = 7;
    public static final int WHERE_UNKNOWN_8 = 8;
    public static final int WHERE_UNKNOWN_9 = 9;
    public static final int WHERE_UNKNOWN_10 = 10;

    public static final int CATEGORY_AGRICULTURE = 1;
    public static final int CATEGORY_LAND = 102;
    public static final int CATEGORY_CORRUPTION = 124;
    public static final int CATEGORY_GAMBLING = 6;
    public static final int CATEGORY_NO_ANSWER = 5;
    public static final int CATEGORY_DEPOSITS = 7;
    public static final int CATEGORY_VERKHOVNA_RADA = 118;
    public static final int CATEGORY_RESIDENTS = 12;
    public static final int CATEGORY_WAGES = 4;
    public static final int CATEGORY_ARCHIVES = 2;
    public static final int CATEGORY_IMPROVEMENTS = 3;
    public static final int CATEGORY_HOTLINE = 40;
    public static final int CATEGORY_OTHER = 125;
    public static final int CATEGORY_GOV_BUILDING = 123;
    public static final int CATEGORY_MEDIA = 8;
    public static final int CATEGORY_MEDIA_POL = 116;
    public static final int CATEGORY_TOURISM = 44;
    public static final int CATEGORY_FINANCE = 105;
    public static final int CATEGORY_GOV_LOCAL = 120;
    public static final int CATEGORY_COMPENSATION = 19;
    public static final int CATEGORY_CRIME = 18;
    public static final int CATEGORY_GOV_INQUIRY = 15;
    public static final int CATEGORY_CONSUMERS = 14;
    public static final int CATEGORY_LABOR = 13;
    public static final int CATEGORY_LAW = 112;
    public static final int CATEGORY_EXECUTIVE = 119;
    public static final int CATEGORY_ECONOMY = 104;

    public static final int QUERY_ALL = -1;
    public static final int QUERY_FIRST_PAGE = 0;
    public static final int QUERY_AMOUNT = 20;
    public static final int QUERY_OFFSET = 20;
    public static final int QUERY_START = 8;

    public static final String QUERY_PROGRESS = "0,9,5,7,8";
    public static final String QUERY_DONE = "10,6";
    public static final String QUERY_PENDING = "1,3,4";

    @PrimaryKey private int id;
    private RealmList<TaskPhotoRealm> photo;
    private String title;
    private int type;
    private int status;
    private long registered;
    private long assigned;
    private String responsible;
    private String description;
    private int likes;
    private String address;
    private double latitude;
    private double longitude;
    private String statusText;
    private long created;
    private String typeText;
    private String categoryText;
    private int category;

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<TaskPhotoRealm> getPhoto() {
        return photo;
    }

    public void setPhoto(RealmList<TaskPhotoRealm> photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getRegistered() {
        return registered;
    }

    public void setRegistered(long registered) {
        this.registered = registered;
    }

    public long getAssigned() {
        return assigned;
    }

    public void setAssigned(long assigned) {
        this.assigned = assigned;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeList(this.photo);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeInt(this.status);
        dest.writeLong(this.registered);
        dest.writeLong(this.assigned);
        dest.writeString(this.responsible);
        dest.writeString(this.description);
        dest.writeInt(this.likes);
        dest.writeString(this.address);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.statusText);
        dest.writeLong(this.created);
        dest.writeString(this.typeText);
        dest.writeString(this.categoryText);
        dest.writeInt(this.category);
    }

    public TaskObjectRealm() {
    }

    protected TaskObjectRealm(Parcel in) {
        this.id = in.readInt();
        this.photo = new RealmList<>();
        in.readList(this.photo, TaskPhotoRealm.class.getClassLoader());
        this.title = in.readString();
        this.type = in.readInt();
        this.status = in.readInt();
        this.registered = in.readLong();
        this.assigned = in.readLong();
        this.responsible = in.readString();
        this.description = in.readString();
        this.likes = in.readInt();
        this.address = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.statusText = in.readString();
        this.created = in.readLong();
        this.typeText = in.readString();
        this.categoryText = in.readString();
        this.category = in.readInt();
    }

    public static final Parcelable.Creator<TaskObjectRealm> CREATOR = new Parcelable.Creator<TaskObjectRealm>() {
        @Override
        public TaskObjectRealm createFromParcel(Parcel source) {
            return new TaskObjectRealm(source);
        }

        @Override
        public TaskObjectRealm[] newArray(int size) {
            return new TaskObjectRealm[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        return o instanceof TaskObjectRealm && id == ((TaskObjectRealm) o).getId();
    }
}
