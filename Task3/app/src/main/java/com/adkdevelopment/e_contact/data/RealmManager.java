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

import com.adkdevelopment.e_contact.App;
import com.adkdevelopment.e_contact.data.contracts.Manager;
import com.adkdevelopment.e_contact.data.local.ProfileRealm;
import com.adkdevelopment.e_contact.data.local.TaskRealm;
import com.adkdevelopment.e_contact.data.local.TokenRealm;
import com.adkdevelopment.e_contact.data.model.TaskObject;
import com.adkdevelopment.e_contact.utils.Utilities;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by karataev on 5/12/16.
 */
public class RealmManager implements Manager.RealmManager {

    private static final String TAG = RealmManager.class.getSimpleName();

    @Inject
    DatabaseRealm mDatabaseRealm;

    public RealmManager() {
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
    public Observable<Boolean> add(final ProfileRealm profileRealm) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean added = mDatabaseRealm.add(profileRealm) != null;
                    subscriber.onNext(added);
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
    public AccessToken getToken() {

        TokenRealm tokenRealm = mDatabaseRealm.getToken();

        if (tokenRealm == null) {
            return null;
        }

        return new AccessToken(tokenRealm.getToken(),
                tokenRealm.getAppId(),
                tokenRealm.getUserId(),
                tokenRealm.getPermissionsList(),
                tokenRealm.getDeclinedPermissionsList(),
                AccessTokenSource.valueOf(tokenRealm.getTokenSource()),
                new Date(tokenRealm.getExpirationDate()),
                new Date(tokenRealm.getLastRefreshTime()));
    }

    @Override
    public Observable<ProfileRealm> getProfile() {
        return Observable.create(new Observable.OnSubscribe<ProfileRealm>() {
            @Override
            public void call(Subscriber<? super ProfileRealm> subscriber) {
                try {
                    subscriber.onNext(mDatabaseRealm.find(ProfileRealm.class));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    Log.e(TAG, "Error: " + e);
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public void saveToken(AccessToken accessToken) {

        TokenRealm tokenRealm = new TokenRealm();
        tokenRealm.setAppId(accessToken.getApplicationId());
        tokenRealm.setUserId(accessToken.getUserId());
        tokenRealm.setToken(accessToken.getToken());
        tokenRealm.setTokenSource(accessToken.getSource().toString());
        tokenRealm.setPermissions(accessToken.getPermissions());
        tokenRealm.setDeclinedPermissions(accessToken.getDeclinedPermissions());
        tokenRealm.setExpirationDate(accessToken.getExpires().getTime());
        tokenRealm.setLastRefreshTime(accessToken.getLastRefresh().getTime());
        mDatabaseRealm.add(tokenRealm);
    }

    @Override
    public Observable<List<TaskRealm>> findAll() {

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
