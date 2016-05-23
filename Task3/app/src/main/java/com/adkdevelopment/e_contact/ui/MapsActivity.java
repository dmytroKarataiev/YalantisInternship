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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.data.local.TaskRealm;
import com.adkdevelopment.e_contact.ui.base.BaseActivity;
import com.adkdevelopment.e_contact.ui.contract.MapsContract;
import com.adkdevelopment.e_contact.ui.presenters.MapsPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karataev on 5/21/16.
 */
public class MapsActivity extends BaseActivity implements OnMapReadyCallback, MapsContract.View {

    @Inject MapsPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private GoogleMap mGoogleMap;
    public static final int ZOOM_DEFAULT = 12;

    // data to start detail activity on info window click
    private HashMap<String, Intent> mMarkers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);

        mPresenter.attachView(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Manipulates the map once available.
     * Here we just put camera to Yalantis office position
     * But we can call ContentProvider and populate map with markers...
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mPresenter.loadMarkers(getIntent());
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

    @Override
    public void showMarker(Intent intent) {
        TaskRealm taskRealm = intent.getParcelableExtra(TaskRealm.TASK_EXTRA);

        if (taskRealm != null) {
            LatLng position = new LatLng(taskRealm.getLatitude(), taskRealm.getLongitude());

            mGoogleMap.addMarker(new MarkerOptions().position(position)
                    .title(taskRealm.getTitle())
                    .snippet(taskRealm.getDescription()));

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(position)
                    .zoom(ZOOM_DEFAULT)
                    .build();

            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

    @Override
    public void showMarkers(List<TaskRealm> realmList) {
        for (TaskRealm each : realmList) {
            LatLng latLng = new LatLng(each.getLatitude(), each.getLongitude());
            String id = mGoogleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .snippet(each.getDescription())
                    .title(each.getTitle()))
                    .getId();

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(TaskRealm.TASK_EXTRA, each);
            intent.putExtra(TaskRealm.TASK_EXTRA_TITLE, String.valueOf(each.getId()));
            mMarkers.put(id, intent);
        }

        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(mMarkers.get(marker.getId()));
            }
        });

        if (realmList.size() > 0) {
            LatLng position = new LatLng(realmList.get(0).getLatitude(),
                    realmList.get(0).getLongitude());

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(position)
                    .zoom(ZOOM_DEFAULT)
                    .build();

            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
