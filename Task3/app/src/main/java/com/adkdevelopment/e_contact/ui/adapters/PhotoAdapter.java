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

package com.adkdevelopment.e_contact.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.data.local.PhotoRealm;
import com.adkdevelopment.e_contact.data.local.ProfilePhotosRealm;
import com.adkdevelopment.e_contact.interfaces.ItemClickListener;
import com.adkdevelopment.e_contact.ui.viewholders.PhotoViewHolder;

import java.util.List;

import javax.inject.Inject;

/**
 * Photo Adapter for the Details and Profile Activities.
 * Shows photos and resizes them to improve performance.
 * Created by karataev on 5/13/16.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private List<PhotoRealm> mPhotos;
    private List<ProfilePhotosRealm> mProfilePhotos;
    private ItemClickListener<Integer, View> mListener;

    @Inject
    PhotoAdapter() {
    }

    public void setPhotos(List<PhotoRealm> photos,
                          ItemClickListener<Integer, View> listener) {
        mPhotos = photos;
        mListener = listener;
    }

    public void setPhotos(List<ProfilePhotosRealm> photos) {
        mProfilePhotos = photos;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_detail_image, parent, false);

        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder viewHolder, int position) {
        final int pos = viewHolder.getAdapterPosition();

        if (mPhotos != null) {
            viewHolder.setData(mPhotos.get(position));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClicked(pos,
                                viewHolder.itemView.findViewById(R.id.task_image));
                    }
                }
            });
        } else {
            viewHolder.setData(mProfilePhotos.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mPhotos == null ? (mProfilePhotos == null) ? 0 : mProfilePhotos.size() : mPhotos.size();
    }
}
