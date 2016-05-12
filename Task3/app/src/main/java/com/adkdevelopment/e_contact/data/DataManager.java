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

import android.util.Log;

import com.adkdevelopment.e_contact.Utilities;
import com.adkdevelopment.e_contact.data.local.DatabaseRealm;
import com.adkdevelopment.e_contact.data.local.TaskObjectRealm;
import com.adkdevelopment.e_contact.data.model.TaskObject;
import com.adkdevelopment.e_contact.data.remote.ApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by karataev on 5/10/16.
 */
@Singleton
public class DataManager {

    private final ApiService mApiService;
    private final DatabaseRealm mDatabaseRealm;

    @Inject
    public DataManager(ApiService apiService, DatabaseRealm databaseRealm) {
        mApiService = apiService;
        mDatabaseRealm = databaseRealm;
    }

    public Observable<List<TaskObjectRealm>> getTasks(String query) {

        Log.d("DataManager", "getTasks: " + query);
        return mDatabaseRealm.findAll(TaskObjectRealm.class).map(new Func1<RealmResults<TaskObjectRealm>, List<TaskObjectRealm>>() {
            @Override
            public List<TaskObjectRealm> call(RealmResults<TaskObjectRealm> taskObjectRealms) {
                List<TaskObjectRealm> tasks = new ArrayList<>();
                tasks.addAll(taskObjectRealms);
                for (TaskObjectRealm task : taskObjectRealms) {
                    Log.d("DataManager", task.getAddress());
                }
                return tasks;
            }
        });
    }

    public void fetchTasks(String query) {
        mApiService.getTasks(query)
                .flatMap(new Func1<List<TaskObject>, Observable<TaskObject>>() {
            @Override
            public Observable<TaskObject> call(List<TaskObject> taskObjects) {
                        return Observable.from(taskObjects); }
                })
                .subscribe(new Subscriber<TaskObject>() {
                    @Override
                    public void onCompleted() {
                        Log.d("DataManager", "completed");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TaskObject taskObject) {
                        mDatabaseRealm.add(Utilities.convertTask(taskObject));
                    }
        });
    }
}
