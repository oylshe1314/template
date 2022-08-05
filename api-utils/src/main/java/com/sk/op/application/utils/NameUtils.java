package com.sk.op.application.utils;

public abstract class NameUtils {

    public static String toUnderscore(String name) {
        StringBuilder sb = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append('_');
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String toCamelcase(String name) {
        StringBuilder sb = new StringBuilder();
        char[] cs = name.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == '_') {
                i++;
                sb.append(Character.toUpperCase(cs[i]));
            } else {
                sb.append(cs[i]);
            }
        }
        return sb.toString();
    }

    public static String firstUpperCase(String name) {
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
}
