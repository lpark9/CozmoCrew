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

    private Calendar birthCalendar;


    private OnFragmentInteractionListener mListener;
    private boolean isApplying;

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
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isApplying = bundle.getBoolean("isApplying");
        } else {
            isApplying = false;
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ((EditText) getView().findViewById(R.id.dateOfBirthEdit)).setText(sdf.format(birthCalendar.getTime()));
    }

    private void buildCalendar() {
        //Build the date picker
        birthCalendar = Calendar.getInstance();

        EditText edittext= (EditText) getView().findViewById(R.id.dateOfBirthEdit);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                birthCalendar.set(Calendar.YEAR, year);
                birthCalendar.set(Calendar.MONTH, monthOfYear);
                birthCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, birthCalendar
                        .get(Calendar.YEAR), birthCalendar.get(Calendar.MONTH),
                        birthCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
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
                Fragment personalHealth = new AcademicProfilePersonalHealth();
                personalHealth.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, personalHealth);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isApplying", isApplying);
                Fragment familyInfo = new AcademicProfileFamilyInfo();
                familyInfo.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, familyInfo);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buildCalendar();
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
