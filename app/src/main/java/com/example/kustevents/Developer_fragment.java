package com.example.kustevents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Developer_fragment extends Fragment
{
    ImageButton showDetails, hideDetails;
    LinearLayout dev__details;
    TextView name, skills, education;
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_developer_details, null);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        dev__details();
    }

    private void dev__details()
    {
        name = (TextView)mView.findViewById(R.id.dev_name);
        skills = (TextView)mView.findViewById(R.id.dev_skills);
        education = (TextView)mView.findViewById(R.id.dev_student);

        dev__details = (LinearLayout)mView.findViewById(R.id.details_frame);

        showDetails = (ImageButton)mView.findViewById(R.id.show_details);
        hideDetails = (ImageButton)mView.findViewById(R.id.hide_details);

        showDetails.setEnabled(true);
        hideDetails.setEnabled(false);

        showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText(R.string.developerName);
                skills.setText(R.string.developerSkills);
                education.setText(R.string.studyingAt);

                hideDetails.setEnabled(true);
                showDetails.setEnabled(false);
            }
        });

        hideDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText(null);
                skills.setText(null);
                education.setText(null);

                hideDetails.setEnabled(false);
                showDetails.setEnabled(true);
            }
        });

    }

    public void FeedbackBTN(View view)
    {

    }

}
