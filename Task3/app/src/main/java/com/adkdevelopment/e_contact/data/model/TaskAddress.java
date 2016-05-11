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

public class TaskAddress implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("district")
    @Expose
    private TaskDistrict district;
    @SerializedName("city")
    @Expose
    private TaskCity city;
    @SerializedName("street")
    @Expose
    private TaskStreet street;
    @SerializedName("house")
    @Expose
    private TaskHouse house;
    @SerializedName("flat")
    @Expose
    private String flat;

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
     * The district
     */
    public TaskDistrict getDistrict() {
        return district;
    }

    /**
     *
     * @param district
     * The district
     */
    public void setDistrict(TaskDistrict district) {
        this.district = district;
    }

    /**
     *
     * @return
     * The city
     */
    public TaskCity getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(TaskCity city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The street
     */
    public TaskStreet getStreet() {
        return street;
    }

    /**
     *
     * @param street
     * The street
     */
    public void setStreet(TaskStreet street) {
        this.street = street;
    }

    /**
     *
     * @return
     * The house
     */
    public TaskHouse getHouse() {
        return house;
    }

    /**
     *
     * @param house
     * The house
     */
    public void setHouse(TaskHouse house) {
        this.house = house;
    }

    /**
     *
     * @return
     * The flat
     */
    public String getFlat() {
        return flat;
    }

    /**
     *
     * @param flat
     * The flat
     */
    public void setFlat(String flat) {
        this.flat = flat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.district, flags);
        dest.writeParcelable(this.city, flags);
        dest.writeParcelable(this.street, flags);
        dest.writeParcelable(this.house, flags);
        dest.writeString(this.flat);
    }

    public TaskAddress() {
    }

    protected TaskAddress(Parcel in) {
        this.id = in.readInt();
        this.district = in.readParcelable(TaskDistrict.class.getClassLoader());
        this.city = in.readParcelable(TaskCity.class.getClassLoader());
        this.street = in.readParcelable(TaskStreet.class.getClassLoader());
        this.house = in.readParcelable(TaskHouse.class.getClassLoader());
        this.flat = in.readString();
    }

    public static final Parcelable.Creator<TaskAddress> CREATOR = new Parcelable.Creator<TaskAddress>() {
        @Override
        public TaskAddress createFromParcel(Parcel source) {
            return new TaskAddress(source);
        }

        @Override
        public TaskAddress[] newArray(int size) {
            return new TaskAddress[size];
        }
    };
}