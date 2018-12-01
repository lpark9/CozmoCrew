package com.example.mamfe.commonappafrica;

import android.app.Activity;
import android.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.RecoverySystem;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.Activity.RESULT_OK;


public class AcademicProfileAttachment extends Fragment {

    private Button saveButton, uploadButton, prevButton, nextButton;

    private ImageView idImage;
    private Uri filePath;
    private BroadcastReceiver mDownloadReceiver;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private final int PICK_IMAGE_REQUEST = 71;
    private AcademicProfileApplicationDetails.OnFragmentInteractionListener mListener;
    private boolean isApplying;
    private Uri mDownloadUrl = null;
    private Uri mFileUri = null;
    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //storageReference = FirebaseStorage.getInstance().getReference();
//        if (savedInstanceState != null) {
//            mFileUri = savedInstanceState.getParcelable(KEY_FILE_URI);
//            mDownloadUrl = savedInstanceState.getParcelable(KEY_DOWNLOAD_URL);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_academic_profile_attachment, container, false);


//        mAuth = FirebaseAuth.getInstance();
//        user = mAuth.getCurrentUser();

//
//        //final String uid = user.getUid();
//
//        //Bind the save listener to button
        uploadButton = view.findViewById(R.id.chooseImageButton);
        saveButton = view.findViewById(R.id.saveButton);
        nextButton = view.findViewById(R.id.next_button);
        idImage = view.findViewById(R.id.profileImage);
        prevButton = view.findViewById(R.id.prev_button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //uploadImage();
//                Toast feedback = Toast.makeText(view.getContext(), "Information Updated!", Toast.LENGTH_SHORT);
//                feedback.show();

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
                android.app.Fragment workExperience = new AcademicProfileWorkExperience();
                workExperience.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, workExperience);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return view;
    }
//
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                idImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//
//    private void uploadImage() {
//
//        if (filePath != null) {
//
//            if (storageReference !=null) {
//                StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
//                ref.putFile(filePath)
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                Toast text = Toast.makeText(getContext(), "Uploaded!", Toast.LENGTH_SHORT);
//                                text.show();
//
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                                Toast text = Toast.makeText(getContext(), "Failed.", Toast.LENGTH_SHORT);
//                                text.show();
//                            }
//                        });
//            } else{
//                Toast text = Toast.makeText(getContext(), "Database Sink Failed.", Toast.LENGTH_SHORT);
//                text.show();
//            }
//        }
//    }


}
