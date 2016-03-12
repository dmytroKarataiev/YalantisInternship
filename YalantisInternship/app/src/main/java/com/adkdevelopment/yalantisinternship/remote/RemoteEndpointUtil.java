/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016.  Dmytro Karataiev
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
package com.adkdevelopment.yalantisinternship.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class with implementation of REST client (Retrofit2)
 * Connects to the server, downloads data, creates objects from the data and returns it.
 */
public class RemoteEndpointUtil {
    private static final String TAG = "RemoteEndpointUtil";
    private static final String BASE_URL = "http://adkdevelopment.com";

    private RemoteEndpointUtil() {
    }

    /**
     * Method to connect and download data, works on background thread and is called from AsyncTask
     * @return List of RSSNewsItems
     */
    public static BlockingQueue<List<RSSNewsItem>> fetchItems() {
        final BlockingQueue<List<RSSNewsItem>> blockingQueue = new ArrayBlockingQueue<List<RSSNewsItem>>(1);
        Retrofit client = getRetrofitClient();
        RSSService service  = client.create(RSSService.class);
        Call<List<RSSNewsItem>> call = service.getRSS();

        try {
            retrofit2.Response<List<RSSNewsItem>> response = call.execute();
            List<RSSNewsItem> list = response.body();
            blockingQueue.add(list);
        } catch (IOException e) {
            Log.e(TAG, "fetchItems: " + e);
        }

        return blockingQueue;

    }

    /**
     * Retrofit client build method
     * @return set up Retrofit client
     */
    @NonNull
    private static Retrofit getRetrofitClient() {
        OkHttpClient okClient = new OkHttpClient
                .Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        }).build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
