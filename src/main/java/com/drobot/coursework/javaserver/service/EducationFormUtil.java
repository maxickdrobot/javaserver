package com.drobot.coursework.javaserver.service;

public class EducationFormUtil {
    public static String toGenitive(String form) {
        if (form == null) {
            return null;
        }

        switch (form.toLowerCase()) {
            case "денна":
                return "денної";
            case "заочна":
                return "заочної";
            default:
                throw new IllegalArgumentException("Невідома форма навчання: " + form);
        }
    }
}
