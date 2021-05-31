package com.midproject.final_project_bncc;

public class Student {
    private String id, email, name, pass;

    public Student () {}

    public Student(String id, String email, String name, String pass) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }
}
