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

package com.adkdevelopment.e_contact.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.ui.adapters.PagerAdapter;
import com.adkdevelopment.e_contact.ui.base.BaseActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initActionBar();

        initPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        // TODO: 5/11/16 add strings 
        pagerAdapter.addFragment(TasksFragment.newInstance(1, "0,9,5,7,8"), "In progress");
        pagerAdapter.addFragment(TasksFragment.newInstance(2, "10,6"), "Done");
        pagerAdapter.addFragment(TasksFragment.newInstance(3, "1,3,4"), "Waiting");

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        mTabLayout.setViewPager(mViewPager);
    }

}
