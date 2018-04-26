package com.application.mxm.soundtracks.tracklist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.mxm.soundtracks.R;
import com.application.mxm.soundtracks.data.model.Track;
import com.bumptech.glide.Glide;

import java.util.List;


public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {
    private List<?> items;
    private OnTrackItemClickListener listener;

    public TrackListAdapter(List<?> devices, OnTrackItemClickListener lst) {
        items = devices;
        listener = lst;
    }

    @NonNull
    @Override
    public TrackListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.track_item, viewGroup, false);
        return new TrackListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        Track stargazer = (Track) items.get(position);
//        setAvatar(vh, stargazer.getAvatarUrl());
        vh.usernameTextview.setText(stargazer.getTrackName());
        if (listener != null)
            vh.itemView.setOnClickListener(view -> listener.onTrackItemClick(view, position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    /**
     * set avatar on user - Glide lib to handle pics
     * @param vh
     * @param avatarUrl
     */
    private void setAvatar(ViewHolder vh, String avatarUrl) {
        if (avatarUrl == null) {
            Glide.clear(vh.avatarImageView);
            return;
        }

        Glide.with(vh.itemView.getContext())
                .load(avatarUrl)
                .placeholder(R.mipmap.github_placeholder)
                .into(vh.avatarImageView);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView usernameTextview;
        private final ImageView avatarImageView;

        /**
         *
         * @param view
         */
        private ViewHolder(View view) {
            super(view);
            usernameTextview =  view.findViewById(R.id.usernameTextViewId);
            avatarImageView =  view.findViewById(R.id.avatarImageViewId);
        }


    }

    /**
     *
     */
    interface OnTrackItemClickListener {
        void onTrackItemClick(View view, int position);
    }

}
