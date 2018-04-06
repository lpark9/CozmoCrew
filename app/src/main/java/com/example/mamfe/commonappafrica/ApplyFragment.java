package com.example.mamfe.commonappafrica;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApplyFragment extends Fragment {


    @BindView(R.id.collegeName)
    TextView collegeName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply, container, false);
        ButterKnife.bind(this, view);
        collegeName.setText(Model.selected);
        return view;
    }
    @OnClick (R.id.add) void onClick() {
        Toast.makeText(getContext(), Model.selected + " had been added", Toast.LENGTH_SHORT).show();
        Model.myColleges.add(Model.selected);
    }
    @OnClick (R.id.apply) void onClick2() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new EssayFragment();
        if (!Model.myColleges.contains(Model.selected))
        Model.myColleges.add(Model.selected);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myFragment).addToBackStack(null).commit();
    }

}
