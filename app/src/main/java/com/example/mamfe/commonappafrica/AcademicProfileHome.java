package com.example.mamfe.commonappafrica;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AcademicProfileHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcademicProfileHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcademicProfileHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AcademicProfileHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcademicProfileHome.
     */
    // TODO: Rename and change types and number of parameters
    public static AcademicProfileHome newInstance(String param1, String param2) {
        AcademicProfileHome fragment = new AcademicProfileHome();
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
        View view = inflater.inflate(R.layout.fragment_academic_profile_home, container, false);
        // Inflate the layout for this fragment

        Button generalInfoButton = view.findViewById(R.id.buttonGenInfo);
        Button educationBackgroundButton = view.findViewById(R.id.buttonEduBackground);
        Button applicationDetailsButton = view.findViewById(R.id.buttonAppDetails);
        Button workExperienceButton = view.findViewById(R.id.buttonWorkExp);
        Button personalHealthButton = view.findViewById(R.id.buttonPersonalHealth);
        Button familyInfoButton = view.findViewById(R.id.buttonFamilyInfo);

        generalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment genInfoFragment = new AcademicProfileGeneralInfo();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, genInfoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        educationBackgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment eduBackgroundFragment = new AcademicProfileEducationBackground();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, eduBackgroundFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        applicationDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment appDetailsFragment = new AcademicProfileApplicationDetails();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, appDetailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        workExperienceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment workExperienceFragment = new AcademicProfileWorkExperience();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, workExperienceFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        personalHealthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment personalHealthFragment = new AcademicProfilePersonalHealth();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, personalHealthFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        familyInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment familyInfoFragment = new AcademicProfileFamilyInfo();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, familyInfoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
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
