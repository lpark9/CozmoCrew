package com.example.mamfe.commonappafrica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserIdView;
    private EditText mPasswordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button email = (Button) findViewById(R.id.login_button);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click
                mUserIdView = (EditText) findViewById(R.id.email_input);
                mPasswordView = (EditText) findViewById(R.id.passwd_input);

                String userValue = mUserIdView.getText().toString();
                String passValue = mPasswordView.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(userValue.equals("")) {
                    Toast.makeText(LoginActivity.this, "User email is Empty", Toast.LENGTH_SHORT).show();
                } else if(passValue.equals("")) {
                    Toast.makeText(LoginActivity.this, "User password is Empty", Toast.LENGTH_SHORT).show();
                }else if (!userValue.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "not valid email address", Toast.LENGTH_SHORT).show();
                }
                if (userValue.matches(emailPattern) && passValue.length() >= 8) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
        //Button register = (Button) findViewById(R.id.register_button);

        ButterKnife.bind(this);
    }
//    @OnClick(R.id.login_button) public void onClick() {
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//    }
}
