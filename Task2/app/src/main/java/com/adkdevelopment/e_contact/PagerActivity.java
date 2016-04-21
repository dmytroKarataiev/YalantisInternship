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

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adkdevelopment.e_contact.adapters.PagerAdapter;
import com.adkdevelopment.e_contact.remote.FetchData;
import com.adkdevelopment.e_contact.utils.UnderlinePageIndicator;
import com.adkdevelopment.e_contact.utils.Utilities;
import com.adkdevelopment.e_contact.utils.ZoomOutPageTransformer;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    // tab indicator
    @Bind(R.id.indicator) UnderlinePageIndicator mTabIndicator;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        // Fetch data on Create only on first launch
        if (App.firstLaunch) {
            new FetchData(this).execute();
            App.firstLaunch = false;
        }

        // Set up ActionBar and corresponding icons
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(Utilities
                            .getActionbarTitle(this, Utilities.getSortingPreference(this)));
        }

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());

        //Bind the title indicator to the adapter
        mTabIndicator.setViewPager(mViewPager);
        mTabIndicator.setFades(false);

        // zoom effect on swipe
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mTabLayout.setupWithViewPager(mViewPager);

        // Make links work in the Drawer
        mFooterLinks.setMovementMethod(LinkMovementMethod.getInstance());

        // set correct elevations
        if (mAppBar != null) {
            ViewCompat.setElevation(mAppBar, 0f);
            // uncomment next line if you want to make elevations as in the first specification
            // ViewCompat.setElevation(mToolbar, 16f);
        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PagerActivity.this, mFab.getContentDescription(), Toast.LENGTH_SHORT).show();
            }
        });

        // on second click on tab - scroll to the top if in TasksFragment
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Fragment fragment = mPagerAdapter.getRegisteredFragment(tab.getPosition());
                if (fragment instanceof TasksFragment) {
                    ((TasksFragment) fragment).scrollToTop();
                }
            }
        });

        // add listener to the buttons in the navigation drawer
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.drawer_map) {
                    item.setChecked(false);
                    mDrawerLayout.closeDrawers();
                    if (Utilities.checkPlayServices(PagerActivity.this)) {
                        Intent intent = new Intent(PagerActivity.this, MapsActivity.class);
                        startActivity(intent);
                        return true;
                    } else {
                        item.setChecked(false);
                        return true;
                    }
                } else {
                    item.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                }
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
            case R.id.popup_filter_architecture:
                Utilities.setSortingPreference(this, 3);
                return true;
            default:
                return false;
        }
    }

    /**
     * Helper method to be called from fragments to fetch data
     * Not the most elegant way, but it works ok for now
     */
    public void fetchData() {
        new FetchData(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // select first item in navigation drawer on startup
        mNavigationView.getMenu().getItem(0).setChecked(true);
    }
}
