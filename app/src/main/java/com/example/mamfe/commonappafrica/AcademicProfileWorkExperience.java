package com.example.mamfe.commonappafrica;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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
 * {@link AcademicProfileWorkExperience.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcademicProfileWorkExperience#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcademicProfileWorkExperience extends Fragment {
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

    private Calendar startCalendar;
    private Calendar endCalendar;

    private boolean isApplying;

    private OnFragmentInteractionListener mListener;

    public AcademicProfileWorkExperience() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcademicProfileWorkExperience.
     */
    // TODO: Rename and change types and number of parameters
    public static AcademicProfileWorkExperience newInstance(String param1, String param2) {
        AcademicProfileWorkExperience fragment = new AcademicProfileWorkExperience();
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
        View view = inflater.inflate(R.layout.fragment_academic_profile_work_experience, container, false);

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
                if (isApplying) {
                    startActivity(new Intent(getActivity(), PaymentActivity.class));
                } else {
                    Toast feedback = Toast.makeText(view.getContext(), "You have completed your profile!", Toast.LENGTH_SHORT);
                    feedback.show();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
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
        DatabaseReference appDetails = database.child("UserProfiles").child(uid).child("workExperience");

        appDetails.child("organization").setValue(((EditText) getView().findViewById(R.id.orgEdit)).getText().toString());
        appDetails.child("title").setValue(((EditText) getView().findViewById(R.id.titleEdit)).getText().toString());
        appDetails.child("dates").child("start").setValue(((EditText) getView().findViewById(R.id.startDateEdit)).getText().toString());
        appDetails.child("dates").child("end").setValue(((EditText) getView().findViewById(R.id.endDateEdit)).getText().toString());
    }

    private void buildCalendar() {
        //Build the date picker
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        EditText startDateEditText = (EditText) getView().findViewById(R.id.startDateEdit);
        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartLabel();
            }

        };

        startDateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), startDate, startCalendar
                        .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EditText endDateEditText = (EditText) getView().findViewById(R.id.endDateEdit);
        final DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, monthOfYear);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndLabel();
            }

        };

        endDateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), endDate, endCalendar
                        .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ((EditText) getView().findViewById(R.id.startDateEdit)).setText(sdf.format(startCalendar.getTime()));
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ((EditText) getView().findViewById(R.id.endDateEdit)).setText(sdf.format(endCalendar.getTime()));
    }

    private void populateFields(final View view) {
        //TODO: Populate the fields
        String uid = this.user.getUid();
        database.child("UserProfiles").child(uid).child("workExperience").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("organization").getValue() != null) {
                    EditText t = view.findViewById(R.id.orgEdit);
                    t.setText((String) dataSnapshot.child("organization").getValue());
                }

                if(dataSnapshot.child("title").getValue() != null) {
                    EditText t = view.findViewById(R.id.titleEdit);
                    t.setText((String) dataSnapshot.child("title").getValue());
                }

                if(dataSnapshot.child("dates").child("start").getValue() != null) {
                    EditText t = view.findViewById(R.id.startDateEdit);
                    t.setText((String) dataSnapshot.child("dates").child("start").getValue());
                }

                if(dataSnapshot.child("dates").child("end").getValue() != null) {
                    EditText t = view.findViewById(R.id.endDateEdit);
                    t.setText((String) dataSnapshot.child("dates").child("end").getValue());
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
