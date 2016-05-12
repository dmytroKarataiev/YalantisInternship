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

import android.content.Context;
import android.util.Log;

import com.adkdevelopment.e_contact.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by karataev on 5/11/16.
 */
@Singleton
public class DatabaseRealm {

    RealmConfiguration realmConfiguration;
    private Context mContext;

    @Inject
    public DatabaseRealm(@ApplicationContext Context context) {
        mContext = context;
        setup();
    }

    public void setup() {
        if (realmConfiguration == null) {
            realmConfiguration = new RealmConfiguration.Builder(mContext).build();
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
        realm.copyToRealm(model);
        realm.commitTransaction();
        return model;
    }

    public <T extends RealmObject> Observable<RealmResults<T>> findAll(Class<T> clazz) {

        RealmResults<T> realms = getRealmInstance().where(clazz).findAll();

        for (T each : realms) {
            if (each instanceof TaskObjectRealm) {
                Log.d("DatabaseRealm", ((TaskObjectRealm) each).getAddress());
            }
        }

        return realms.asObservable();
    }
}
