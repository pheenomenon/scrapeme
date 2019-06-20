package com.statuspage.project.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageValidator {
    private static final String imagePattern = "([^\\s]+(\\.(?i)(jpg|gif|png))$)";
    private static Pattern pattern;
    private static Matcher matcher;

    static {
        pattern = Pattern.compile(ImageValidator.imagePattern);
    }
    public static boolean validate(String imgUrl) {
        matcher = pattern.matcher(imgUrl);
        return matcher.matches();
    }

    public ImageValidator() {

    }
}