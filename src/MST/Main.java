package MST;

import java.util.Random;

public class Main {
    static Random rand = new Random();
    static final int amountOfNodes = 20;
    static final int amountOfEdges = 5;
    static int[][] graph = new int[20][20];

    public static void main(String[] args) {
        initGraph();
        Graph g = new Graph(amountOfNodes);
        g._graph = graph;
        print();

        System.out.println();
        System.out.println("MST:");
        // g.primMST();
        // g.updateMST();
    }

    public static void initGraph() {
        for (int row = 0; row < amountOfNodes; row++) {
            int rowEdgesIndex = 0;
            while (rowEdgesIndex < amountOfEdges) {
                int col = rand.nextInt(amountOfNodes);
                if (row != col && graph[row][col] == 0) {
                    int weight = rand.nextInt(10) + 10;
                    graph[row][col] = weight;
                    graph[col][row] = weight;
                    rowEdgesIndex++;
                }
            }
        }
    }

    public static void print() {
        System.out.println();
        System.out.println("The Graph:");

        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                if (graph[i][j] != 0) {
                    System.out.println((char) (97 + i) + " -> " + (char) (97 + j) + " [" + graph[i][j] + "]");
                }
            }
        }

    }
}