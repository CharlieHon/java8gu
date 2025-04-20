package com.charlie.pattern;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/12 14:44
 * @Description: 枚举类实现单例模式
 */
public enum Gender {
    MALE, FEMALE;
}

// public final class Gender extends Enum<Gender> {
//     public static final Gender MALE;
//     public static final Gender FEMALE;
//
//     private static final Gender[] $VALUES;
//
//     static {
//         MALE = new Gender("MALE", 0);
//         FEMALE = new Gender("FEMALE", 1);
//         $VALUES = values();
//     }
//
//     private Gender(String name, int ordinal) {
//         super(name, ordinal);
//     }
//
//     private static Gender[] $values() {
//         return new Gender[] {MALE, FEMALE};
//     }
//
//     public static Gender[] values() {
//         return $VALUES.clone();
//     }
//
//     public static Gender valueOf(String value) {
//         return Enum.valueOf(Gender.class, value);
//     }
// }
