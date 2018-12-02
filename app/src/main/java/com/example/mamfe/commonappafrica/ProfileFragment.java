package com.example.mamfe.commonappafrica;


import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    TextView emailText;
    TextView addresstext;
    TextView nameText;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        System.out.println("______________________________________");
        changeInfo(view);
        ButterKnife.bind(this, view);
        return view;


    }
//    @OnClick (R.id.editButton) void onClick(Button v) {
//
//
//        if (v.getText().toString().equals("Edit")) {
//            v.setText("Save");
//            editText.setEnabled(true);
//        } else {
//            v.setTag(0);
//            Model.GPA = editText.getText().toString();
//            v.setText("Edit");
//            editText.setEnabled(false);
//        }
//    }

    public void changeInfo(View view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        emailText = (TextView) view.findViewById(R.id.emailAddress);
        addresstext = (TextView) view.findViewById(R.id.homeAddress);
        nameText = (TextView) view.findViewById(R.id.textView2);
        databaseReference.child("Users").orderByChild("email").equalTo(LoginActivity.userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                String email, add;
                for (DataSnapshot child: children) {
                    emailText.setText("Email Address: " + child.child("email").getValue().toString());
                    addresstext.setText("Home Address: " + child.child("address").getValue().toString());
                    nameText.setText(child.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
