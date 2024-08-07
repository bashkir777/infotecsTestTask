package com.bashkir777.entities;

public class Student implements Comparable<Student> {

    private Integer id;
    private String name;

    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(Student otherStudent) {
        return this.getName().compareTo(otherStudent.getName());
    }

    @Override
    public String toString() {
        return "\t{\n\t\"id\": \"" + this.id + "\",\n\t \"name\": \"" + this.name + "\"\n\t}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Student student = (Student) obj;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
