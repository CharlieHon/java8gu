package com.charlie.threadx;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/9 20:01
 * @Description: TestThreadLocal
 */
public class TestThreadLocal {
    private static final ThreadLocal<Student> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        Student stu = new Student("charlie", 24);
        tl.set(stu);
        tl.get();
        tl.remove();
    }

    static class Student {
        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
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
    }
}
