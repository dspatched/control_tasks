package com.tsystems.javaschool.tasks.calculator;

import java.util.regex.*;

public class CheckIfLegal {

    private String str;

    public CheckIfLegal(String input) {
        super();
        this.str = input;
    }

    public String check() {
        this.simpleParse();
        this.correctOperators();
        this.correctParenthesis();
        this.correctDots();
        this.correctUnary();
        return this.str;
    }

    private void simpleParse() {
        if (this.str != null) {
            Pattern pattern = Pattern.compile("^[\\d\\-\\+)\\*\\/\\(\\)\\.]+$");
            Matcher matcher = pattern.matcher(this.str);
            if (matcher.matches() == false) this.str = null;
        }
    }

    private void correctDots() {
        if (this.str != null) {
            String input = this.str;
            Pattern pattern = Pattern.compile("^\\d\\.\\d$");
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '.') {
                    if ((i == 0) || (i == input.length() - 1)) {
                        this.str = null;
                    } else {
                        Matcher matcher = pattern.matcher(input.substring(i - 1, i + 2));
                        if (matcher.matches() == false) this.str = null;
                    }
                }
            }
        }
    }

    private void correctOperators() {
        if (this.str != null) {
            String input = this.str;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '+' || c == '-' || c == '/' || c == '*') {
                    if (i == input.length() - 1) {
                        this.str = null;
                    } else {
                        char nc = input.charAt(i + 1);
                        if (nc == '+' || nc == '-' || nc == '/' || nc == '*') this.str = null;
                    }
                }
            }
        }
    }

    private void correctParenthesis() {
        if (this.str != null) {
            String input = this.str;
            int count = 0;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '(') {
                    count += 1;
                    if (i != 0) {
                        char pc = input.charAt(i-1);
                        if (pc != '+' && pc != '-' && pc != '/' && pc != '*' && pc != '(' && pc != ')') this.str = null;
                    }
                }
                if (c == ')') {
                    count -= 1;
                    if (i != input.length()-1) {
                        char nc = input.charAt(i+1);
                        if (nc != '+' && nc != '-' && nc != '/' && nc != '*' && nc != '(' && nc != ')') this.str = null;
                    }
                }
                if (count < 0) this.str = null;
            }
            if (count != 0) this.str = null;
        }
    }

    private void correctUnary() {
        if (this.str != null) {
            String input = this.str;
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '+' || c == '-' || c == '/' || c == '*') {
                    if (i == 0) {
                        if (c == '/' || c == '*') this.str = null;
                        if (c == '+') continue;
                        if (c == '-') output.append(c);
                    } else if (input.charAt(i - 1) == '(') {
                        if (c == '/' || c == '*') this.str = null;
                        if (c == '+') continue;
                        if (c == '-') output.append(c);
                    } else {
                        output.append(c);
                    }
                } else output.append(c);
            }
            this.str = output.toString();
        }
    }
}

