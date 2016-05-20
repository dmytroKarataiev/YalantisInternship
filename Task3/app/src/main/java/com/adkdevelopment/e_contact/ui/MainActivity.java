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

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.data.local.TaskRealm;
import com.adkdevelopment.e_contact.ui.presenters.MainPresenter;
import com.adkdevelopment.e_contact.ui.adapters.PagerAdapter;
import com.adkdevelopment.e_contact.ui.base.BaseActivity;
import com.adkdevelopment.e_contact.ui.contract.MainContract;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.AccessToken;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karataev on 5/10/16.
 */
public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject MainPresenter mPresenter;

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
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter.attachView(this);

        initActionBar();

        initPager();

        initNavigationDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDrawer();
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
                mPresenter.loadDialog();
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
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Initialises a ViewPager and a TabLayout with a custom indicator
     * sets listeners to them
     */
    private void initPager() {
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(TasksFragment.newInstance(TaskRealm.STATE_PROGRESS,
                TaskRealm.QUERY_PROGRESS), getString(R.string.title_inprogress));
        pagerAdapter.addFragment(TasksFragment.newInstance(TaskRealm.STATE_DONE,
                TaskRealm.QUERY_DONE), getString(R.string.title_done));
        pagerAdapter.addFragment(TasksFragment.newInstance(TaskRealm.STATE_PENDING,
                TaskRealm.QUERY_PENDING), getString(R.string.title_pending));

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
                switch (item.getItemId()) {
                    case R.id.drawer_map:
                        // TODO: 5/13/16 add MapsActivity
                        if ("main".equals("maintain)"/* Utilities.checkPlayServices(PagerActivity.this) */)) {
                            //Intent intent = new Intent(PagerActivity.this, MapsActivity.class);
                            //startActivity(intent);
                        }
                        mDrawerLayout.closeDrawers();
                        return false;
                    case R.id.login_button:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        return true;
                    case R.id.profile_button:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        return true;
                    default:
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                }
            }
        });
    }

    /**
     * Updated buttons in the Navigation Drawer according to a state
     */
    private void updateDrawer() {
        // select first item in navigation drawer on startup
        Menu drawerMenu = mNavigationView.getMenu();
        drawerMenu.getItem(0).setChecked(true);

        // set correct visibility of we are logged in or logged out
        MenuItem facebookProfile = drawerMenu.findItem(R.id.profile_button);
        MenuItem login = drawerMenu.findItem(R.id.login_button);
        if (facebookProfile != null) {
            if (AccessToken.getCurrentAccessToken() != null) {
                login.setVisible(false);
                facebookProfile.setVisible(true);
            } else {
                login.setVisible(true);
                facebookProfile.setVisible(false);
            }
        }
    }

    /**
     * MaterialDialog with a filtering preferences
     * @param select Integer[] of checked elements
     */
    @Override
    public void showDialog(Integer[] select) {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.dialog_title))
                .items(getResources().getStringArray(R.array.filter_strings))
                .itemsCallbackMultiChoice(select, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        return true;
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        if (dialog.getActionButton(which).getText()
                                .equals(getString(R.string.dialog_select_all))) {
                            dialog.selectAllIndicies();
                            dialog.getActionButton(which)
                                    .setText(getString(R.string.dialog_select_none));
                        } else {
                            dialog.clearSelectedIndices();
                            dialog.getActionButton(which)
                                    .setText(getString(R.string.dialog_select_all));
                        }
                    }
                })
                .positiveText(getString(R.string.dialog_accept))
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        mPresenter.saveDialog(dialog.getSelectedIndices());
                    }
                })
                .neutralText(getString(R.string.dialog_select_all))
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
