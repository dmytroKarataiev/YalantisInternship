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

public class TaskUser implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("middle_name")
    @Expose
    private String middleName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("birthday")
    @Expose
    private long birthday;
    @SerializedName("address")
    @Expose
    private TaskAddress address;
    @SerializedName("fb_registered")
    @Expose
    private int fbRegistered;

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
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     *
     * @param middleName
     * The middle_name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The birthday
     */
    public long getBirthday() {
        return birthday;
    }

    /**
     *
     * @param birthday
     * The birthday
     */
    public void setBirthday(long birthday) {
        this.birthday = birthday;
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
     * The fbRegistered
     */
    public int getFbRegistered() {
        return fbRegistered;
    }

    /**
     *
     * @param fbRegistered
     * The fb_registered
     */
    public void setFbRegistered(int fbRegistered) {
        this.fbRegistered = fbRegistered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.middleName);
        dest.writeString(this.email);
        dest.writeLong(this.birthday);
        dest.writeParcelable(this.address, flags);
        dest.writeInt(this.fbRegistered);
    }

    public TaskUser() {
    }

    protected TaskUser(Parcel in) {
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.middleName = in.readString();
        this.email = in.readString();
        this.birthday = in.readLong();
        this.address = in.readParcelable(TaskAddress.class.getClassLoader());
        this.fbRegistered = in.readInt();
    }

    public static final Parcelable.Creator<TaskUser> CREATOR = new Parcelable.Creator<TaskUser>() {
        @Override
        public TaskUser createFromParcel(Parcel source) {
            return new TaskUser(source);
        }

        @Override
        public TaskUser[] newArray(int size) {
            return new TaskUser[size];
        }
    };
}