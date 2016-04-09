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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adkdevelopment.e_contact.provider.photos.PhotosColumns;
import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;
import com.adkdevelopment.e_contact.remote.FetchData;
import com.adkdevelopment.e_contact.remote.RSSNewsItem;
import com.adkdevelopment.e_contact.utils.Utilities;
import com.adkdevelopment.e_contact.utils.ZoomOutPageTransformer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Main screen with a Pager and a Navigation Drawer
 */
public class PagerActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Bind(R.id.layout_drawer) DrawerLayout mDrawerLayout;
    @Bind(R.id.drawer_view_navigation) NavigationView mNavigationView;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.appbar) AppBarLayout mAppBar;
    @Bind(R.id.tabs) TabLayout mTabLayout;
    @Bind(R.id.viewpager) ViewPager mViewPager;
    @Bind(R.id.drawer_footer_links) TextView mFooterLinks;
    @Bind(R.id.fab) FloatingActionButton mFab;

    private PagerAdapter mPagerAdapter;
    private static final String TAG = PagerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        // Fetch data on Create
        new FetchData(this).execute();

        // Set up ActionBar and corresponding icons
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());

        // zoom effect on swipe
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mTabLayout.setupWithViewPager(mViewPager);

        // Add links to the footer in the Drawer
        mFooterLinks.setText(Html.fromHtml(getString(R.string.drawer_footer_links)));
        mFooterLinks.setMovementMethod(LinkMovementMethod.getInstance());

        // Set custom font to the Drawer Header
        Typeface typeface = Typeface.createFromAsset(getAssets(), getString(R.string.font_roboto_bold));
        ((TextView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_header_text)).setTypeface(typeface);

        // set correct elevations
        if (mAppBar != null) {
            ViewCompat.setElevation(mAppBar, 0f);
            ViewCompat.setElevation(mToolbar, 16f);
        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PagerActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menu_action_sort:
                showSortMenu(findViewById(R.id.menu_action_sort));
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows PopupMenu on Filter button click in ActionBar
     * @param view of the button itself
     */
    public void showSortMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(PagerActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_filter_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.popup_filter_all:
                Utilities.setSortingPreference(this, 0);
                return true;
            case R.id.popup_filter_public:
                Utilities.setSortingPreference(this, 1);
                return true;
            case R.id.popup_filter_improvement:
                Utilities.setSortingPreference(this, 2);
                return true;
            default:
                return false;
        }
    }

    /**
     * Callback for the retrofit service, which updated ContentProvider
     */
    private Callback<List<RSSNewsItem>> mCallback = new Callback<List<RSSNewsItem>>() {
        @Override
        public void onResponse(Call<List<RSSNewsItem>> call, Response<List<RSSNewsItem>> response) {
            List<RSSNewsItem> mItemList = response.body();

            ContentResolver resolver = getContentResolver();

            for (RSSNewsItem each : mItemList) {

                ContentValues tasksItems = new ContentValues();

                tasksItems.put(TasksColumns.ID_TASK, each.getId());
                tasksItems.put(TasksColumns.STATUS, each.getStatus());
                tasksItems.put(TasksColumns.TYPE, each.getType());
                tasksItems.put(TasksColumns.DESCRIPTION, each.getDescription());
                tasksItems.put(TasksColumns.ADDRESS, each.getAddress());
                tasksItems.put(TasksColumns.RESPONSIBLE, each.getResponsible());
                tasksItems.put(TasksColumns.DATE_CREATED, each.getCreated());
                tasksItems.put(TasksColumns.DATE_REGISTERED, each.getRegistered());
                tasksItems.put(TasksColumns.DATE_ASSIGNED, each.getAssigned());
                tasksItems.put(TasksColumns.LONGITUDE, each.getLongitude());
                tasksItems.put(TasksColumns.LATITUDE, each.getLatitude());
                tasksItems.put(TasksColumns.LIKES, each.getLikes());

                // retrieve id of just inserted row and put it in a table, where it is a foreign key for photos
                long id = ContentUris.parseId(resolver.insert(TasksColumns.CONTENT_URI, tasksItems));
                for (String photo : each.getPhoto()) {
                    ContentValues photoValues = new ContentValues();
                    photoValues.put(PhotosColumns.TASK_ID, id);
                    photoValues.put(PhotosColumns.URL, photo);
                    resolver.insert(PhotosColumns.CONTENT_URI, photoValues);
                }
            }

            getContentResolver().notifyChange(TasksColumns.CONTENT_URI, null, false);
        }

        @Override
        public void onFailure(Call<List<RSSNewsItem>> call, Throwable t) {
            Log.d(TAG, "error " + t.toString());
        }
    };

    /**
     * Helper method to be called from fragments to fetch data
     * Not the most elegant way, but it works ok for now
     */
    public void fetchData() {
        new FetchData(this).execute();
    }

}
