package com.example.kustevents;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class Announcement_fragment extends Fragment
{
    RecyclerView rv;
    View mView;
    DatabaseReference dbRefer;
    FirebaseAuth mAuth;
    FirebaseStorage storage;

    private CustomAdapter adapter;
    private List<ModelClass> events_Uploaded_list;

    private ProgressDialog progress;

    //current user id
    private String currentUser_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_datesheet, null);
        return myView;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mView = view;

        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Syncing...");
        progress.setCancelable(false);
        progress.show();

        loadData();
    }

    private void loadData()
    {
        rv = (RecyclerView) mView.findViewById(R.id.datesheet_rv);
        rv.hasFixedSize();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        events_Uploaded_list = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        currentUser_ID = mAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance();
        dbRefer = FirebaseDatabase.getInstance().getReference("Announcements").child("Event");

        dbRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ModelClass uploadedpic = postSnapshot.getValue(ModelClass.class);
                    events_Uploaded_list.add(uploadedpic);
                }
                adapter = new CustomAdapter(getActivity(), events_Uploaded_list);
                rv.setAdapter(adapter);
                progress.dismiss();
//                adapter.setOnItemClickListener(getActivity()); for this we will implement the onItemClickListener
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
        progress.show();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

}
