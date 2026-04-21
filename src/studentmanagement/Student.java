package studentmanagement;

public class Student {
    private int id;
    private String name;
    private int age;
    private String branch;
    private double CGPA;
    public Student(int id,String name,int age, String branch,double CGPA){
        this.id=id;
        this.name=name;
        this.age=age;
        this.branch=branch;
        this.CGPA=CGPA;

    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;

    }
    public void setName(String name){
        this.name=name;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age=age;
    }

    public String getBranch(){
        return branch;
    }
    public void setBranch(String branch){
        this.branch=branch;
    }
    public double getCgpa(){
        return CGPA;
    }
    public void setCGPA(double CGPA){
        this.CGPA=CGPA;
    }
    public String toString(){
        return String.format("| %-4d | %-20s | %-4d | %-12s | %-6.2f |",id,name,age,branch,CGPA);

    }

}

