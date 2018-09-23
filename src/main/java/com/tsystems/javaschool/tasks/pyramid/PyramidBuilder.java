package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    private int rows;
    private int columns;
    private int max;

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    public int[][] buildPyramid(List<Integer> inputNumbers) throws CannotBuildPyramidException {
        this.check(inputNumbers.size());
        for (Object element : inputNumbers) {
            if (element == null) throw new CannotBuildPyramidException();
        }
        Collections.sort(inputNumbers);
        int [][] pyramid = new int[this.rows][this.columns];
        int number = this.max;
        int start = this.columns, stop = -2;
        outer: for (int i = this.rows-1; i > -1; i--) {
            start--; stop++;
            for (int j = start; j > stop; j-= 2) {
                pyramid[i][j] = inputNumbers.get(number-1);
                number--;
                if (number == 0) break outer;
            }
        }
        return pyramid;
    }

    private void check(int input) throws CannotBuildPyramidException {
        if (input < 3 || input > 100000) throw new CannotBuildPyramidException();
        boolean canBuild = false;
        double x = -1; int n = 1;
        while (input - x > -1) {
            x = (n*n + 3*n + 2)/2;
            if ((x - input) == 0) {
                this.rows = n + 1;
                this.columns = n*2 + 1;
                this.max = input;
                canBuild = true;
                break;
            }
            n += 1;
        }
        if (!canBuild) throw new CannotBuildPyramidException();
    }

}
