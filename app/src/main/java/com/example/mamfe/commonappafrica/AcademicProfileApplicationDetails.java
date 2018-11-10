package com.example.mamfe.commonappafrica;

import android.app.FragmentTransaction;
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
 * {@link AcademicProfileApplicationDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcademicProfileApplicationDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcademicProfileApplicationDetails extends Fragment {
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
    private boolean isApplying;

    public AcademicProfileApplicationDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isApplying = bundle.getBoolean("isApplying");
        } else {
            isApplying = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_academic_profile_application_details, container, false);

        this.mAuth = FirebaseAuth.getInstance();
        this.user = mAuth.getCurrentUser();
        this.database = FirebaseDatabase.getInstance().getReference();

        //final String uid = user.getUid();

        //Bind the save listener to button
        Button saveButton = view.findViewById(R.id.saveButton);
        Button nextButton = view.findViewById(R.id.next_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update firebase
                updateFirebaseFields();

                Toast feedback = Toast.makeText(view.getContext(), "Information Updated!", Toast.LENGTH_SHORT);
                feedback.show();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isApplying", isApplying);
                Fragment education = new AcademicProfileEducationBackground();
                education.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, education);
                transaction.addToBackStack(null);
                transaction.commit();
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
        DatabaseReference appDetails = database.child("UserProfiles").child(uid).child("applicationDetails");

        appDetails.child("degreeDesired").setValue(((EditText) getView().findViewById(R.id.degreeEdit)).getText().toString());
        appDetails.child("fieldOfStudy").setValue(((EditText) getView().findViewById(R.id.fieldOfStudyEdit)).getText().toString());
        appDetails.child("semester").setValue(((EditText) getView().findViewById(R.id.semesterEdit)).getText().toString());
        appDetails.child("year").setValue(((EditText) getView().findViewById(R.id.yearEdit)).getText().toString());
    }

    private void populateFields(final View view) {
        //TODO: Populate the fields
        String uid = this.user.getUid();
        database.child("UserProfiles").child(uid).child("applicationDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("degreeDesired").getValue() != null) {
                    EditText t = view.findViewById(R.id.degreeEdit);
                    t.setText((String) dataSnapshot.child("degreeDesired").getValue());
                }

                if(dataSnapshot.child("fieldOfStudy").getValue() != null) {
                    EditText t = view.findViewById(R.id.fieldOfStudyEdit);
                    t.setText((String) dataSnapshot.child("fieldOfStudy").getValue());
                }

                if(dataSnapshot.child("semester").getValue() != null) {
                    EditText t = view.findViewById(R.id.semesterEdit);
                    t.setText((String) dataSnapshot.child("semester").getValue());
                }

                if(dataSnapshot.child("year").getValue() != null) {
                    EditText t = view.findViewById(R.id.yearEdit);
                    t.setText((String) dataSnapshot.child("year").getValue());
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
