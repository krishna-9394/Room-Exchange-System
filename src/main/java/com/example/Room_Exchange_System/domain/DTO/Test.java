package com.example.Room_Exchange_System.domain.DTO;

public class Test {
    int a;
    Test(int c) {
        System.out.println("Test");
    }



    public static void main(String[] args) {
        Test2 test2 = new Test2();
    }
}

class Test2 extends Test {
    Test2(){
        super(11);
        System.out.println("test2");
    }
}
