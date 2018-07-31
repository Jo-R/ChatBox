package com.jorigg.android.chatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.jorigg.android.chatbox.model.UserPreferences;

public class ConfigureUserActivity extends AppCompatActivity {

    private EditText mEditName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_user);

        mEditName = findViewById(R.id.edit_name);
        mEditName.setText(UserPreferences.getUserName(getApplicationContext()));

        mEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserPreferences.setUserName(getApplicationContext(), mEditName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
}
