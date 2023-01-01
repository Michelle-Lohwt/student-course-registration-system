package sample.classes;

public class Course {
    private String code;
    private String title;
    private String desc;
    private String time;

    public Course(String code, String title, String desc, String time) {
        this.code = code;
        this.title = title;
        this.desc = desc;
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getTime() {
        return time;
    }
}
