package com.websystique.springboot.model;

public class User {

    private long id;
    private String uname;
    private String email;
    private String upass;

    public User(String uname, String email, String upass, int isActive) {
        this.uname = uname;
        this.email = email;
        this.upass = upass;
        this.isActive = isActive;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }
    private int isActive;
    private String name;

    private int age;

    private double salary;

    public User(long id, String name, int age, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public User() {
        id = 0;
    }

 

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", name=" + name + ", age=" + age
//				+ ", salary=" + salary + "]";
//	}
}
