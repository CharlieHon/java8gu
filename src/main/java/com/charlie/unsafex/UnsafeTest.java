package com.charlie.unsafex;

import sun.misc.Unsafe;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/3/7 18:59
 * @Description: Java魔法类：Unsafe应用解析
 */
public class UnsafeTest {
    public static void main(String[] args) throws NoSuchFieldException {
        Student stu = new Student("charlie", 23);
        System.out.println(stu);

        // Unsafe unsafe = Unsafe.getUnsafe();
        Unsafe unsafe = UnsafeUtil.getUnsafe();
        long ageOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("age"));
        boolean success = unsafe.compareAndSwapInt(stu, ageOffset, 23, 24);
        if (success) {
            System.out.println(stu);
        } else {
            System.out.println("修改失败");
        }

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

        @Override
        public String toString() {
            return "Student:" +
                    "name=" + name + ',' +
                    "age=" + age;
        }
    }
}
