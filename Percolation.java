/******************************************************************************
 *  Name:              Zhang Shen
 *  Coursera User ID:  zhangshen2201@hotmail.com
 *  Last modified:     9/19/2019
 *****************************************************************************/
/**
 * 渗透问题：
 *  建立一个数组对每个连通分量进行编号,(0,n-1)，设置两个虚拟分量(top,rail)
 *  将第一排连通分量与top相连，将最后一行的连通分量与rail相连
 *  这样判断是否渗透仅需判断top和rail是否连通
 *  此时会出现backwash问题，即在连通后由于top与rail已经是连通的，此时若再打开与最后一行连通的
 *  节点即使没有连通到顶部也会被渗透
 *  解决方案是再建立另一个Percolation对象，只连接top虚拟节点，在isfull中只判断是否和第二个
 *  Percolation中的top相连接。
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[][] map;
    private WeightedQuickUnionUF fun;
    private WeightedQuickUnionUF fun2;
    private int n;
    private int top,rail1;
    private int opencount;
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("error");
        this.n = n;
        map = new boolean[n][n];
        int count = 0;
        fun = new WeightedQuickUnionUF(n * n + 10);
        fun2 = new WeightedQuickUnionUF(n * n + 10);
        top = n * n + 1;
        rail1=n*n+2;
        opencount=0;
    }
    private int getpos(int row,int col)
    {
        return (row-1)*n+col-1;
    }
    public void open(int row, int col)  {
        if (row < 1 || col < 1||row>n||col>n)
        {
            throw new IllegalArgumentException("error");
        }
        if (isOpen(row,col))
            return;
        map[row - 1][col - 1] = true;
        if ((row != 1) && isOpen(row - 1, col)) {
            fun.union(getpos(row-1,col), getpos(row,col));
            fun2.union(getpos(row-1,col), getpos(row,col));
        }
        if ((row != n) && isOpen(row + 1, col)) {
            fun.union(getpos(row+1,col), getpos(row,col));
            fun2.union(getpos(row+1,col), getpos(row,col));
        }
        if ((col != 1) && isOpen(row, col - 1)) {
            fun.union(getpos(row,col-1), getpos(row,col));
            fun2.union(getpos(row,col-1), getpos(row,col));
        }
        if ((col != n) && isOpen(row, col + 1)) {
            fun.union(getpos(row,col+1), getpos(row,col));
            fun2.union(getpos(row,col+1), getpos(row,col));
        }
        if (row == 1) {
            fun.union(top, getpos(row,col));
            fun2.union(top, getpos(row,col));
        }

        if (row == n) {
                fun.union(getpos(row, col),rail1);
        }
        opencount++;
        return;
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1||row>n||col>n)
            throw new IllegalArgumentException("error");
        return map[row - 1][col - 1] == true;
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1||row>n||col>n)
            throw new IllegalArgumentException("error");
        return fun2.connected(top, getpos(row,col));


    }
    public int numberOfOpenSites() {
        return opencount;
    }

    public boolean percolates() {
        //if(sign) return true;
        return fun.connected(rail1,top);
    }
    public static void main(String[] args) {

    }

}


// /******************************************************************************
//  *  Name:              Zhang Shen
//  *  Coursera User ID:  zhangshen2201@hotmail.com
//  *  Last modified:     9/19/2019
//  *****************************************************************************/
//
//
// import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//
// public class Percolation {
//     private int[][] map;
//     private int[][] sign;
//     private WeightedQuickUnionUF fun;
//     private WeightedQuickUnionUF fun2;
//     private int n;
//     private int top, rail;
//
//     public Percolation(int n) {
//         if (n <= 0)
//             throw new IllegalArgumentException("error");
//         this.n = n;
//         map = new int[n][n];
//         sign = new int[n][n];
//         int count = 0;
//         for (int i = 0; i < n; i++) {
//             for (int j = 0; j < n; j++) {
//                 map[i][j] = count;
//                 count++;
//             }
//         }
//         fun = new WeightedQuickUnionUF(n * n + 10);
//         fun2 = new WeightedQuickUnionUF( n + 10);
//         top = n * n + 1;
//         rail = n + 2;
//     }
//
//     public void open(int row, int col)  {
//         if (row < 1 || col < 1||row>n||col>n)
//         {
//             throw new IllegalArgumentException("error");
//         }
//
//         sign[row - 1][col - 1] = 1;
//         if ((row != 1) && isOpen(row - 1, col)) {
//             fun.union(map[row - 2][col - 1], map[row - 1][col - 1]);
//         }
//         if ((row != n) && isOpen(row + 1, col)) {
//             fun.union(map[row][col - 1], map[row - 1][col - 1]);
//         }
//         if ((col != 1) && isOpen(row, col - 1)) {
//             fun.union(map[row - 1][col - 2], map[row - 1][col - 1]);
//         }
//         if ((col != n) && isOpen(row, col + 1)) {
//             fun.union(map[row - 1][col], map[row - 1][col - 1]);
//         }
//         if (row == 1) {
//             fun.union(top, map[row - 1][col - 1]);
//         }
//
//         if (row == n) {
//             fun2.union(rail, col-1);
//         }
//         return;
//     }
//
//     public boolean isOpen(int row, int col) {
//         if (row < 1 || col < 1||row>n||col>n)
//             throw new IllegalArgumentException("error");
//         return sign[row - 1][col - 1] == 1;
//     }
//
//     public boolean isFull(int row, int col) {
//         if (row < 1 || col < 1||row>n||col>n)
//             throw new IllegalArgumentException("error");
//         return fun.connected(top, map[row - 1][col - 1]);
//     }
//
//     public int numberOfOpenSites() {
//         int num = 0;
//         int size = this.n * this.n + 1;
//         for (int i = 0; i < n; i++) {
//             for (int j = 0; j < n; j++) {
//                 if (sign[i][j]==1)
//                     num++;
//             }
//         }
//         return num;
//     }
//
//     public boolean percolates() {
//         for (int i = 0; i < n; i++) {
//             if (fun2.connected(i, rail))
//                 if (fun.connected(top, map[n - 1][i]))
//                     return true;
//         }
//         return false;
//
//     }
//     public static void main(String[] args) {
//
//     }
//
// }
//
