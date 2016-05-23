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

package com.adkdevelopment.e_contact.data;

import android.content.Context;

import com.adkdevelopment.e_contact.App;
import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.data.local.TaskRealm;
import com.adkdevelopment.e_contact.data.local.TokenRealm;
import com.adkdevelopment.e_contact.injection.ApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Database class
 * Created by karataev on 5/11/16.
 */
public class DatabaseRealm {

    RealmConfiguration realmConfiguration;

    @Inject
    @ApplicationContext
    Context mContext;

    public DatabaseRealm() {
        App.getAppComponent().inject(this);
    }

    /**
     * Sets up database, deletes on changes.
     * We can possibly improve this part by adding migration rules in the production-ready app.
     */
    public void setup() {
        if (realmConfiguration == null) {
            realmConfiguration = new RealmConfiguration.Builder(mContext)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(realmConfiguration);
        } else {
            throw new IllegalStateException("database already configured");
        }
    }

    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    public <T extends RealmObject> T add(T model) {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
        return model;
    }

    /**
     * Returns all objects matching to the class parameter
     * @param clazz class of the object
     * @param <T> type of of the object
     * @return all matching elements
     */
    public <T extends RealmObject> List<T> findAll(Class<T> clazz) {
        return getRealmInstance().where(clazz).findAll();
    }

    public <T extends RealmObject> T find(Class<T> clazz) {
        return getRealmInstance().where(clazz).findFirst();
    }

    public <T extends RealmObject> List<T> findCoords(Class<T> clazz) {
        return getRealmInstance().where(clazz).greaterThan(TaskRealm.LATITUDE, 0.0).findAll();
    }

    /**
     * Returns all matching objects to the query parameter
     * @param clazz which we are looking for
     * @param state of the object accroding to the schema
     * @param <T> type of the object
     * @return all objects of the specified state
     */
    public <T extends RealmObject> List<T> findByState(Class<T> clazz, int state) {
        switch (state) {
            case TaskRealm.STATE_PROGRESS:
                return getRealmInstance().where(clazz)
                        .equalTo(TaskRealm.STATE, TaskRealm.WHERE_MODERATION).or()
                        .equalTo(TaskRealm.STATE, TaskRealm.WHERE_PROGRESS).or()
                        .equalTo(TaskRealm.STATE, TaskRealm.WHERE_UNKNOWN_7).or()
                        .equalTo(TaskRealm.STATE, TaskRealm.WHERE_UNKNOWN_8).or()
                        .equalTo(TaskRealm.STATE, TaskRealm.WHERE_UNKNOWN_9)
                        .findAll();
            case TaskRealm.STATE_DONE:
                return getRealmInstance().where(clazz)
                        .equalTo(TaskRealm.STATE, TaskRealm.WHERE_UNKNOWN_10).or()
                        .equalTo(TaskRealm.STATE, TaskRealm.WHERE_DONE)
                        .findAll();
            case TaskRealm.STATE_PENDING:
                return getRealmInstance().where(clazz)
                        .equalTo(TaskRealm.STATE, TaskRealm.WHERE_STILL_MODERATION).or()
                        .equalTo(TaskRealm.STATE, TaskRealm.WHERE_ACCEPTED).or()
                        .equalTo(TaskRealm.STATE, TaskRealm.WHERE_REVIEW)
                        .findAll();
        }
        return null;
    }

    /**
     * For each category queries the database and returns an aggregated list
     * @param categories to query the db
     * @return all tasks which correspond to the filter
     */
    public List<TaskRealm> findByCategories(int state, Integer[] categories) {

        RealmResults<TaskRealm> realmList = (RealmResults<TaskRealm>) findByState(TaskRealm.class, state);
        List<TaskRealm> arrayList = new ArrayList<>();

        // maps indices to the correct categories according to the db schema
        String[] mapCategories = mContext.getString(R.string.filter_map).split(", ");

        // if selection is equal to the number of possible categories - return all
        if (!(mapCategories.length == categories.length)) {
            for (int each : categories) {
                arrayList.addAll(realmList.where().equalTo(TaskRealm.CATEGORY,
                        Integer.valueOf(mapCategories[each])).findAll());
            }
        } else {
            arrayList.addAll(realmList);
        }

        // As we are querying db step by step, we need to sort it by id
        Collections.sort(arrayList, new Comparator<TaskRealm>() {
            @Override
            public int compare(TaskRealm lhs, TaskRealm rhs) {
                return lhs.compareTo(rhs);
            }
        });

        // and return tasks in descending order starting from the latest
        Collections.sort(arrayList, Collections.reverseOrder());

        return arrayList;
    }

    /**
     * Returns first available token from the database
     * @return token object
     */
    public TokenRealm getToken() {
        return getRealmInstance().where(TokenRealm.class).findFirst();
    }

    /**
     * Debugging function to delete all from the db
     */
    public void deleteAll() {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }
}
