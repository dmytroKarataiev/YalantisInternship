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

import com.adkdevelopment.e_contact.data.local.TaskObjectRealm;
import com.adkdevelopment.e_contact.data.model.TaskObject;
import com.adkdevelopment.e_contact.data.remote.ApiService;
import com.adkdevelopment.e_contact.injection.DataRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by karataev on 5/10/16.
 */
@Singleton
public class DataManager {

    private static final String TAG = DataManager.class.getSimpleName();

    private final ApiService mApiService;
    private final DataRepository mDataRepository;

    @Inject
    public DataManager(ApiService apiService, DataRepository dataRepository) {
        mApiService = apiService;
        mDataRepository = dataRepository;
    }

    public Observable<List<TaskObjectRealm>> getTasks(int query) {
        return mDataRepository.findByState(query);
    }

    public Observable<List<TaskObjectRealm>> fetchTasks(final int status) {

        String query = "";
        switch (status) {
            case TaskObjectRealm.STATE_PROGRESS:
                query = TaskObjectRealm.QUERY_PROGRESS;
                break;
            case TaskObjectRealm.STATE_DONE:
                query = TaskObjectRealm.QUERY_DONE;
                break;
            case TaskObjectRealm.STATE_PENDING:
                query = TaskObjectRealm.QUERY_PENDING;
                break;
        }

        // returns an observable which notifies a user when data was successfully downloaded
        return mApiService.getTasks(query).flatMap(new Func1<List<TaskObject>, Observable<List<TaskObjectRealm>>>() {
            @Override
            public Observable<List<TaskObjectRealm>> call(List<TaskObject> taskObjects) {
                return mDataRepository.addBulk(taskObjects);
            }
        });

    }
}