package com.project.graduation.welcomeback.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.graduation.welcomeback.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmed on 5/27/2017.
 */

public class GalleriesAdapter extends RecyclerView.Adapter<GalleriesAdapter.PhotoViewHolder> {

    private ArrayList<String> photosUrl;

    private Context context;

    public GalleriesAdapter(Context cont){
        context = cont;
        photosUrl = new ArrayList<>();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mPhotoView;

        public PhotoViewHolder(View view) {
            super(view);
            mPhotoView = (ImageView) view.findViewById(R.id.gallery_image_view);

        }

    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForPhoto = R.layout.gallery_photo;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForPhoto, viewGroup, false);

        PhotoViewHolder photoViewHolder = new PhotoViewHolder(view);

        return photoViewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder viewholder, int position) {

        Picasso.with(context)
                .load(photosUrl.get(position))
                .resize(480,640)
                .into(viewholder.mPhotoView);
    }

    @Override
    public int getItemCount() {
        return photosUrl.size();
    }

    public void addPhotosUrl(String photo){
        photosUrl.add(photo);
        notifyDataSetChanged();
    }

    public void clear(){
        photosUrl.clear();
    }

}
