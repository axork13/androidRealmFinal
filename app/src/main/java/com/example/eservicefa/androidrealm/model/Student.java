package com.example.eservicefa.androidrealm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jimmy on 09/11/2017.
 */

public class Student extends RealmObject {

    String name;
    int marks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student " +
                " name='" + name + ' ' +
                ", marks=" + marks +
                '\n';
    }
}
