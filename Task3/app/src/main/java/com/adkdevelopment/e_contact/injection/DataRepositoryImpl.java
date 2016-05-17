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

package com.adkdevelopment.e_contact.injection;

import android.util.Log;

import com.adkdevelopment.e_contact.App;
import com.adkdevelopment.e_contact.data.local.DatabaseRealm;
import com.adkdevelopment.e_contact.data.local.TaskRealm;
import com.adkdevelopment.e_contact.data.model.TaskObject;
import com.adkdevelopment.e_contact.utils.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by karataev on 5/12/16.
 */
public class DataRepositoryImpl implements DataRepository {

    private static final String TAG = DataRepositoryImpl.class.getSimpleName();

    @Inject DatabaseRealm mDatabaseRealm;

    public DataRepositoryImpl() {
        App.getAppComponent().inject(this);
    }

    @Override
    public Observable<TaskRealm> add(final TaskObject model) {
        return Observable.create(new Observable.OnSubscribe<TaskRealm>() {
            @Override
            public void call(Subscriber<? super TaskRealm> subscriber) {
                try {
                    subscriber.onNext(mDatabaseRealm.add(Utilities.convertTask(model)));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    Log.e(TAG, "Error: " + e);
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<List<TaskRealm>> addBulk(final List<TaskObject> list) {
        return Observable.create(new Observable.OnSubscribe<List<TaskRealm>>() {

            @Override
            public void call(Subscriber<? super List<TaskRealm>> subscriber) {
                try {
                    List<TaskRealm> realmList = new ArrayList<>();
                    for (TaskObject each : list) {
                        realmList.add(mDatabaseRealm.add(Utilities.convertTask(each)));
                    }
                    subscriber.onNext(realmList);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    Log.e(TAG, "Error: " + e);
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<List<TaskRealm>> findAll() {
        Log.d(TAG, "find all");
        return Observable.create(new Observable.OnSubscribe<List<TaskRealm>>() {
            @Override
            public void call(Subscriber<? super List<TaskRealm>> subscriber) {
                try {
                    RealmResults<TaskRealm> list = (RealmResults<TaskRealm>)
                            mDatabaseRealm.findAll(TaskRealm.class);
                    List<TaskRealm> tasksList = new ArrayList<>();
                    for (TaskRealm each : list) {
                        tasksList.add(each);
                    }
                    subscriber.onNext(tasksList);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<List<TaskRealm>> findByState(final int state) {
        Log.d(TAG, "find by state " + state);
        return Observable.create(new Observable.OnSubscribe<List<TaskRealm>>() {
            @Override
            public void call(Subscriber<? super List<TaskRealm>> subscriber) {
                try {
                    RealmResults<TaskRealm> list = (RealmResults<TaskRealm>)
                            mDatabaseRealm.findByState(TaskRealm.class, state);
                    List<TaskRealm> tasksList = new ArrayList<>();
                    for (TaskRealm each : list) {
                        tasksList.add(each);
                    }
                    subscriber.onNext(tasksList);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<List<TaskRealm>> findByCategories(final int state, final Integer[] categories) {
        Log.d(TAG, "find by state " + Arrays.asList(categories).toString());
        return Observable.create(new Observable.OnSubscribe<List<TaskRealm>>() {
            @Override
            public void call(Subscriber<? super List<TaskRealm>> subscriber) {
                try {
                    List<TaskRealm> list = mDatabaseRealm.findByCategories(state, categories);

                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
