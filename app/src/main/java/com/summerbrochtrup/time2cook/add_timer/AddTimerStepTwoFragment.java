package com.summerbrochtrup.time2cook.add_timer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.adapters.DirectionsListAdapter;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 7/28/16.
 */
public class AddTimerStepTwoFragment extends Fragment implements View.OnClickListener {
    private EditText mAddDirectionEditText;
    private ImageView mAddDirectionButton, mBackButton;
    private RecyclerView mRecylerView;
    private DirectionsListAdapter mAdapter;
    private ArrayList<String> mDirections = new ArrayList<>();

    public static AddTimerStepTwoFragment newInstance() {
        AddTimerStepTwoFragment fragmentFirst = new AddTimerStepTwoFragment();
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_timer_step_two, container, false);
        mAddDirectionEditText = (EditText) view.findViewById(R.id.directionEditText);
        mAddDirectionButton = (ImageView) view.findViewById(R.id.addDirectionButton);
        mBackButton = (ImageView) view.findViewById(R.id.backStepButton);
        mRecylerView = (RecyclerView) view.findViewById(R.id.directionsRecyclerView);
        mAdapter = new DirectionsListAdapter(mDirections, true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(layoutManager);
        mRecylerView.setAdapter(mAdapter);
        mAddDirectionButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addDirectionButton:
                String direction = mAddDirectionEditText.getText().toString();
                mAddDirectionEditText.setText("");
                mAdapter.addDirection(direction);
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
                break;
            case R.id.backStepButton:
                ((AddTimerActivity) getActivity()).setCurrentItem (0, true);
                break;
        }
    }

    public String getDirections() {
        String directions = TextUtils.join(". ", mAdapter.getDirections());
        return directions;
    }
}
