package com.summerbrochtrup.time2cook.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.summerbrochtrup.time2cook.R;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 7/25/16.
 */
public class DirectionsListAdapter extends RecyclerView.Adapter<DirectionsListAdapter.DirectionsViewHolder> {
    private ArrayList<String> mDirections;

    public DirectionsListAdapter(ArrayList<String> directions) {
        mDirections = directions;
    }

    @Override
    public DirectionsListAdapter.DirectionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout_directions, parent, false);
        DirectionsViewHolder viewHolder = new DirectionsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DirectionsListAdapter.DirectionsViewHolder holder, int position) {
        holder.bindDirection(mDirections.get(position));
    }

    @Override
    public int getItemCount() {
        return mDirections.size();
    }



    public class DirectionsViewHolder extends RecyclerView.ViewHolder {
        private TextView mDirectionTextView;

        public DirectionsViewHolder(View itemView) {
            super(itemView);
            mDirectionTextView = (TextView) itemView.findViewById(R.id.directionTextView);
        }

        public void bindDirection(String direction) {
            mDirectionTextView.setText(direction);
        }
    }
}

