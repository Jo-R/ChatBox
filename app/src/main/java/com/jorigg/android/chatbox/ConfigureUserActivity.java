package com.jorigg.android.chatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jorigg.android.chatbox.model.UserPreferences;

public class ConfigureUserActivity extends AppCompatActivity {

    private EditText mEditName;
    private ImageView mSelectGirl;
    private ImageView mSelectBoy;

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
                Toast.makeText(ConfigureUserActivity.this, "Name updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSelectBoy = findViewById(R.id.select_boy_figure);
        mSelectGirl = findViewById(R.id.select_girl_figure);
        if (UserPreferences.getUserAvatar(getApplicationContext()) == R.drawable.boy_figure_test) {
            mSelectBoy.setBackground(getResources().getDrawable(R.drawable.highlight));
        } else {
            mSelectGirl.setBackground(getResources().getDrawable(R.drawable.highlight));
        }


        mSelectGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferences.setUserAvatar(getApplicationContext(), R.drawable.girl_figure);
                mSelectGirl.setBackground(getResources().getDrawable(R.drawable.highlight));
                mSelectBoy.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        mSelectBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferences.setUserAvatar(getApplicationContext(), R.drawable.boy_figure_test);
                mSelectBoy.setBackground(getResources().getDrawable(R.drawable.highlight));
                mSelectGirl.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });


    }
}
