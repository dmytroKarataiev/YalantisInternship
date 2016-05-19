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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Cached token from the Facebook
 * Created by karataev on 5/19/16.
 */
public class TokenRealm extends RealmObject {

    @PrimaryKey private String token;
    private String appId;
    private String userId;
    private RealmList<StringRealm> permissions;
    private RealmList<StringRealm> declinedPermissions;
    private String tokenSource;
    private long expirationDate;
    private long lastRefreshTime;

    public long getLastRefreshTime() {
        return lastRefreshTime;
    }

    public void setLastRefreshTime(long lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RealmList<StringRealm> getPermissions() {
        return permissions;
    }

    public List<String> getPermissionsList() {

        List<String> permissions = new ArrayList<>();

        for (StringRealm each : this.permissions) {
            permissions.add(each.getPermission());
        }

        return permissions;
    }

    public List<String> getDeclinedPermissionsList() {

        List<String> permissions = new ArrayList<>();

        for (StringRealm each : this.declinedPermissions) {
            permissions.add(each.getPermission());
        }

        return permissions;
    }

    public void setPermissions(RealmList<StringRealm> permissions) {
        this.permissions = permissions;
    }

    public void setPermissions(Set<String> permissions) {

        RealmList<StringRealm> list = new RealmList<>();

        for (String each : permissions) {
            StringRealm permission = new StringRealm();
            permission.setPermission(each);
            list.add(permission);
        }

        this.permissions = list;
    }

    public void setDeclinedPermissions(Set<String> permissions) {

        RealmList<StringRealm> list = new RealmList<>();

        for (String each : permissions) {
            StringRealm permission = new StringRealm();
            permission.setPermission(each);
            list.add(permission);
        }

        this.declinedPermissions = list;
    }

    public RealmList<StringRealm> getDeclinedPermissions() {
        return declinedPermissions;
    }

    public void setDeclinedPermissions(RealmList<StringRealm> declinedPermissions) {
        this.declinedPermissions = declinedPermissions;
    }

    public String getTokenSource() {
        return tokenSource;
    }

    public void setTokenSource(String tokenSource) {
        this.tokenSource = tokenSource;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

}
