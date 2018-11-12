package com.example.mamfe.commonappafrica;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserIdView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;

    //added
    private static String userId, userName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }

        setContentView(R.layout.activity_login);
        Button email = (Button) findViewById(R.id.login_button);
        Button register = (Button) findViewById(R.id.register_button);


        mUserIdView = (EditText) findViewById(R.id.email_input);
        mPasswordView = (EditText) findViewById(R.id.passwd_input);

        mAuth = FirebaseAuth.getInstance();

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click
                String userValue = mUserIdView.getText().toString();
                String passValue = mPasswordView.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (TextUtils.isEmpty(userValue)) {
                    Toast.makeText(LoginActivity.this, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passValue)) {
                    Toast.makeText(LoginActivity.this, "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!userValue.matches(emailPattern)) {
                    Toast.makeText(LoginActivity.this, "User ID should be an email!", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(userValue, passValue)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    if (mPasswordView.getText().toString().length() < 6) {
                                        mPasswordView.setError("The password is too short");
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Invalid Credential", Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    //added
                                    userId = mAuth.getCurrentUser().getEmail();
                                    userName = mAuth.getCurrentUser().getDisplayName();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }
        });
        //Button register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        ButterKnife.bind(this);
    }

    public static String getUserId() {
        if (userId != null) {
            return userId;
        } else {
            return "N/A";
        }

    }

    public static String getUserName() {
        if (userName != null) {
            return userName;
        } else {
            return "N/A";
        }
    }
    //    @OnClick(R.id.login_button) public void onClick() {
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//    }
}
