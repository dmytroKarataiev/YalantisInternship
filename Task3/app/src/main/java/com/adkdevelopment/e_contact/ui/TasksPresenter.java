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

import android.util.Log;

import com.adkdevelopment.e_contact.data.DataManager;
import com.adkdevelopment.e_contact.data.local.TaskObjectRealm;
import com.adkdevelopment.e_contact.ui.base.BaseMvpPresenter;
import com.adkdevelopment.e_contact.ui.contract.TasksContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by karataev on 5/10/16.
 */
public class TasksPresenter
        extends BaseMvpPresenter<TasksContract.View>
        implements TasksContract.Presenter {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public TasksPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void loadData(String query) {
        Log.d("Presenter", "loadData: ");
        checkViewAttached();
        mDataManager.fetchTasks(query);
        mSubscription = mDataManager.getTasks(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<TaskObjectRealm>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TasksPresenter", "e:" + e);
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<TaskObjectRealm> taskObjects) {
                        if (taskObjects.isEmpty()) {
                            getMvpView().showTasksEmpty();
                        } else {
                            getMvpView().showData(taskObjects);
                        }
                    }
                });
    }

}
