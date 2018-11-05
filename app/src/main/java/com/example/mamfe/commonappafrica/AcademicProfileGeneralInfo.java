package com.example.mamfe.commonappafrica;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AcademicProfileGeneralInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcademicProfileGeneralInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcademicProfileGeneralInfo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference database;


    private OnFragmentInteractionListener mListener;

    public AcademicProfileGeneralInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcademicProfileGeneralInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static AcademicProfileGeneralInfo newInstance(String param1, String param2) {
        AcademicProfileGeneralInfo fragment = new AcademicProfileGeneralInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_academic_profile_general_info, container, false);

        this.mAuth = FirebaseAuth.getInstance();
        this.user = mAuth.getCurrentUser();
        this.database = FirebaseDatabase.getInstance().getReference();

        //final String uid = user.getUid();

        //Bind the save listener to button
        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update firebase
                updateFirebaseFields();

                Toast feedback = Toast.makeText(view.getContext(), "Information Updated!", Toast.LENGTH_SHORT);
                feedback.show();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        populateFields(getView());
    }

    private void updateFirebaseFields() {
        String uid = this.user.getUid();
        DatabaseReference appDetails = database.child("UserProfiles").child(uid).child("generalInfo");

        appDetails.child("firstName").setValue(((EditText) getView().findViewById(R.id.firstNameEdit)).getText().toString());
        appDetails.child("lastName").setValue(((EditText) getView().findViewById(R.id.lastNameEdit)).getText().toString());
        appDetails.child("mailingAddress").setValue(((EditText) getView().findViewById(R.id.mailingAddressEdit)).getText().toString());
        appDetails.child("sex").setValue(((EditText) getView().findViewById(R.id.sexEdit)).getText().toString());
        appDetails.child("dateOfBirth").setValue(((EditText) getView().findViewById(R.id.dateOfBirthEdit)).getText().toString());
        appDetails.child("email").setValue(((EditText) getView().findViewById(R.id.emailEdit)).getText().toString());
    }

    private void populateFields(final View view) {
        //TODO: Populate the fields
        String uid = this.user.getUid();
        database.child("UserProfiles").child(uid).child("generalInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("firstName").getValue() != null) {
                    EditText t = view.findViewById(R.id.firstNameEdit);
                    t.setText((String) dataSnapshot.child("firstName").getValue());
                }

                if(dataSnapshot.child("lastName").getValue() != null) {
                    EditText t = view.findViewById(R.id.lastNameEdit);
                    t.setText((String) dataSnapshot.child("lastName").getValue());
                }

                if(dataSnapshot.child("mailingAddress").getValue() != null) {
                    EditText t = view.findViewById(R.id.mailingAddressEdit);
                    t.setText((String) dataSnapshot.child("mailingAddress").getValue());
                }

                if(dataSnapshot.child("sex").getValue() != null) {
                    EditText t = view.findViewById(R.id.sexEdit);
                    t.setText((String) dataSnapshot.child("sex").getValue());
                }

                if(dataSnapshot.child("dateOfBirth").getValue() != null) {
                    EditText t = view.findViewById(R.id.dateOfBirthEdit);
                    t.setText((String) dataSnapshot.child("dateOfBirth").getValue());
                }

                if(dataSnapshot.child("email").getValue() != null) {
                    EditText t = view.findViewById(R.id.emailEdit);
                    t.setText((String) dataSnapshot.child("email").getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO: Doing nothing right now
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
