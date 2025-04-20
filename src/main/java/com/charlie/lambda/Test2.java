package com.charlie.lambda;

import java.util.stream.Stream;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/18 22:59
 * @Description: Test2
 */
public class Test2 {
    public static void main(String[] args) {
        Stream.of(
                        new Student("张无忌", 18),
                        new Student("周芷若", 19),
                        new Student("赵敏", 17)
                )
                // .forEach(System.out::println);
                // .forEach(Student::abs);
                // .forEach(Student::abc);
                .filter(stu -> stu.age > 18)
                .forEach(Student::abc);
    }

    static class Student {
        private String name;
        private int age;

        public Student() {
        }

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public static void abc(Student stu) {
            System.out.println(stu);
        }

        // public static boolean isBig(Student stu) {
        //     return stu.age >= 18;
        // }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
