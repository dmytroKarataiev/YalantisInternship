package com.adkdevelopment.e_contact;

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

import android.app.Application;
import android.content.Context;

import com.adkdevelopment.e_contact.data.DatabaseRealm;
import com.adkdevelopment.e_contact.injection.component.AppComponent;
import com.adkdevelopment.e_contact.injection.component.DaggerAppComponent;
import com.adkdevelopment.e_contact.injection.module.AppModule;
import com.facebook.FacebookSdk;

import javax.inject.Inject;

/**
 * Custom App class to init all the needed components
 * Created by karataev on 5/10/16.
 */
public class App extends Application {

    @Inject DatabaseRealm mDatabaseRealm;
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initAppComponent(this);
        getAppComponent().inject(this);
        mDatabaseRealm.setup();
        FacebookSdk.sdkInitialize(this);
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    private static void initAppComponent(App app) {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    public AppComponent getComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return mAppComponent;
    }

}
