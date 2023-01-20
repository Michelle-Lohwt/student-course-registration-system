package sample.classes;

public class Student {
    public Student(int id){
        this.ID = id;
    }
    private final int ID;
    private String name;
    private long NRIC;
    private String status;
    private String sem;
    private float CGPA;
    private int year;
    private String school;
    private String campus;
    private String programme;
    private String major;
    private String minor;

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNRIC(long NRIC) {
        this.NRIC = NRIC;
    }

    public long getNRIC() {
        return NRIC;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getSem() {
        return sem;
    }

    public void setCGPA(float CGPA) {
        this.CGPA = CGPA;
    }

    public float getCGPA() {
        return CGPA;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCampus() {
        return campus;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getProgramme() {
        return programme;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getMinor() {
        return minor;
    }
}
