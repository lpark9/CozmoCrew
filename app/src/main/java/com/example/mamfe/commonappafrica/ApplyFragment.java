package com.example.mamfe.commonappafrica;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

    TextView websiteName, rankingNum, acceptance, tuition_num, total_num, duedate;
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

        acceptance = view.findViewById(R.id.acceptance_rate);
        acceptance.setText(Model.rateSelected);
        tuition_num = view.findViewById(R.id.tuition_fees);
        tuition_num.setText(Model.tuitionSelected);
        total_num = view.findViewById(R.id.enrollment_num);
        total_num.setText(Model.totalSelected);
        duedate = view.findViewById(R.id.deadline);
        duedate.setText(Model.deadlineSelected);

        //TODO: Decide how to format the button
        if (Model.myColleges.contains(Model.selected)) {
            Button add = view.findViewById(R.id.add);
            
            add.setBackgroundResource(R.drawable.remove_college_button);
            add.setTextColor(Color.BLACK);
            add.setText("Remove From My Colleges");
        }

        return view;
    }
    @OnClick (R.id.add) void onClick() {
        //TODO: Change the click handler here
        CustomDialog dialog = new CustomDialog(getContext());
        if (Model.myColleges.contains(Model.selected)) {
            dialog.show();
            dialog.setText(Model.selected + " has been removed from your 'My Colleges' list");
            Model.myColleges.remove(Model.selected);

            Button add = getActivity().findViewById(R.id.add);
            add.setBackgroundResource(R.drawable.login_button);
            add.setTextColor(Color.WHITE);
            add.setText("Add to My Colleges");

            //Change the style back
        } else {
            dialog.show();
            dialog.setText(Model.selected + " had been added to your 'My Colleges' list");
            Model.myColleges.add(Model.selected);

            Button add = getActivity().findViewById(R.id.add);
            add.setBackgroundResource(R.drawable.remove_college_button);
            add.setTextColor(Color.BLACK);
            add.setText("Remove From My Colleges");
        }

        //Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
    }
    @OnClick (R.id.apply) void onClick2() {
        AppCompatActivity activity = (AppCompatActivity) getContext();

        if (!Model.myColleges.contains(Model.selected)) {
            Model.myColleges.add(Model.selected);
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("Do you want to check your profile before submitting?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isApplying", true);
                Fragment details = new AcademicProfileApplicationDetails();
                details.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, details);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getActivity(), PaymentActivity.class));
            }
        });


        AlertDialog alert = alertDialog.create();
        alert.show();

//
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_container, essayFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();

    }

}

