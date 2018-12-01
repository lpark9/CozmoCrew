package com.example.mamfe.commonappafrica;

import android.app.Activity;
import android.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference(user.getUid());

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
        tryPopulateImage(idImage);
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
                uploadImage();
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

    //https://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //https://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
    //https://firebase.google.com/docs/storage/android/upload-files
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                idImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //https://firebase.google.com/docs/storage/android/upload-files
    private void uploadImage() {
        //TODO: Check to see if there is an image, if not display error toast

        StorageReference idPhotoRef = storageReference.child("idPhoto");

        idImage.setDrawingCacheEnabled(true);
        idImage.buildDrawingCache();

        Bitmap bitmap = ((BitmapDrawable) idImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        UploadTask uploadTask = idPhotoRef.putBytes(imageBytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast text = Toast.makeText(getContext(), "Attachment Upload Failed!", Toast.LENGTH_SHORT);
                text.show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast text = Toast.makeText(getContext(), "Attachment Uploaded", Toast.LENGTH_SHORT);
                text.show();
            }
        });
    }

    //https://firebase.google.com/docs/storage/android/download-files
    //https://stackoverflow.com/questions/14257604/how-to-display-the-jpeg-image-directly-from-byte-array-before-saving-image
    private void tryPopulateImage(ImageView iv) {
        Toast text = Toast.makeText(getContext(), "Checking for ID Photo", Toast.LENGTH_SHORT);
        text.show();

        StorageReference idPhotoRef = storageReference.child("idPhoto");

        final long ONE_GIGABYTE = 1024 * 1024 * 1024;
        idPhotoRef.getBytes(ONE_GIGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Make a bitmap and set image view if file present in storage
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                idImage.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast text = Toast.makeText(getContext(), "No File Found", Toast.LENGTH_SHORT);
                text.show();
            }
        });
    }


}
