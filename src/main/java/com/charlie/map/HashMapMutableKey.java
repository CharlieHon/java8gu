package com.charlie.map;

import java.util.HashMap;
import java.util.Objects;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/11 15:55
 * @Description: HashMap 的 key 可以为 null，但是 key 必须是 不可变类型。否则 key 值修改后，就无法找到对应的 key
 */
public class HashMapMutableKey {
    public static void main(String[] args) {
        HashMap<Student, Object> map = new HashMap<>();
        Student stu = new Student("charlie", 18);
        map.put(stu, new Object());

        // java.lang.Object@1e643faf
        System.out.println(map.get(stu));

        stu.age = 24;
        // null
        System.out.println(map.get(stu));
    }

    static class Student {
        String name;
        int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Student student = (Student) obj;
            return age == student.age && Objects.equals(name, student.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
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
