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

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by karataev on 5/11/16.
 */
public class TaskObjectRealm extends RealmObject {

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

}
