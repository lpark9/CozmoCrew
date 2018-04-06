package com.example.mamfe.commonappafrica;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class EssayFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_essay, container, false);
        ButterKnife.bind(this, view);
        new MaterialDialog.Builder(getContext())
                .title("Note!")
                .content("Don't forget to save your progress regularly!")
                .positiveText("OK").onAny(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        })
                .show();
        return view;
    }

    @OnClick(R.id.save)
    void onClick() {
        Toast.makeText(getContext(), "Your application had been saved", Toast.LENGTH_SHORT).show();
        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new CollegeFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myFragment).addToBackStack(null).commit();
    }

    @OnClick(R.id.submit)
    void onClick2() {
        new MaterialDialog.Builder(getContext())
                .title("Note!")
                .content("Are you sure you want to submit?")
                .positiveText("Yes").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment myFragment = new SearchFragment();
                Toast.makeText(getContext(), "Your application had been submmited", Toast.LENGTH_SHORT).show();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myFragment).addToBackStack(null).commit();
            }
        }).negativeText("No").onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        })
                .show();
    }


}
