package com.example.mamfe.commonappafrica;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Locale;


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

        RadioGroup doYouUseDrugsRadioGroup = getView().findViewById(R.id.doYouUseDrugsRadio);
        int doYouUseDrugsCheckedButtonId = doYouUseDrugsRadioGroup.getCheckedRadioButtonId();

        if(doYouUseDrugsCheckedButtonId != -1) {
            RadioButton doYouUseDrugsCheckedButton = (RadioButton) doYouUseDrugsRadioGroup.findViewById(doYouUseDrugsCheckedButtonId);
            appDetails.child("doYouUseDrugs").setValue(doYouUseDrugsCheckedButton.getText().toString());
        } else {
            appDetails.child("doYouUseDrugs").setValue(null);
        }

        RadioGroup haveYouEverUsedDrugsRadioGroup = getView().findViewById(R.id.haveYouEverUsedRadio);
        int haveYouEverUsedDrugsCheckedButtonId = haveYouEverUsedDrugsRadioGroup.getCheckedRadioButtonId();

        if(haveYouEverUsedDrugsCheckedButtonId != -1) {
            RadioButton haveYouEverUsedDrugsCheckedButton = (RadioButton) haveYouEverUsedDrugsRadioGroup.findViewById(haveYouEverUsedDrugsCheckedButtonId);
            appDetails.child("haveYouEverUsedDrugs").setValue(haveYouEverUsedDrugsCheckedButton.getText().toString());
        } else {
            appDetails.child("haveYouEverUsedDrugs").setValue(null);
        }

        RadioGroup doYouDrinkRadioGroup = getView().findViewById(R.id.doYouDrinkRadio);
        int doYouDrinkCheckedButtonId = doYouDrinkRadioGroup.getCheckedRadioButtonId();

        if(doYouDrinkCheckedButtonId != -1) {
            RadioButton doYouDrinkCheckedButton = (RadioButton) doYouDrinkRadioGroup.findViewById(doYouDrinkCheckedButtonId);
            appDetails.child("doYouDrink").setValue(doYouDrinkCheckedButton.getText().toString());
        } else {
            appDetails.child("doYouDrink").setValue(null);
        }

        RadioGroup haveYouDrankRadioGroup = getView().findViewById(R.id.haveYouEverDrankRadio);
        int haveYouDrankCheckedButtonId = haveYouDrankRadioGroup.getCheckedRadioButtonId();

        if(haveYouDrankCheckedButtonId != -1) {
            RadioButton doYouUseDrugsCheckedButton = (RadioButton) haveYouDrankRadioGroup.findViewById(haveYouDrankCheckedButtonId);
            appDetails.child("haveYouDrank").setValue(doYouUseDrugsCheckedButton.getText().toString());
        } else {
            appDetails.child("haveYouDrank").setValue(null);
        }

        RadioGroup doYouSmokeRadioGroup = getView().findViewById(R.id.doYouSmokeRadio);
        int doYouSmokeCheckedButtonId = doYouSmokeRadioGroup.getCheckedRadioButtonId();

        if(doYouSmokeCheckedButtonId != -1) {
            RadioButton doYouSmokeCheckedButton = (RadioButton) doYouSmokeRadioGroup.findViewById(doYouSmokeCheckedButtonId);
            appDetails.child("doYouSmoke").setValue(doYouSmokeCheckedButton.getText().toString());
        } else {
            appDetails.child("doYouSmoke").setValue(null);
        }

        RadioGroup haveYouEverSmokedRadioGroup = getView().findViewById(R.id.haveYouEverSmokedRadio);
        int haveYouEverSmokedCheckedButtonId = haveYouEverSmokedRadioGroup.getCheckedRadioButtonId();

        if(haveYouEverSmokedCheckedButtonId != -1) {
            RadioButton haveYouEverSmokedCheckedButton = (RadioButton) haveYouEverSmokedRadioGroup.findViewById(haveYouEverSmokedCheckedButtonId);
            appDetails.child("haveYouEverSmoked").setValue(haveYouEverSmokedCheckedButton.getText().toString());
        } else {
            appDetails.child("haveYouEverSmoked").setValue(null);
        }

    }

    private void populateFields(final View view) {
        //TODO: Populate the fields
        String uid = this.user.getUid();
        database.child("UserProfiles").child(uid).child("personalHealth").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RadioGroup doYouUseDrugsRadioGroup = view.findViewById(R.id.doYouUseDrugsRadio);
                RadioGroup haveYouEverUsedDrugsRadioGroup = view.findViewById(R.id.haveYouEverUsedRadio);
                RadioGroup doYouDrinkRadioGroup = view.findViewById(R.id.doYouDrinkRadio);
                RadioGroup haveYouDrankRadioGroup = view.findViewById(R.id.haveYouEverDrankRadio);
                RadioGroup doYouSmokeRadioGroup = view.findViewById(R.id.doYouSmokeRadio);
                RadioGroup haveYouEverSmokedRadioGroup = view.findViewById(R.id.haveYouEverSmokedRadio);

                if(dataSnapshot.child("doYouUseDrugs").getValue() != null) {
                    if(((String) dataSnapshot.child("doYouUseDrugs").getValue()).equals("Yes")) {
                        doYouUseDrugsRadioGroup.check(R.id.doYouUseDrugsRadioYes);
                    } else {
                        doYouUseDrugsRadioGroup.check(R.id.doYouUseDrugsRadioNo);
                    }
                }

                if(dataSnapshot.child("haveYouEverUsedDrugs").getValue() != null) {
                    if(((String) dataSnapshot.child("haveYouEverUsedDrugs").getValue()).equals("Yes")) {
                        haveYouEverUsedDrugsRadioGroup.check(R.id.haveYouEverUsedRadioYes);
                    } else {
                        haveYouEverUsedDrugsRadioGroup.check(R.id.haveYouEverUsedRadioNo);
                    }
                }

                if(dataSnapshot.child("doYouDrink").getValue() != null) {
                    if(((String) dataSnapshot.child("doYouDrink").getValue()).equals("Yes")) {
                        doYouDrinkRadioGroup.check(R.id.doYouDrinkRadioYes);
                    } else {
                        doYouDrinkRadioGroup.check(R.id.doYouDrinkRadioNo);
                    }
                }

                if(dataSnapshot.child("haveYouDrank").getValue() != null) {
                    if(((String) dataSnapshot.child("haveYouDrank").getValue()).equals("Yes")) {
                        haveYouDrankRadioGroup.check(R.id.haveYouEverDrankRadioYes);
                    } else {
                        haveYouDrankRadioGroup.check(R.id.haveYouEverDrankRadioNo);
                    }
                }

                if(dataSnapshot.child("doYouSmoke").getValue() != null) {
                    if(((String) dataSnapshot.child("doYouUseDrugs").getValue()).equals("Yes")) {
                        doYouSmokeRadioGroup.check(R.id.doYouSmokeRadioYes);
                    } else {
                        doYouSmokeRadioGroup.check(R.id.doYouSmokeRadioNo);
                    }
                }


                if(dataSnapshot.child("haveYouEverSmoked").getValue() != null) {
                    if(((String) dataSnapshot.child("haveYouEverSmoked").getValue()).equals("Yes")) {
                        haveYouEverSmokedRadioGroup.check(R.id.haveYouEverSmokedRadioYes);
                    } else {
                        haveYouEverSmokedRadioGroup.check(R.id.haveYouEverSmokedRadioNo);
                    }
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
