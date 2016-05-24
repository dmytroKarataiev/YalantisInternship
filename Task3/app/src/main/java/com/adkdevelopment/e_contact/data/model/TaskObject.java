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

package com.adkdevelopment.e_contact.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TaskObject implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user")
    @Expose
    private TaskUser user;
    @SerializedName("address")
    @Expose
    private TaskAddress address;
    @SerializedName("geo_address")
    @Expose
    private TaskGeoAddress geoAddress;
    @SerializedName("category")
    @Expose
    private TaskCategory category;
    @SerializedName("type")
    @Expose
    private TaskType type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("created_date")
    @Expose
    private long createdDate;
    @SerializedName("start_date")
    @Expose
    private long startDate;
    @SerializedName("completed_date")
    @Expose
    private long completedDate;
    @SerializedName("state")
    @Expose
    private TaskState state;
    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("files")
    @Expose
    private List<TaskFile> files = new ArrayList<>();
    @SerializedName("performers")
    @Expose
    private List<TaskPerformer> performers = new ArrayList<>();
    @SerializedName("deadline")
    @Expose
    private long deadline;
    @SerializedName("likes_counter")
    @Expose
    private int likesCounter;

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The user
     */
    public TaskUser getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(TaskUser user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The address
     */
    public TaskAddress getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(TaskAddress address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The geoAddress
     */
    public TaskGeoAddress getGeoAddress() {
        return geoAddress;
    }

    /**
     *
     * @param geoAddress
     * The geo_address
     */
    public void setGeoAddress(TaskGeoAddress geoAddress) {
        this.geoAddress = geoAddress;
    }

    /**
     *
     * @return
     * The category
     */
    public TaskCategory getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(TaskCategory category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The type
     */
    public TaskType getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(TaskType type) {
        this.type = type;
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
     * The body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     *
     * @return
     * The createdDate
     */
    public long getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     * The created_date
     */
    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     * The startDate
     */
    public long getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     * The start_date
     */
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     * The completedDate
     */
    public long getCompletedDate() {
        return completedDate;
    }

    /**
     *
     * @param completedDate
     * The completed_date
     */
    public void setCompletedDate(long completedDate) {
        this.completedDate = completedDate;
    }

    /**
     *
     * @return
     * The state
     */
    public TaskState getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(TaskState state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The ticketId
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     *
     * @param ticketId
     * The ticket_id
     */
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /**
     *
     * @return
     * The files
     */
    public List<TaskFile> getFiles() {
        return files;
    }

    /**
     *
     * @param files
     * The files
     */
    public void setFiles(List<TaskFile> files) {
        this.files = files;
    }

    /**
     *
     * @return
     * The performers
     */
    public List<TaskPerformer> getPerformers() {
        return performers;
    }

    /**
     *
     * @param performers
     * The performers
     */
    public void setPerformers(List<TaskPerformer> performers) {
        this.performers = performers;
    }

    /**
     *
     * @return
     * The deadline
     */
    public long getDeadline() {
        return deadline;
    }

    /**
     *
     * @param deadline
     * The deadline
     */
    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    /**
     *
     * @return
     * The likesCounter
     */
    public int getLikesCounter() {
        return likesCounter;
    }

    /**
     *
     * @param likesCounter
     * The likes_counter
     */
    public void setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.address, flags);
        dest.writeParcelable(this.geoAddress, flags);
        dest.writeParcelable(this.category, flags);
        dest.writeParcelable(this.type, flags);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeLong(this.createdDate);
        dest.writeLong(this.startDate);
        dest.writeLong(this.completedDate);
        dest.writeParcelable(this.state, flags);
        dest.writeString(this.ticketId);
        dest.writeList(this.files);
        dest.writeList(this.performers);
        dest.writeLong(this.deadline);
        dest.writeInt(this.likesCounter);
    }

    public TaskObject() {
    }

    protected TaskObject(Parcel in) {
        this.id = in.readInt();
        this.user = in.readParcelable(TaskUser.class.getClassLoader());
        this.address = in.readParcelable(TaskAddress.class.getClassLoader());
        this.geoAddress = in.readParcelable(TaskGeoAddress.class.getClassLoader());
        this.category = in.readParcelable(TaskCategory.class.getClassLoader());
        this.type = in.readParcelable(TaskType.class.getClassLoader());
        this.title = in.readString();
        this.body = in.readString();
        this.createdDate = in.readLong();
        this.startDate = in.readLong();
        this.completedDate = in.readLong();
        this.state = in.readParcelable(TaskState.class.getClassLoader());
        this.ticketId = in.readString();
        this.files = new ArrayList<>();
        in.readList(this.files, TaskFile.class.getClassLoader());
        this.performers = new ArrayList<>();
        in.readList(this.performers, TaskPerformer.class.getClassLoader());
        this.deadline = in.readLong();
        this.likesCounter = in.readInt();
    }

    public static final Parcelable.Creator<TaskObject> CREATOR = new Parcelable.Creator<TaskObject>() {
        @Override
        public TaskObject createFromParcel(Parcel source) {
            return new TaskObject(source);
        }

        @Override
        public TaskObject[] newArray(int size) {
            return new TaskObject[size];
        }
    };
}