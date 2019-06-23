package com.example.kustevents;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class Settings_fragment extends Fragment
{
    private FirebaseAuth user_Auth;
    private FirebaseDatabase user_Database;
    private FirebaseUser user_ID;
    private DatabaseReference databaseReference;
    View mView;

    //Variables related to xml
    private EditText user_Name;
    private EditText user_Reg_Num;
    private Spinner user_Department;
    private Spinner user_Semester;
    private RadioButton user_section_A, user_Section_B;
    private Button save_User_Data;

    //For use of only Spinner
    String departmentSelected, semesterSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, null);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mView = view;
        MainWork();
    }

    public void MainWork()
    {
        user_Auth = FirebaseAuth.getInstance();
        FirebaseUser mUser = user_Auth.getCurrentUser();
        String uId = mUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserData").child(uId);
        databaseReference.keepSynced(true);

        user_Name = (EditText)mView.findViewById(R.id.user_name_id);
        user_Reg_Num = (EditText)mView.findViewById(R.id.user_reg_id);
        user_Department = (Spinner)mView.findViewById(R.id.user_department_id);
        user_Semester = (Spinner) mView.findViewById(R.id.user_semester_id);
        user_section_A = (RadioButton)mView.findViewById(R.id.user_section_A_id);
        user_Section_B = (RadioButton)mView.findViewById(R.id.user_section_B_id);
        save_User_Data = (Button) mView.findViewById(R.id.save_user_data_btn_id);

        //Spinner Functionality Of User Department Selection
        ArrayAdapter<CharSequence> departmentAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.userDepartment, android.R.layout.simple_spinner_item);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_Department.setAdapter(departmentAdapter);

        //Spinner Functionality Of User Department Selection
        ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.userSemester, android.R.layout.simple_spinner_item);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_Semester.setAdapter(semesterAdapter);

//        user_Department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                departmentSelected = user_Department.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
//            }
//        });

        save_User_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = user_Name.getText().toString().trim();
                String Registration_Number = user_Reg_Num.getText().toString().trim();
                departmentSelected = user_Department.getSelectedItem().toString();
                semesterSelected = user_Semester.getSelectedItem().toString();
//                boolean sectionA = user_section_A.isChecked();
//                boolean sectionB = user_Section_B.isChecked();

//                if(sectionA == true)
//                {
//                    String userSection = user_section_A.getText().toString();
//                }
//                else if(sectionB == true)
//                {
//                    String userSection = user_Section_B.getText().toString();
//                }

                if(Name.isEmpty())
                {
                    user_Name.setError("Name Required!");
                    user_Name.requestFocus();
                    return;
                }
                if(Name.length() > 20)
                {
                    user_Name.setError("Name size must be lower than 20 letters");
                    user_Name.requestFocus();
                    return;
                }
                if(Name.length() < 3)
                {
                    user_Name.setError("Name size must be greater than 3 letters");
                    user_Name.requestFocus();
                    return;
                }
                if(Registration_Number.isEmpty())
                {
                    user_Reg_Num.setError("Registration Number Required!");
                    user_Reg_Num.requestFocus();
                    return;
                }
                if(Registration_Number.length() < 11)
                {
                    user_Reg_Num.setError("Valid Format : CS120------");
                    user_Reg_Num.requestFocus();
                    return;
                }
                if(Registration_Number.length() > 11)
                {
                    user_Reg_Num.setError("Valid Format : CS120------");
                    user_Reg_Num.requestFocus();
                    return;
                }
                if(departmentSelected.isEmpty())
                {
                    Toast.makeText(getActivity(), "Please Select Department", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(semesterSelected.isEmpty())
                {
                    Toast.makeText(getActivity(), "Please Select Semester", Toast.LENGTH_SHORT).show();
                    return;
                }

                String date = DateFormat.getDateInstance().format(new Date());

                ModelClass_UserSetting settingModel = new ModelClass_UserSetting(Name,Registration_Number,departmentSelected,
                        semesterSelected,date);

                databaseReference.setValue(settingModel);
                Toast.makeText(getActivity(), "Successfully Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
