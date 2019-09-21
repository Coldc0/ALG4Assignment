/******************************************************************************
 *  Name:              Zhang Shen
 *  Coursera User ID:  zhangshen2201@hotmail.com
 *  Last modified:     9/19/2019
 *****************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private double[] allx;
    private double mean;
    private double stddev;;
    private int trials;
    public PercolationStats(int n, int trials) {
        if(n<=0|| trials<=0)
            throw  new IllegalArgumentException();
        this.n = n;
        this.trials=trials;
        allx = new double[trials];
        int row, col;
        double xsum = 0;

        for (int i = 0; i < trials; i++) {
            Percolation fun = new Percolation(n);
            while (!fun.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                if (fun.isOpen(row, col)) continue;
                fun.open(row, col);
                allx[i]++;
            }
            allx[i]/=n*n;
            // xsum += count/(n*n);
        }
        mean = StdStats.mean(allx);
        stddev=StdStats.stddev(allx);

    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean-1.96*stddev/Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean+1.96*stddev/Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats fun = new PercolationStats(n, T);
        System.out.println("mean                    = " + fun.mean());
        System.out.println("stddev                  = " + fun.stddev());
        System.out.println(
                "95% confidence interval = [" + fun.confidenceLo() + ", " + fun.confidenceHi()
                        + "]");

    }
}
