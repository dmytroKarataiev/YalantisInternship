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

package com.adkdevelopment.e_contact.ui;

import android.content.SharedPreferences;
import android.util.Log;

import com.adkdevelopment.e_contact.data.DataManager;
import com.adkdevelopment.e_contact.data.local.TaskRealm;
import com.adkdevelopment.e_contact.injection.PrefsManager;
import com.adkdevelopment.e_contact.ui.base.BaseMvpPresenter;
import com.adkdevelopment.e_contact.ui.contract.TasksContract;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by karataev on 5/10/16.
 */
public class TasksPresenter
        extends BaseMvpPresenter<TasksContract.View> implements TasksContract.Presenter, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = TasksPresenter.class.getSimpleName();

    private final DataManager mDataManager;
    private PrefsManager mPreferenceManager;
    private CompositeSubscription mSubscription;

    @Inject
    public TasksPresenter(DataManager dataManager, PrefsManager preferenceManager) {
        mSubscription = new CompositeSubscription();
        mDataManager = dataManager;
        mPreferenceManager = preferenceManager;
    }

    @Override
    public void attachView(TasksContract.View mvpView) {
        super.attachView(mvpView);
        mPreferenceManager.getSharedPrefs().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        mPreferenceManager.getSharedPrefs().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void getData(int query) {
        checkViewAttached();
        getMvpView().showProgress(true);

        // get data from the database and update a view
        mSubscription.add(mDataManager.getTasks(query)
                .subscribe(new Subscriber<List<TaskRealm>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error Getting: " + e);
                        getMvpView().showError();
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onNext(List<TaskRealm> taskRealms) {
                        if (taskRealms.isEmpty()) {
                            getMvpView().showTasks(true);
                        } else {
                            getMvpView().getData(taskRealms);
                        }
                        getMvpView().showProgress(false);
                    }
                }));
    }

    public void fetchData(int query, int page, int offset) {
        checkViewAttached();
        getMvpView().showProgress(true);

        // TODO: 5/13/16 if Online check
        // fetch new data and update a view if there are new objects
        mSubscription.add(mDataManager.fetchTasks(query, page, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<TaskRealm>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError();
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onNext(List<TaskRealm> taskRealms) {
                        if (taskRealms.isEmpty()) {
                            getMvpView().showTasks(false);
                        } else {
                            getMvpView().addData(taskRealms);
                        }
                        getMvpView().showProgress(false);
                    }
                }));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, key + " " + Arrays.asList(mPreferenceManager.getFilterSelection()).toString());
        // TODO: 5/15/16 get filtered data 
    }
}
