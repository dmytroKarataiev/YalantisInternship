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

package com.adkdevelopment.e_contact.remote;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TaskItem implements Parcelable {

    public static final String TASKITEM = "task";

    // constants set according to the database schema
    public static final String STATUS_ALL = "0";
    public static final String STATUS_PROGRESS = "1";
    public static final String STATUS_COMPLETED = "2";
    public static final String STATUS_WAITING = "3";

    public static final int TYPE_ALL = 0;
    public static final int TYPE_PUBLIC = 1;
    public static final int TYPE_IMPROVEMENT = 2;
    public static final int TYPE_ARCHITECTURE = 3;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("photo")
    @Expose
    private List<String> photo = new ArrayList<>();
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("created")
    @Expose
    private long created;
    @SerializedName("registered")
    @Expose
    private long registered;
    @SerializedName("assigned")
    @Expose
    private long assigned;
    @SerializedName("responsible")
    @Expose
    private String responsible;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("likes")
    @Expose
    private int likes;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;

    // unique database id to retrieve photos for the object
    private int databaseId;

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The photo
     */
    public List<String> getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     * The photo
     */
    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The type
     */
    public int getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The created
     */
    public long getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The created
     */
    public void setCreated(long created) {
        this.created = created;
    }

    /**
     *
     * @return
     * The registered
     */
    public long getRegistered() {
        return registered;
    }

    /**
     *
     * @param registered
     * The registered
     */
    public void setRegistered(long registered) {
        this.registered = registered;
    }

    /**
     *
     * @return
     * The assigned
     */
    public Long getAssigned() {
        return assigned;
    }

    /**
     *
     * @param assigned
     * The assigned
     */
    public void setAssigned(Long assigned) {
        this.assigned = assigned;
    }

    /**
     *
     * @return
     * The responsible
     */
    public String getResponsible() {
        return responsible;
    }

    /**
     *
     * @param responsible
     * The responsible
     */
    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The likes
     */
    public int getLikes() {
        return likes;
    }

    /**
     *
     * @param likes
     * The likes
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public TaskItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeStringList(this.photo);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeInt(this.status);
        dest.writeLong(this.created);
        dest.writeLong(this.registered);
        dest.writeLong(this.assigned);
        dest.writeString(this.responsible);
        dest.writeString(this.description);
        dest.writeInt(this.likes);
        dest.writeString(this.address);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeInt(this.databaseId);
    }

    protected TaskItem(Parcel in) {
        this.id = in.readString();
        this.photo = in.createStringArrayList();
        this.title = in.readString();
        this.type = in.readInt();
        this.status = in.readInt();
        this.created = in.readLong();
        this.registered = in.readLong();
        this.assigned = in.readLong();
        this.responsible = in.readString();
        this.description = in.readString();
        this.likes = in.readInt();
        this.address = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.databaseId = in.readInt();
    }

    public static final Creator<TaskItem> CREATOR = new Creator<TaskItem>() {
        @Override
        public TaskItem createFromParcel(Parcel source) {
            return new TaskItem(source);
        }

        @Override
        public TaskItem[] newArray(int size) {
            return new TaskItem[size];
        }
    };
}