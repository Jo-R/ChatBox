package com.jorigg.android.chatbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.XmlWriteRead;

import java.util.ArrayList;

public class ParentHomeActivity extends AppCompatActivity implements DeleteChatDialogFragment
        .NoticeDialogListener {

    public final static String TITLE_TO_CONFIG = "convoTitle";

    private Spinner mEditSpinner;
    private Spinner mCreateSpinner;
    private EditText mNewTitle;
    private ChatBank mChatBank;
    private Button mCreateButton;
    private Button mEditButton;
    private Button mDeleteButton;

    private String mSelectedConversation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        mChatBank = ChatBank.get(this);
        mEditSpinner = findViewById(R.id.edit_spinner);
        mCreateSpinner = findViewById(R.id.create_new_spinner);
        populateSpinners();

        mCreateButton = findViewById(R.id.create_new_button);
        mEditButton = findViewById(R.id.edit_button);
        mDeleteButton = findViewById(R.id.delete_button);

        mNewTitle = findViewById(R.id.create_new_title);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedTemplate = String.valueOf(mCreateSpinner.getSelectedItem());
                String title = mNewTitle.getText().toString();
                //create conversation which can be accessed in next screen
                if (mChatBank.addNewConversation(title, selectedTemplate)) {
                    mNewTitle.setText("");
                    populateSpinners();
                    Intent intent = new Intent(ParentHomeActivity.this, ConversationListActivity
                            .class);
                    intent.putExtra(TITLE_TO_CONFIG, title);
                    startActivity(intent);
                } else {
                    Toast.makeText(ParentHomeActivity.this, "this title already exists", Toast
                            .LENGTH_LONG).show();
                }
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedConversation = String.valueOf(mEditSpinner.getSelectedItem());
                Intent intent = new Intent(ParentHomeActivity.this, ConversationListActivity
                        .class);
                intent.putExtra(TITLE_TO_CONFIG, mSelectedConversation);
                startActivity(intent);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedConversation = String.valueOf(mEditSpinner.getSelectedItem());
                DialogFragment confirmation = new DeleteChatDialogFragment();
                confirmation.show(getSupportFragmentManager(), "deleteConfirm");

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_user_config_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_config:
                Intent intent = new Intent(ParentHomeActivity.this, ConfigureUserActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Context ctxt = this.getApplicationContext();
        XmlWriteRead.writeChatsToXML(mChatBank.getConversationLibrary(), ctxt);
    }

    private void populateSpinners() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, mChatBank.getChatTitles());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditSpinner.setAdapter(adapter);

        ArrayList<String> templates = new ArrayList<>();
        templates.add("Ask for Something");
        templates.add("Greeting");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, templates);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCreateSpinner.setAdapter(adapter2);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        mChatBank.deleteConversation(mSelectedConversation);
        Toast.makeText(ParentHomeActivity.this, mSelectedConversation + "deleted", Toast
                .LENGTH_LONG).show();
        populateSpinners();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
