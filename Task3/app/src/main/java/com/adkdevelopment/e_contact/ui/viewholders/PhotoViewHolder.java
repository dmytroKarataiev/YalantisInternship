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

package com.adkdevelopment.e_contact.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.data.local.PhotoRealm;
import com.adkdevelopment.e_contact.data.local.ProfilePhotosRealm;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ViewHolder for images in a DetailView
 * Created by karataev on 5/11/16.
 */
public class PhotoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.task_image)
    ImageView mTaskImage;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public PhotoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(Object item) {

        String url;
        if (item instanceof PhotoRealm) {
            url = ((PhotoRealm) item).getImageUrl();
        } else {
            url = ((ProfilePhotosRealm) item).getUrl();
        }

        Picasso.with(itemView.getContext())
                .load(url)
                // to prevent OOM we scale images down
                .resize(PhotoRealm.WIDTH_SCALE, PhotoRealm.HEIGHT_SCALE)
                .onlyScaleDown()
                .centerInside()
                .error(R.drawable.image_placeholder)
                .into(mTaskImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
        mTaskImage.setContentDescription(itemView.getContext()
                .getString(R.string.task_image_text));
    }

}
