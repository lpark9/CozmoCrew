package com.example.mamfe.commonappafrica;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Myo Thiha on 4/10/2018.
 */

public class CustomDialog extends AppCompatDialog{
    private final Context context;
    @BindView(R.id.textInfo)
    TextView textinfo;
    public CustomDialog(Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_added_college);
        ButterKnife.bind(this);
    }
    public void setText(String str) {
        textinfo.setText(str);
    }
    @OnClick (R.id.okButton) void onClick() {
        dismiss();
    }
}
