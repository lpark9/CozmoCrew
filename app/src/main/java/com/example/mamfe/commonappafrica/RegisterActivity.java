package com.example.mamfe.commonappafrica;

import android.content.Intent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference databaseReference = database.getInstance().getReference("user");
    private FirebaseAuth mAuth;
    //private FirebaseUser user;

    private EditText nameEdit;
    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText confirmEdit;
    private EditText addressEdit;
    public static int registerCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        nameEdit = (EditText) findViewById(R.id.nameEdit);
        emailEdit = (EditText) findViewById(R.id.emailEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        confirmEdit = (EditText) findViewById(R.id.confirmEdit);
        addressEdit = (EditText)  findViewById(R.id.addressEdit);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (nameEdit.getText().toString().equals("")
                        || emailEdit.getText().toString().equals("")
                        || passwordEdit.getText().toString().equals("")
                        || confirmEdit.getText().toString().equals("")
                        || addressEdit.getText().toString().equals("")) {

                    Context context = getApplicationContext();
                    CharSequence text = "Need more registration information.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailEdit.getText().toString(), passwordEdit.getText().toString())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {

                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {

                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println(registerCount + "                                             ");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    registerCount += 1;
                                    DatabaseReference mRef = database.getReference().child("Users").child("" + registerCount);
                                    mRef.child("name").setValue(nameEdit.getText().toString());
                                    mRef.child("email").setValue(emailEdit.getText().toString());
                                    mRef.child("address").setValue(addressEdit.getText().toString());
                                    mRef.child("password").setValue(passwordEdit.getText().toString());
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });





    }

    private void registerUser(String e, String p) {

    }

}
