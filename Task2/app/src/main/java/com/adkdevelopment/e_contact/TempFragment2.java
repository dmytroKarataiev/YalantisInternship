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

package com.adkdevelopment.e_contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adkdevelopment.e_contact.remote.RSSNewsItem;

import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karataev on 4/8/16.
 */
public class TempFragment2 extends Fragment {

    @Bind(R.id.temp_title_text) TextView mTempTitle;

    // List of tasks
    private List<RSSNewsItem> mItemList;

    public TempFragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.temp_layout, container, false);

        App.getApiManager().getService().getData().enqueue(callback);

        return rootView;
    }

    /**
     * Custom callback to perform actions on data update
     */
    private Callback<List<RSSNewsItem>> callback = new Callback<List<RSSNewsItem>>() {
        @Override
        public void onResponse(Call<List<RSSNewsItem>> call, Response<List<RSSNewsItem>> response) {
            mItemList = response.body();
            Log.d("TempFragment2", "mItemList:" + mItemList.size());

            if (mItemList.size() > 0) {
                Log.d("TempFragment2", mItemList.get(0).getAddress());
                for (RSSNewsItem each : mItemList) {
                    Log.d("TempFragment2", each.getAddress());
                }
            }
        }

        @Override
        public void onFailure(Call<List<RSSNewsItem>> call, Throwable t) {
            Log.d("TempFragment2", "error " + t.toString());
        }
    };
}
