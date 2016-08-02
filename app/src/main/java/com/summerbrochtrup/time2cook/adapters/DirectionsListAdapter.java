package com.summerbrochtrup.time2cook.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.summerbrochtrup.time2cook.R;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 7/25/16.
 */
public class DirectionsListAdapter extends RecyclerView.Adapter<DirectionsListAdapter.DirectionsViewHolder> {
    private ArrayList<String> mDirections;
    private boolean mIsDeletable;

    public DirectionsListAdapter(ArrayList<String> directions, boolean isDeletable) {
        mDirections = directions;
        mIsDeletable = isDeletable;
    }

    @Override
    public DirectionsListAdapter.DirectionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout_directions, parent, false);
        DirectionsViewHolder viewHolder = new DirectionsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DirectionsListAdapter.DirectionsViewHolder holder, int position) {
        holder.bindDirection(mDirections.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDirections.size();
    }

    public void addDirection(String direction) {
        mDirections.add(direction);
    }

    public ArrayList<String> getDirections() {
        return mDirections;
    }

    public class DirectionsViewHolder extends RecyclerView.ViewHolder {
        private TextView mDirectionTextView;
        private ImageView mRemoveDirectionIcon;

        public DirectionsViewHolder(View itemView) {
            super(itemView);
            mDirectionTextView = (TextView) itemView.findViewById(R.id.directionTextView);
            mRemoveDirectionIcon = (ImageView) itemView.findViewById(R.id.deleteListItemIcon);
            if (!mIsDeletable) {
                mRemoveDirectionIcon.setVisibility(View.GONE);
            }
        }

        public void bindDirection(String direction, final int position) {
            mDirectionTextView.setText(direction);
            mRemoveDirectionIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDirections.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }
}

