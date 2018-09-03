package com.jorigg.android.chatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import static com.jorigg.android.chatbox.ConversationListActivity.CONVO_TYPE;

public class ViewDiagramActivity extends AppCompatActivity{

    private ImageView mDiagramImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diagram);

        mDiagramImageView = findViewById(R.id.diagram_image_view);

        String convoType = getIntent().getStringExtra(CONVO_TYPE);

        if (convoType.equals("AskForSomething")) {
            mDiagramImageView.setImageDrawable(getResources().getDrawable(R.drawable
                    .ask_for_something_state));
        } else if (convoType.equals("Greeting")) {
            mDiagramImageView.setImageDrawable(getResources().getDrawable(R.drawable
                    .greet_state));
        }


    }
}
