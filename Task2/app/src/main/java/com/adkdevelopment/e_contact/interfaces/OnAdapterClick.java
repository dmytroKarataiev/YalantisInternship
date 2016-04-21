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

package com.adkdevelopment.e_contact.interfaces;

import android.util.Pair;
import android.view.View;

import com.adkdevelopment.e_contact.remote.TaskItem;

/**
 * Interface to connect adapters and a fragment
 * Created by karataev on 4/20/16.
 */
public interface OnAdapterClick {

    /**
     * Callback to start activity on devices with API >= 21 with shared transitions
     * @param item to pass to another activity
     * @param pair details of a shared transition (View, String with a shared transition name)
     */
    void onTaskClickTransition(TaskItem item, Pair<View, String> pair);

    /**
     * Callback to start an activity
     * @param item to pass to another activity
     */
    void onTaskClick(TaskItem item);

}
