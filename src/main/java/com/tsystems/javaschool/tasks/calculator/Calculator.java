package com.tsystems.javaschool.tasks.calculator;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        CheckIfLegal checkIfLegal = new CheckIfLegal(statement);
        if (checkIfLegal.check() == null) return null;

        ConverterToList ctl = new ConverterToList(checkIfLegal.check());
        ArrayList<String> converted_input = this.convert(ctl.toList());
        if (converted_input == null) return null;

        Stack<Double> stack =  new Stack<>();
        Set<String> ops = new HashSet<>();
        ops.add("+"); ops.add("-"); ops.add("*"); ops.add("/");

        for (String element : converted_input) {
            if (ops.contains(element)) {
                Double b = stack.pop();
                Double a = stack.pop();
                if (calculate(a, b, element) == null) return null;
                stack.push(calculate(a, b, element));
            } else {
                stack.push(Double.valueOf(element));
            }
        }
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        String output = df.format(stack.pop());
        String fix_output = "";
        for (int i = 0; i < output.length(); i++) {
            if (output.charAt(i) == ',') fix_output += ".";
            else fix_output += output.charAt(i);
        }
        return fix_output;
    }

    private Double calculate(Double a, Double b, String op) {
        double result = 0;
        if ("+".equals(op)) result = a + b;
        if ("-".equals(op)) result = a - b;
        if ("*".equals(op)) result = a * b;
        if ("/".equals(op)) {
            if (b == 0) return null;
            else result = a / b;
        }
        return result;
    }

    private ArrayList<String> convert(ArrayList<String> input) {
        if (input == null) return null;
        Map<String, Integer> priority = new HashMap<>();
        Stack<String> ops = new Stack<>();
        Queue<String> conv = new LinkedList<>();
        priority.put("(", 0);
        priority.put("+", 1); priority.put("-", 1);
        priority.put("*", 2); priority.put("/", 2);
        for (String i : input) {
            if (i.equals("(")) {
                ops.push(i);
                continue;
            }
            if (i.equals(")")) {
                while (!ops.peek().equals("(")) {
                    conv.add(ops.pop());
                }
                ops.pop();
                continue;
            }
            if (priority.containsKey(i)) {
                while (!ops.empty() && priority.get(i) <= priority.get(ops.peek())) {
                    conv.add(ops.pop());
                }
                ops.push(i);
                continue;
            }
            if (isNumber(i)) {
                conv.add(i);
                continue;
            }
            throw new IllegalArgumentException("Invalid input");
        }
        while (!ops.isEmpty()) {
            conv.add(ops.pop());
        }
        return new ArrayList<String>(conv);
    }

    private static boolean isNumber(String s) {
        Pattern pattern = Pattern.compile("^[\\d\\.]+$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

}
