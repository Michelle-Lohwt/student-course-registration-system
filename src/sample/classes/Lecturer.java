package sample.classes;

public class Lecturer {
    private int ID;
    private String name;
    private long NRIC;
    private String status;
    private String position;
    private String school;
    private String campus;

    public Lecturer(int ID){
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCampus() {
        return campus;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }

    public void setNRIC(long NRIC) {
        this.NRIC = NRIC;
    }

    public long getNRIC() {
        return NRIC;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
