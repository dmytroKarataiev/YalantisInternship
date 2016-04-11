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

package com.adkdevelopment.yalantisinternship;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.adkdevelopment.yalantisinternship.remote.RSSNewsItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Launches the window with detailed information about a task
 * If there is no outside data - shows "dummy data" from resources
 */
public class DetailActivity extends AppCompatActivity {
    //[Comment] Please remove all menu items, .idea still in git repo
    private final String TAG = DetailActivity.class.getSimpleName();

    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);

        // Initialize a custom Toolbar
        setSupportActionBar(mToolbar);

        // Add back button to the actionbar if the app is full version
        if (getApplication().getPackageName()
                .contains(getString(R.string.app_name_full))) {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // populate the item with data
        RSSNewsItem rssNewsItem = getData();

        // Set the title bar with Task title
        String actionbarTitle = getString(R.string.task_asctionbar_title) + " " + rssNewsItem.getTitle();
        getSupportActionBar().setTitle(actionbarTitle);

    }

    /**
     * Gets RSSNewsItem from the intent or creates it from the internal data
     * @return RSSNewsItem object with the data
     */
    private RSSNewsItem getData() {
        // If launched from the List Activity - get data from the intent
        RSSNewsItem rssNewsItem;

        if (getIntent().hasExtra(RSSNewsItem.TASKITEM)) {
            rssNewsItem = getIntent().getParcelableExtra(RSSNewsItem.TASKITEM);
        } else {
            // Example data
            rssNewsItem = new RSSNewsItem();
            rssNewsItem.setTitle(getString(R.string.task_title_example));
            rssNewsItem.setOwner(getString(R.string.task_owner));
            rssNewsItem.setStatus(getString(R.string.task_status));
        }

        return rssNewsItem;
    }
}
