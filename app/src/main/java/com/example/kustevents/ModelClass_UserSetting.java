package com.example.kustevents;

public class ModelClass_UserSetting
{
    private String fullName, registrationNum;
    private String Department, Semester, CurrentDate;
//    private Boolean Section;

    public ModelClass_UserSetting(String fullName, String registrationNum, String department, String semester, String currentDate)
    {
        this.fullName = fullName;
        this.registrationNum = registrationNum;
        Department = department;
        Semester = semester;
        CurrentDate = currentDate;
//        Section = section;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    //    public Boolean getSection() {
//        return Section;
//    }
//
//    public void setSection(Boolean section) {
//        Section = section;
//    }
}
