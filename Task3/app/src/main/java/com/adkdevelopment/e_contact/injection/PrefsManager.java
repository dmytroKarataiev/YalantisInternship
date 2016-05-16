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

package com.adkdevelopment.e_contact.injection;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.adkdevelopment.e_contact.R;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * SharedPrefences manager as a Singleton
 * Created by karataev on 5/15/16.
 */
@Singleton
public class PrefsManager {

    private final SharedPreferences mPref;
    private Context mContext;

    @Inject
    public PrefsManager(@ApplicationContext Context context) {
        mPref = PreferenceManager.getDefaultSharedPreferences(context);
        mContext = context;
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    /**
     * Saves selection from the filter menu as a String in SharedPreferences
     * @param selection of checked elements in the filter menu
     */
    public void saveFilterSelection(Integer[] selection) {
        String selectionString = Arrays.asList(selection).toString();
        Log.d("PrefsManager", selectionString);
        mPref.edit()
                .putString(mContext.getString(R.string.sharedprefs_selection), selectionString)
                .apply();
    }

    /**
     * Retrieves filter preferences from the SharedPreferences as a String and performs
     * convertion to the Integer[] array
     * @return Integer[] array of checked elements in a multichoice dialog
     */
    public Integer[] getFilterSelection() {
        String selection = mPref.getString(mContext.getString(R.string.sharedprefs_selection),
                mContext.getString(R.string.filter_all));

        // not the most elegant way of storing filters
        String[] selectionSplit = selection.substring(1, selection.length() - 1).split(",");
        Integer[] selectionArray = new Integer[selectionSplit.length];
        if (selectionSplit.length < 1 || selectionSplit[0].length() < 1) {
            return new Integer[]{};
        }
        for (int i = 0, n = selectionSplit.length; i < n; i++) {
            selectionArray[i] = Integer.parseInt(selectionSplit[i].replaceAll(" ", ""));
        }
        return selectionArray;
    }

    public SharedPreferences getSharedPrefs() {
        return mPref;
    }

}