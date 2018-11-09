package com.example.mamfe.commonappafrica;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.Toast;

import butterknife.OnClick;

public class PaymentActivity extends AppCompatActivity{

    private EditText cardNumber;
    private EditText expirationDate;
    private EditText CVV;
    private EditText address;
    private boolean isDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button pay = (Button) findViewById(R.id.Pay);
        Button cancel = (Button) findViewById(R.id.Cancel);

        cardNumber = (EditText) findViewById(R.id.CardNumber);
        cardNumber.addTextChangedListener(new CardNumberFormatting());
        expirationDate = (EditText) findViewById(R.id.ExpirationDate);
        CVV = (EditText) findViewById(R.id.CVV);
        address = (EditText) findViewById(R.id.address);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardNumber.length() == 16 + 3 &&
                        expirationDate.length() == 4 &&
                        CVV.length() == 3) {
                    Toast.makeText(PaymentActivity.this, "Payment Confirmed!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(PaymentActivity.this, "Incorrect payment info", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(PaymentActivity.this, PaymentActivity.class));
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, MainActivity.class));
            }
        });
    }

    public static class CardNumberFormatting implements TextWatcher{

        private boolean lock;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (lock || s.length() > 16) {
                return;
            }
            lock = true;
            for (int i = 4; i < s.length(); i += 5) {
                if (s.toString().charAt(i) != ' ') {
                    s.insert(i, " ");
                }
            }
            lock = false;
        }
    }
}
