package com.charlie.compare;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/18 16:07
 * @Description: Test
 */
class Test {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student(18, "charlie"));
        list.add(new Student(23, "bruce"));
        list.add(new Student(34, "john"));
        list.add(new Student(24, "tom"));
        // list.sort(null);
        // list.sort((s1, s2) -> s2.age - s1.age);
        list.sort(Comparator.comparingInt(Student::getAge).reversed());
        System.out.println(list);
    }


    static class Student implements Comparable<Student> {
        private int age;
        private String name;

        public Student(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public int compareTo(Student o) {
            return this.age - o.age;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "age=" + age +
                    ", name='" + name +
                    "}";
        }
    }
}
