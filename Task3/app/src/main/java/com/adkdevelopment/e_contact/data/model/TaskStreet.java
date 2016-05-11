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

public class TaskStreet implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ru_name")
    @Expose
    private String ruName;
    @SerializedName("street_type")
    @Expose
    private TaskStreetType streetType;
    @SerializedName("city_district")
    @Expose
    private TaskCityDistrict cityDistrict;

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The ruName
     */
    public String getRuName() {
        return ruName;
    }

    /**
     *
     * @param ruName
     * The ru_name
     */
    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    /**
     *
     * @return
     * The streetType
     */
    public TaskStreetType getStreetType() {
        return streetType;
    }

    /**
     *
     * @param streetType
     * The street_type
     */
    public void setStreetType(TaskStreetType streetType) {
        this.streetType = streetType;
    }

    /**
     *
     * @return
     * The cityDistrict
     */
    public TaskCityDistrict getCityDistrict() {
        return cityDistrict;
    }

    /**
     *
     * @param cityDistrict
     * The city_district
     */
    public void setCityDistrict(TaskCityDistrict cityDistrict) {
        this.cityDistrict = cityDistrict;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.ruName);
        dest.writeParcelable(this.streetType, flags);
        dest.writeParcelable(this.cityDistrict, flags);
    }

    public TaskStreet() {
    }

    protected TaskStreet(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ruName = in.readString();
        this.streetType = in.readParcelable(TaskStreetType.class.getClassLoader());
        this.cityDistrict = in.readParcelable(TaskCityDistrict.class.getClassLoader());
    }

    public static final Parcelable.Creator<TaskStreet> CREATOR = new Parcelable.Creator<TaskStreet>() {
        @Override
        public TaskStreet createFromParcel(Parcel source) {
            return new TaskStreet(source);
        }

        @Override
        public TaskStreet[] newArray(int size) {
            return new TaskStreet[size];
        }
    };
}
