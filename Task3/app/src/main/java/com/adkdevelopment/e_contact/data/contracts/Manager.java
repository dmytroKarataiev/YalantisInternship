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

package com.adkdevelopment.e_contact.data.contracts;

import android.content.SharedPreferences;

import com.adkdevelopment.e_contact.data.local.ProfileRealm;
import com.adkdevelopment.e_contact.data.local.TaskRealm;
import com.adkdevelopment.e_contact.data.model.TaskObject;
import com.facebook.AccessToken;

import java.util.List;

import rx.Observable;

/**
 * Contract for all managers in the app.
 * Created by karataev on 5/20/16.
 */
public interface Manager {

    /**
     * Realm Database Manager.
     */
    interface RealmManager {

        Observable<TaskRealm> add(TaskObject model);

        Observable<Boolean> add(ProfileRealm profileRealm);

        Observable<List<TaskRealm>> addBulk(List<TaskObject> list);

        Observable<List<TaskRealm>> findAll();

        Observable<List<TaskRealm>> findByState(int state);

        Observable<List<TaskRealm>> findByCategories(int state, Integer[] categories);

        void saveToken(AccessToken accessToken);

        AccessToken getToken();

        Observable<ProfileRealm> getProfile();

    }

    /**
     * Manages all model-related issues: data fetching, database work,
     * retrieval of information.
     */
    interface DataManager {

        Observable<List<TaskRealm>> getTasks(int query);

        Observable<List<TaskRealm>> getTasks(int query, Integer[] categories);

        Observable<List<TaskRealm>> fetchTasks(final int status, int page, int offset);

        void saveAccessToken(AccessToken accessToken);

        AccessToken getAccessToken();

        Observable<ProfileRealm> getProfile();

        Observable<Boolean> saveProfile(ProfileRealm profileRealm);

    }

    /**
     * SharedPreferences manager.
     */
    interface PrefsManager {

        void clear();

        void saveFilterSelection(Integer[] selection);

        Integer[] getFilterSelection();

        SharedPreferences getSharedPrefs();

    }

}
