package com.project.graduation.welcomeback;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kairos.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Iterator;

import static android.R.attr.name;

/**
 * Created by ayyad on 5/15/2017.
 */

public class Utilities {

    private static Kairos mKairosRef;

    private final static String KAIROS_GALLERY = "suspects";
    public static final String KAIROS_SELECTOR = "SETPOSE";
    public static final String KAIROS_MULTIPLE_FACES = "true";
    public static final String KAIROS_MIN_HEAD_SCALE = "0.125";
    public static final String KAIROS_MAX_RESULTS = "5";
    public static final String KAIROS_THRESHOLD = "0.70";
    public static final String KAIROS_API_ID = "76f8b3c5";
    public static final String KAIROS_API_KEY = "c9dcb07777b9954ff34106d333f9ce29";

    public static void enrollImageToKairosFoundGallery(Context context, String photoUrl, String name) {
        try {
                    mKairosRef = new Kairos();
                    mKairosRef.setAuthentication(context, KAIROS_API_ID, KAIROS_API_KEY);
                    mKairosRef.enroll(photoUrl, name, KAIROS_GALLERY, KAIROS_SELECTOR,
                    KAIROS_MULTIPLE_FACES, KAIROS_MIN_HEAD_SCALE, new KairosListener() {
                        @Override
                        public void onSuccess(String s) {
                            Log.d("done", s);
                        }

                        @Override
                        public void onFail(String s) {
                            Log.d("fail", s);
                        }
                    }

            );
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }





    private void recognizeInLostGallery(Context context, String photoUrl) {
        try {
            mKairosRef = new Kairos();
            mKairosRef.setAuthentication(context, KAIROS_API_ID, KAIROS_API_KEY);
            mKairosRef.recognize(photoUrl, KAIROS_GALLERY, KAIROS_SELECTOR, KAIROS_THRESHOLD, KAIROS_MIN_HEAD_SCALE, KAIROS_MAX_RESULTS, new KairosListener() {
                @Override
                public void onSuccess(String s) {
                    Log.d("done", s);

                    JSONObject rootNode = null;
                    try {
                        rootNode = new JSONObject(s);
                        JSONArray imgArray = rootNode.optJSONArray("images");
                        if (imgArray != null) {

                            JSONObject firstImgJSONObject = imgArray.optJSONObject(0);

                            JSONArray candidatesArray = firstImgJSONObject.optJSONArray("candidates");

                            if (candidatesArray != null) {
                                JSONObject firstCandidateJSONObject = candidatesArray.optJSONObject(0);

                                //get first key i.e. imageID in jsonObject
                                Iterator<String> keys = firstCandidateJSONObject.keys();
                                if (keys.hasNext()) {
                                    String imageID = (String) keys.next();
                                    //TODO: what happend in
                                }
                            } else {
                                Log.v("Face recognition","No Matching");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(String s) {
                    Log.d("rajuSR", s);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
