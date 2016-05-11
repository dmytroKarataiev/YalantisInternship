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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskPerformer {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("person")
    @Expose
    private String person;
    @SerializedName("deadline")
    @Expose
    private int deadline;

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
     * The organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     *
     * @param organization
     * The organization
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     *
     * @return
     * The person
     */
    public String getPerson() {
        return person;
    }

    /**
     *
     * @param person
     * The person
     */
    public void setPerson(String person) {
        this.person = person;
    }

    /**
     *
     * @return
     * The deadline
     */
    public int getDeadline() {
        return deadline;
    }

    /**
     *
     * @param deadline
     * The deadline
     */
    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

}