package com.project.graduation.welcomeback.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.graduation.welcomeback.Home.Data.Report;
import com.project.graduation.welcomeback.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by WinDows8 on 6/1/2017.
 */

public class UserGalleriesAdapter extends RecyclerView.Adapter<UserGalleriesAdapter.UserGalleriesItemViewHolder> {

    private ArrayList<Report> mReports;
    private Context mContext;

    public UserGalleriesAdapter(Context context){
        mContext = context;
        mReports = new ArrayList<>();
    }

    @Override
    public UserGalleriesItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForPhoto = R.layout.user_gallery_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForPhoto, viewGroup, false);

        UserGalleriesItemViewHolder viewHolder = new UserGalleriesItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserGalleriesItemViewHolder viewholder, int position) {
        Report report = mReports.get(position);

        Picasso.with(mContext)
                .load(report.getPhoto())
                .resize(360,480)
                .into(viewholder.mPhotoView);

        viewholder.mNameView.setText(mContext.getString(R.string.user_gallery_item_name)+
                " " + report.getName());

        viewholder.mAgeView.setText(mContext.getString(R.string.user_gallery_item_age) +
                " " + report.getAge());

        viewholder.mGenderView.setText(mContext.getString(R.string.user_gallery_item_gender) +
                " " + report.getGender());
    }

    @Override
    public int getItemCount() {
        return mReports.size();
    }

    public void addReport(Report report){
        mReports.add(report);
        notifyDataSetChanged();
    }

    public static class UserGalleriesItemViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mPhotoView;
        public final TextView mNameView ,mAgeView, mGenderView;

        public UserGalleriesItemViewHolder(View view) {
            super(view);
            mPhotoView = (ImageView) view.findViewById(R.id.user_gallery_photo_Image_view);
            mNameView = (TextView) view.findViewById(R.id.user_gallery_name_text_view);
            mAgeView = (TextView) view.findViewById(R.id.user_gallery_age_text_view);
            mGenderView = (TextView) view.findViewById(R.id.user_gallery_gender_text_view);
        }
    }


}
