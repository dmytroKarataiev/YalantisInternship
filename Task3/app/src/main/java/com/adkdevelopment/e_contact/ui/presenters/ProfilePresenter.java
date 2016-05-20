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

import android.os.Bundle;
import android.util.Log;

import com.adkdevelopment.e_contact.data.DataManager;
import com.adkdevelopment.e_contact.data.local.ProfilePhotosRealm;
import com.adkdevelopment.e_contact.data.local.ProfileRealm;
import com.adkdevelopment.e_contact.ui.base.BaseMvpPresenter;
import com.adkdevelopment.e_contact.ui.contract.ProfileContract;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.realm.RealmList;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by karataev on 5/10/16.
 */
public class ProfilePresenter
        extends BaseMvpPresenter<ProfileContract.View> implements ProfileContract.Presenter {

    private static final String TAG = ProfilePresenter.class.getSimpleName();

    private final DataManager mDataManager;
    private AccessToken mAccessToken;
    private CompositeSubscription mSubscription;

    @Inject
    public ProfilePresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void getProfile() {
        checkViewAttached();
        //mAccessToken = mDataManager.getAccessToken();
        //Log.d(TAG, mAccessToken.getToken());
        // get data from the database
        mSubscription.add(mDataManager.getProfile().subscribe(new Subscriber<ProfileRealm>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error: " + e);
            }

            @Override
            public void onNext(ProfileRealm profileRealm) {
                if (profileRealm == null) {
                    // load data from the internet
                    mAccessToken = mDataManager.getAccessToken();
                    getUser();
                } else {
                    getMvpView().showData(profileRealm);
                }
            }
        }));

    }

    // TODO: 5/20/16 refactor add GSON
    private void getUser() {
        // user, parse response
        GraphRequest request = GraphRequest.newMeRequest(
                mAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        ProfileRealm profileRealm = new ProfileRealm();
                        try {
                            profileRealm.setId(object.getString("id"));
                            profileRealm.setName(object.getString("name"));
                            profileRealm.setLink(object.getString("link"));
                        } catch (JSONException e) {
                            Log.e(TAG, "Error: " + e);
                        }

                        getPhotos(profileRealm);

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);

        request.executeAsync();
    }

    // TODO: 5/20/16 refactor add GSON
    private void getPhotos(final ProfileRealm profileRealm) {
        GraphRequest request = new GraphRequest(
                mAccessToken,
                "/" + profileRealm.getId() + "/photos",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        RealmList<ProfilePhotosRealm> photosRealms = new RealmList<>();

                        try {
                            JSONArray photos = response.getJSONObject().getJSONArray("data");
                            for (int i = 0, n = photos.length(); i < n; i++) {
                                ProfilePhotosRealm photoRealm = new ProfilePhotosRealm();

                                String id = photos.getJSONObject(i).getString("id");

                                if (photos.getJSONObject(i).has("name")) {
                                    String description = photos.getJSONObject(i).getString("name");
                                    photoRealm.setDescription(description);
                                }

                                if (photos.getJSONObject(i).has("created_time")) {
                                    String createdTime = photos.getJSONObject(i).getString("created_time");
                                    photoRealm.setCreatedTime(createdTime);
                                }

                                if (photos.getJSONObject(i).has("picture")) {
                                    String link = photos.getJSONObject(i).getJSONArray("images").getJSONObject(0).getString("source");
                                    photoRealm.setUrl(link);
                                }

                                photoRealm.setId(id);
                                photosRealms.add(photoRealm);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Error: " + e);
                        }

                        profileRealm.setPhotos(photosRealms);

                        getMvpView().showData(profileRealm);

                        // add to the database
                        mSubscription.add(mDataManager.saveProfile(profileRealm).subscribe(new Subscriber<Boolean>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "onCompleted: ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Error: " + e);
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                Log.d(TAG, "onNext: " + aBoolean);
                            }
                        }));
                    }
                }
        );

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,url,picture,images");
        request.setParameters(parameters);

        request.executeAsync();

    }
}
