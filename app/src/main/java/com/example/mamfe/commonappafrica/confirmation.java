package com.example.mamfe.commonappafrica;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.mamfe.commonappafrica.LoginActivity;

public class confirmation extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

//        System.out.println("onCreate !!!!!!!!!!!!!!!!!!!!!!!!");

        userName = LoginActivity.userId;

        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your application has been successfully submitted!");
        intent.putExtra(Intent.EXTRA_TEXT, "This is confirmation email that your application has been received. ");
        String emailAddress = "mailto:" + userName;
        intent.setData(Uri.parse(emailAddress)); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.

//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{userName});
////        System.out.println(userName + " @@@@@@@@@@@@@@@@@@@@@@@@@");
//        i.putExtra(Intent.EXTRA_SUBJECT, "Your application has been successfully submitted!");
//        i.putExtra(Intent.EXTRA_TEXT   , "This is confirmation email that your application has been received. ");
//
//        try {
//            startActivity(Intent.createChooser(i, "Send mail..."));
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(confirmation.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//        }

        ConstraintLayout rlayout = (ConstraintLayout) findViewById(R.id.layout);
        rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(confirmation.this, MainActivity.class));
            }

        });

    }

//    public void redirect(View view) {
//        System.out.println("redirect ============================");
//        startActivity(new Intent(confirmation.this, MainActivity.class));
//    }

//    public void getEmail(){
//        String a;
//        databaseReference.child("Users").orderByChild("email").equalTo(LoginActivity.userId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                String name = "";
//                String key = "";
//
//                for (DataSnapshot child : children) {
//                    key = child.getKey();
//                    name = child.child("name").getValue().toString();
//                }
//
//                userName = name;
//
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("message/rfc822");
//                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{userName});
//                System.out.println(userName + " @@@@@@@@@@@@@@@@@@@@@@@@@");
//                i.putExtra(Intent.EXTRA_SUBJECT, "Your application has been successfully submitted!");
//                i.putExtra(Intent.EXTRA_TEXT   , "This is confirmation email that your application has been received. ");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        //System.out.println("INSIDE THE METHOD" + userName);
//        //return userName;
//    }

}