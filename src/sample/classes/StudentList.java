package sample.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentList {
    
    String name;
    int matric;

    public StudentList(){
        this.name = "";
        this.matric = 0;
    }
    
    
    public String getname() {
        return name;
    }

    public void setname(String n) {
        this.name = n;
    }

    public int getmatric() {
        return matric;
    }

    public void setmatric(int m) {
        this.matric = m;
    }

    public StudentList(String name, int matric){
        this.name = name;
        this.matric = matric;
    }
    
    /*
    StringProperty name = new SimpleStringProperty();
    StringProperty matric = new SimpleStringProperty();
    
    public final StringProperty nameProperty() {
        return this.name;
    }

    public final java.lang.String getName() {
        return this.nameProperty().get();
    }

    public final void setName(final java.lang.String name) {
        this.nameProperty().set(name);
    }

    public final StringProperty matricProperty() {
        return this.matric;
    }

    public final java.lang.String getMatric() {
        return this.matricProperty().get();
    }

    public final void setMatric(final java.lang.String matric) {
        this.matricProperty().set(matric);
    }
    */
  }
