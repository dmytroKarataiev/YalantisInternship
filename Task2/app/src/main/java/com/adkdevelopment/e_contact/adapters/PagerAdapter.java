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

package com.adkdevelopment.e_contact.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.TasksFragment;

import java.lang.ref.WeakReference;

/**
 * Basic Pager for the fragments
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public static final int LISTVIEW_FRAGMENT = 2;

    public static final int FRAGMENT_PROGRESS = 0;
    public static final int FRAGMENT_COMPLETED = 1;
    public static final int FRAGMENT_WAITING = 2;

    public static final int[] FRAGMENTS =
            { FRAGMENT_PROGRESS,
            FRAGMENT_COMPLETED,
            FRAGMENT_WAITING };

    // Keeps references to the fragment so not to recreate them and being able to communicate without problems
    final SparseArray<WeakReference<Fragment>> registeredFragments = new SparseArray<>();

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return TasksFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return FRAGMENTS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case FRAGMENT_PROGRESS:
                return mContext.getString(R.string.title_inprogress);
            case FRAGMENT_COMPLETED:
                return mContext.getString(R.string.title_completed);
            case FRAGMENT_WAITING:
                return mContext.getString(R.string.title_waiting);
            default:
                return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    /**
     * Returns fragment from the Array if it was created before
     * @param position of the Pager
     * @return either a new fragment, or an instance of a saved
     */
    public Fragment getRegisteredFragment(int position) {
        if (registeredFragments.valueAt(position) != null) {
            return registeredFragments.valueAt(position).get();
        } else {
            return getItem(position);
        }
    }
}
