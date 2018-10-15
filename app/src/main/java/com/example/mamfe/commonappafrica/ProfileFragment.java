package com.example.mamfe.commonappafrica;


import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    @BindView(R.id.gpaText)
    EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        editText.setText(Model.GPA);
        return view;
    }
    @OnClick (R.id.editButton) void onClick(Button v) {
        Fragment academicHomeProfileFragment = new AcademicProfileHome();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.frame_container, academicHomeProfileFragment);
        transaction.addToBackStack(null);
        transaction.commit();

//        if (v.getText().toString().equals("Edit")) {
//            v.setText("Save");
//            editText.setEnabled(true);
//        } else {
//            v.setTag(0);
//            Model.GPA = editText.getText().toString();
//            v.setText("Edit");
//            editText.setEnabled(false);
//        }
    }

}
