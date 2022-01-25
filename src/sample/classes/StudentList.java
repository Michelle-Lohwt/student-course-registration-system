package sample.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentList {
    
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
  }
