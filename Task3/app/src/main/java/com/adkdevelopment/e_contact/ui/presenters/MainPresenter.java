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

package com.adkdevelopment.e_contact.ui.presenters;

import com.adkdevelopment.e_contact.data.PrefsManager;
import com.adkdevelopment.e_contact.ui.base.BaseMvpPresenter;
import com.adkdevelopment.e_contact.ui.contract.MainContract;

import javax.inject.Inject;

/**
 * Presenter for the Main Activity
 * Created by karataev on 5/10/16.
 */
public class MainPresenter
        extends BaseMvpPresenter<MainContract.View>
        implements MainContract.Presenter {

    private final PrefsManager mPreferenceManager;

    @Inject
    public MainPresenter(PrefsManager preferenceManager) {
        mPreferenceManager = preferenceManager;
    }

    @Override
    public void loadDialog() {
        getMvpView().showDialog(mPreferenceManager.getFilterSelection());
    }

    @Override
    public void saveDialog(Integer[] selection) {
        mPreferenceManager.saveFilterSelection(selection);
    }
}
