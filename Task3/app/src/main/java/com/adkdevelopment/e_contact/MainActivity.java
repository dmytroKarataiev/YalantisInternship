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
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adkdevelopment.e_contact.ui.TasksFragment;
import com.adkdevelopment.e_contact.ui.adapters.PagerAdapter;
import com.adkdevelopment.e_contact.ui.base.BaseActivity;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karataev on 5/10/16.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.layout_drawer)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.drawer_view_navigation)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppBar;
    @BindView(R.id.tabs)
    SmartTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.drawer_footer_links)
    TextView mFooterLinks;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    // TODO: 5/14/16 shared prefs
    static Integer[] select = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
    static String buttonName = "Select all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initActionBar();

        initPager();

        initNavigationDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // select first item in navigation drawer on startup
        mNavigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_sort:
                // TODO: 5/15/16 add presenter 
                alertDialog();
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // If drawer is open - close it on a Hardware Back button
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Performs all preparation procedures to initialize Toolbar and ActionBar
     */
    private void initActionBar() {

        // Set up ActionBar and corresponding icons
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_drawer_menu);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            // TODO: 5/10/16 fix title
            supportActionBar.setTitle("Temporary");
        }
    }

    /**
     * Initialises a ViewPager and a TabLayout with a custom indicator
     * sets listeners to them
     */
    private void initPager() {
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        // TODO: 5/11/16 add strings 
        pagerAdapter.addFragment(TasksFragment.newInstance(0, "0,9,5,7,8"), getString(R.string.title_inprogress));
        pagerAdapter.addFragment(TasksFragment.newInstance(1, "10,6"), getString(R.string.title_completed));
        pagerAdapter.addFragment(TasksFragment.newInstance(2, "1,3,4"), getString(R.string.title_waiting));

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        mTabLayout.setViewPager(mViewPager);

        // TODO: 5/14/16 add create an inquiry activity 
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        mFab.getContentDescription(),
                        Toast.LENGTH_SHORT).show();
                alertDialog();
            }
        });

        // on second click on tab - scroll to the top if in TasksFragment
        mTabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                if (mViewPager.getCurrentItem() == position) {
                    Fragment fragment = pagerAdapter.getItem(position);
                    if (fragment instanceof TasksFragment) {
                        ((TasksFragment) fragment).scrollToTop();
                    }
                }
            }
        });

    }

    /**
     * Initialises Navigation Drawer and adds links movement to the footer
     * sets listener on an item click in the Drawer
     */
    private void initNavigationDrawer() {
        // Make links work in the Drawer
        mFooterLinks.setMovementMethod(LinkMovementMethod.getInstance());

        // add listener to the buttons in the navigation drawer
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.drawer_map) {
                    // TODO: 5/13/16 add MapsActivity
                    if ("main".equals("maintain)"/* Utilities.checkPlayServices(PagerActivity.this) */)) {
                        //Intent intent = new Intent(PagerActivity.this, MapsActivity.class);
                        //startActivity(intent);
                    }
                    mDrawerLayout.closeDrawers();
                    return false;
                } else {
                    item.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                }
            }
        });
    }

    private void alertDialog() {
        // TODO: 5/14/16 add resources & shared preferences
        final String[] array = new String[20];
        array[0] = "test 1";
        array[1] = "test 2";
        array[2] = "test 3";
        array[3] = "test 4";
        array[4] = "test 5";
        array[5] = "test 1";
        array[6] = "test 2";
        array[7] = "test 3";
        array[8] = "test 4";
        array[9] = "test 5";
        array[10] = "test 1";
        array[11] = "test 2";
        array[12] = "test 3";
        array[13] = "test 4";
        array[14] = "test 5";
        array[15] = "test 1";
        array[16] = "test 2";
        array[17] = "test 3";
        array[18] = "test 4";
        array[19] = "test 5";

        if (select.length == array.length) {
            buttonName = "Clear all";
        }

        new MaterialDialog.Builder(this)
                .title("Social networks")
                .items(array)
                .itemsCallbackMultiChoice(select, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        select = which;
                        return true;
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.d("MainActivity", which.toString() + " " + dialog.getActionButton(which).getText());
                        if (dialog.getActionButton(which).getText().equals("Select all")) {
                            buttonName = "Clear all";
                            dialog.selectAllIndicies();
                            dialog.getActionButton(which).setText("Clear all");
                        } else {
                            buttonName = "Select all";
                            dialog.clearSelectedIndices();
                            dialog.getActionButton(which).setText("Select all");
                        }

                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText("positive")
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .neutralText(buttonName)
                .show();
    }

}
