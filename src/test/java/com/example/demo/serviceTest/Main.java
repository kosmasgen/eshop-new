package com.example.demo.serviceTest;
public class Main {
    public static void main(String[] args) {
        TestLombok test = new TestLombok();
        test.setName("John");
        test.setAge(25);

        System.out.println("Name: " + test.getName());
        System.out.println("Age: " + test.getAge());
    }
}

