package com.example.mamfe.commonappafrica;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApplyFragment extends Fragment {

    TextView websiteName, rankingNum;
    @BindView(R.id.collegeName)
    TextView collegeName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply, container, false);
        ButterKnife.bind(this, view);

        collegeName.setText(Model.selected);
        websiteName = view.findViewById(R.id.website);
        websiteName.setText(Model.websiteSelected);
        rankingNum = view.findViewById(R.id.ranking);
        rankingNum.setText(Model.rankingSelected);
        return view;
    }
    @OnClick (R.id.add) void onClick() {
        CustomDialog dialog = new CustomDialog(getContext());
        dialog.show();
        dialog.setText(Model.selected + " had been added to your 'My Colleges' list");
        //Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
        Model.myColleges.add(Model.selected);
    }
    @OnClick (R.id.apply) void onClick2() {
        AppCompatActivity activity = (AppCompatActivity) getContext();

        if (!Model.myColleges.contains(Model.selected)) {
            Model.myColleges.add(Model.selected);
        }

        Fragment essayFragment = new EssayFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, essayFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}

