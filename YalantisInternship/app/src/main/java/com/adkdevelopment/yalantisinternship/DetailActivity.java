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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.adkdevelopment.yalantisinternship.remote.RSSNewsItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Launches the window with detailed information about a task
 * If there is no outside data - shows "dummy data" from resources
 */
public class DetailActivity extends AppCompatActivity {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        // Retrieve the share menu item
        MenuItem item = menu.findItem(R.id.share);

        // Get the provider and hold onto it to set/change the share intent.
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent());
        }
        else {
            Log.e(TAG, "ShareActionProvider is null");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, R.string.action_settings, Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    /**
     * Method to populate Intent with data
     * @return Intent with data to external apps
     */
    private Intent shareIntent() {
        RSSNewsItem rssNewsItem = getData();

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, rssNewsItem.getTitle() + " - "
                + rssNewsItem.getOwner() + " - "
                + rssNewsItem.getStatus() + " - "
                + "\n#Yalantis App");

        return sendIntent;
    }
}
