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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.adkdevelopment.e_contact.remote.TaskItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Launches the window with detailed information about a task
 * If there is no outside data - shows "dummy data" from resources
 */
public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);

        // Initialize a custom Toolbar
        setSupportActionBar(mToolbar);

        // Add back button to the actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // populate the item with data
        TaskItem taskItem = getData();

        // Set the title bar with Task title
        String actionbarTitle = getString(R.string.task_actionbar_title) + " " + taskItem.getTitle();
        getSupportActionBar().setTitle(actionbarTitle);

    }

    /**
     * Gets TaskItem from the intent or creates it from the internal data
     * @return TaskItem object with the data
     */
    private TaskItem getData() {
        // If launched from the List Activity - get data from the intent
        TaskItem taskItem;

        if (getIntent().hasExtra(TaskItem.TASKITEM)) {
            taskItem = getIntent().getParcelableExtra(TaskItem.TASKITEM);
        } else {
            // Example data
            taskItem = new TaskItem();
            taskItem.setTitle(getString(R.string.task_title_example));
            taskItem.setResponsible(getString(R.string.task_owner));
            taskItem.setStatus(1);
        }

        return taskItem;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // copy the behavior of the hardware back button
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
