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
    private EditText mEditAdultName;
    private EditText mEditPraise;
    private EditText mEditTarget;
    private EditText mEditReward;
    private ImageView mSelectGirl;
    private ImageView mSelectBoy;
    private ImageView mSelectBoyRed;
    private ImageView mSelectBoyToy;
    private ImageView mSelectGirlBlue;
    private ImageView mSelectGirlPink;
    private ImageView mSelectPolice;

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

        mEditAdultName = findViewById(R.id.edit_adult_name);
        mEditAdultName.setText(UserPreferences.getAdultName(getApplicationContext()));
        mEditAdultName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserPreferences.setAdultName(getApplicationContext(), mEditAdultName.getText().toString
                        ());
                Toast.makeText(ConfigureUserActivity.this, "Adult's Name updated", Toast
                        .LENGTH_SHORT)
                        .show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEditPraise = findViewById(R.id.edit_praise);
        mEditPraise.setText(UserPreferences.getPraise(getApplicationContext()));
        mEditPraise.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserPreferences.setPraise(getApplicationContext(), mEditPraise.getText().toString());
                Toast.makeText(ConfigureUserActivity.this, "Praise updated", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mEditTarget = findViewById(R.id.edit_reward_target);
        mEditTarget.setText(Integer.toString(UserPreferences.getTarget(getApplicationContext())));
        mEditTarget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!mEditTarget.getText().toString().equals("")) {
                    UserPreferences.setTarget(getApplicationContext(), Integer.parseInt(mEditTarget
                            .getText().toString()));
                    Toast.makeText(ConfigureUserActivity.this, "Target updated", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mEditReward = findViewById(R.id.edit_reward);
        mEditReward.setText(UserPreferences.getReward(getApplicationContext()));
        mEditReward.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserPreferences.setReward(getApplicationContext(), mEditReward.getText().toString
                        ());
                Toast.makeText(ConfigureUserActivity.this, "Reward updated", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSelectBoy = findViewById(R.id.select_boy_figure);
        mSelectGirl = findViewById(R.id.select_girl_figure);
        mSelectBoyRed = findViewById(R.id.select_boy_red);
        mSelectBoyToy = findViewById(R.id.select_boy_toy);
        mSelectGirlBlue = findViewById(R.id.select_girl_blue);
        mSelectGirlPink = findViewById(R.id.select_girl_pink);
        mSelectPolice = findViewById(R.id.select_police);

        int selectedAvatar = UserPreferences.getUserAvatar(getApplicationContext());

        if (selectedAvatar == R.drawable.boy_figure_test) {
            mSelectBoy.setBackground(getResources().getDrawable(R.drawable.highlight_green));
        } else if (selectedAvatar == R.drawable.girl_figure) {
            mSelectGirl.setBackground(getResources().getDrawable(R.drawable.highlight_green));
        } else if (selectedAvatar == R.drawable.boy_red) {
            mSelectBoyRed.setBackground(getResources().getDrawable(R.drawable.highlight_green));
        } else if (selectedAvatar == R.drawable.boy_toy) {
            mSelectBoyToy.setBackground(getResources().getDrawable(R.drawable.highlight_green));
        } else if (selectedAvatar == R.drawable.girl_blue) {
            mSelectGirlBlue.setBackground(getResources().getDrawable(R.drawable.highlight_green));
        } else if (selectedAvatar == R.drawable.girl_pink) {
            mSelectGirlPink.setBackground(getResources().getDrawable(R.drawable.highlight_green));
        } else if (selectedAvatar == R.drawable.police) {
            mSelectPolice.setBackground(getResources().getDrawable(R.drawable.highlight_green));
        }


        mSelectGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferences.setUserAvatar(getApplicationContext(), R.drawable.girl_figure);
                mSelectGirl.setBackground(getResources().getDrawable(R.drawable.highlight_green));
                mSelectBoy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyRed.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyToy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlBlue.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlPink.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectPolice.setBackgroundColor(getResources().getColor(R.color.white));
                Toast.makeText(ConfigureUserActivity.this, "Avatar Changed", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mSelectBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferences.setUserAvatar(getApplicationContext(), R.drawable.boy_figure_test);
                mSelectBoy.setBackground(getResources().getDrawable(R.drawable.highlight_green));
                mSelectGirl.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyRed.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyToy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlBlue.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlPink.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectPolice.setBackgroundColor(getResources().getColor(R.color.white));
                Toast.makeText(ConfigureUserActivity.this, "Avatar Changed", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mSelectBoyRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferences.setUserAvatar(getApplicationContext(), R.drawable.boy_red);
                mSelectBoyRed.setBackground(getResources().getDrawable(R.drawable.highlight_green));
                mSelectBoy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirl.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyToy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlBlue.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlPink.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectPolice.setBackgroundColor(getResources().getColor(R.color.white));
                Toast.makeText(ConfigureUserActivity.this, "Avatar Changed", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mSelectBoyToy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferences.setUserAvatar(getApplicationContext(), R.drawable.boy_toy);
                mSelectBoyToy.setBackground(getResources().getDrawable(R.drawable.highlight_green));
                mSelectBoy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirl.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyRed.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlBlue.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlPink.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectPolice.setBackgroundColor(getResources().getColor(R.color.white));
                Toast.makeText(ConfigureUserActivity.this, "Avatar Changed", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mSelectGirlBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferences.setUserAvatar(getApplicationContext(), R.drawable.girl_blue);
                mSelectGirlBlue.setBackground(getResources().getDrawable(R.drawable.highlight_green));
                mSelectBoy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirl.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyToy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyRed.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlPink.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectPolice.setBackgroundColor(getResources().getColor(R.color.white));
                Toast.makeText(ConfigureUserActivity.this, "Avatar Changed", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mSelectGirlPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferences.setUserAvatar(getApplicationContext(), R.drawable.girl_pink);
                mSelectGirlPink.setBackground(getResources().getDrawable(R.drawable.highlight_green));
                mSelectBoy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirl.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyRed.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlBlue.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyToy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectPolice.setBackgroundColor(getResources().getColor(R.color.white));
                Toast.makeText(ConfigureUserActivity.this, "Avatar Changed", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mSelectPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferences.setUserAvatar(getApplicationContext(), R.drawable.police);
                mSelectPolice.setBackground(getResources().getDrawable(R.drawable.highlight_green));
                mSelectBoy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirl.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyToy.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlBlue.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectGirlPink.setBackgroundColor(getResources().getColor(R.color.white));
                mSelectBoyRed.setBackgroundColor(getResources().getColor(R.color.white));
                Toast.makeText(ConfigureUserActivity.this, "Avatar Changed", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}
