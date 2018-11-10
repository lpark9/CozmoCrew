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
 * {@link AcademicProfilePersonalHealth.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcademicProfilePersonalHealth#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcademicProfilePersonalHealth extends Fragment {
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

    public AcademicProfilePersonalHealth() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcademicProfilePersonalHealth.
     */
    // TODO: Rename and change types and number of parameters
    public static AcademicProfilePersonalHealth newInstance(String param1, String param2) {
        AcademicProfilePersonalHealth fragment = new AcademicProfilePersonalHealth();
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
        View view = inflater.inflate(R.layout.fragment_academic_profile_personal_health, container, false);

        this.mAuth = FirebaseAuth.getInstance();
        this.user = mAuth.getCurrentUser();
        this.database = FirebaseDatabase.getInstance().getReference();

        //final String uid = user.getUid();

        //Bind the save listener to button
        Button saveButton = view.findViewById(R.id.saveButton);
        Button nextButton = view.findViewById(R.id.next_button);
        Button prevButton = view.findViewById(R.id.prev_button);

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
                Fragment workExperience = new AcademicProfileWorkExperience();
                workExperience.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, workExperience);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isApplying", isApplying);
                Fragment generalInfo = new AcademicProfileGeneralInfo();
                generalInfo.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, generalInfo);
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
        DatabaseReference appDetails = database.child("UserProfiles").child(uid).child("personalHealth");

        appDetails.child("doYouUseDrugs").setValue(((EditText) getView().findViewById(R.id.doYouUseDrugsEdit)).getText().toString());
        appDetails.child("haveYouUsedDrugs").setValue(((EditText) getView().findViewById(R.id.haveYouEverUsedEdit)).getText().toString());
        appDetails.child("doYouDrink").setValue(((EditText) getView().findViewById(R.id.doYouDrinkEdit)).getText().toString());
        appDetails.child("haveYouDrank").setValue(((EditText) getView().findViewById(R.id.haveYouEverDrankEdit)).getText().toString());
        appDetails.child("doYouSmoke").setValue(((EditText) getView().findViewById(R.id.doYouSmokeEdit)).getText().toString());
        appDetails.child("haveYouSmoked").setValue(((EditText) getView().findViewById(R.id.haveYouEverSmokedEdit)).getText().toString());
    }

    private void populateFields(final View view) {
        //TODO: Populate the fields
        String uid = this.user.getUid();
        database.child("UserProfiles").child(uid).child("personalHealth").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("doYouUseDrugs").getValue() != null) {
                    EditText t = view.findViewById(R.id.doYouUseDrugsEdit);
                    t.setText((String) dataSnapshot.child("doYouUseDrugs").getValue());
                }

                if(dataSnapshot.child("haveYouUsedDrugs").getValue() != null) {
                    EditText t = view.findViewById(R.id.haveYouEverUsedEdit);
                    t.setText((String) dataSnapshot.child("haveYouUsedDrugs").getValue());
                }

                if(dataSnapshot.child("doYouDrink").getValue() != null) {
                    EditText t = view.findViewById(R.id.doYouDrinkEdit);
                    t.setText((String) dataSnapshot.child("doYouDrink").getValue());
                }

                if(dataSnapshot.child("haveYouDrank").getValue() != null) {
                    EditText t = view.findViewById(R.id.haveYouEverDrankEdit);
                    t.setText((String) dataSnapshot.child("haveYouDrank").getValue());
                }

                if(dataSnapshot.child("doYouSmoke").getValue() != null) {
                    EditText t = view.findViewById(R.id.doYouSmokeEdit);
                    t.setText((String) dataSnapshot.child("doYouSmoke").getValue());
                }

                if(dataSnapshot.child("haveYouSmoked").getValue() != null) {
                    EditText t = view.findViewById(R.id.haveYouEverSmokedEdit);
                    t.setText((String) dataSnapshot.child("haveYouSmoked").getValue());
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
