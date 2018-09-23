package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */

    public boolean find(List x, List y) throws IllegalArgumentException {
        if (x == null || y == null) throw new IllegalArgumentException();
        if (x.size() == 0) return true;
        int i = 0;
        List <Object> z = new ArrayList<>();
        for (Object element : y) {
            if (element.equals(x.get(i))) {
                z.add(element);
                i++;
                if (i == x.size()) break;
            }
        }
        if (z.size() == x.size()) return true;
        return false;
    }
}
