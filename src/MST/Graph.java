package MST;

import java.util.*;

class Graph {
    private int _sizeOfVertex;
    private int[][] _mst;
    private int[] _parent;

    int[][] _graph;
    Random rand = new Random();

    public Graph(int size) {
        this._sizeOfVertex = size;
        this._graph = new int[size][size];
        this._mst = new int[size][size];
        this._parent = new int[size];
    }

    public void primMST() {
        int[][] key = new int[this._sizeOfVertex][2];
        boolean[] inMst = new boolean[this._sizeOfVertex];
        Arrays.fill(inMst, false);
        Arrays.fill(this._parent, -1);

        for (int j = 0; j < this._sizeOfVertex; ++j) {
            key[j][0] = j;
            key[j][1] = Integer.MAX_VALUE;
        }

        key[0][1] = 0;
        Queue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(x -> x[1]));

        for (int j = 0; j < this._sizeOfVertex; ++j) {
            heap.add(key[j]);
        }

        while (!heap.isEmpty()) {
            int[] min = heap.poll();

            for (int j = 0; j < this._sizeOfVertex; j++) {
                if (this._graph[min[0]][j] != 0 && !inMst[j] && this._graph[min[0]][j] < key[j][1]) {
                    key[j][1] = this._graph[min[0]][j];
                    heap.offer(key[j]);
                    this._parent[j] = min[0];
                }
            }

            inMst[min[0]] = true;
        }

        this.createMST();
        this.printGraph();
    }

    public void createMST() {
        for (int i = 1; i < this._sizeOfVertex; ++i) {
            if (this._parent[i] != -1) {
                this._mst[this._parent[i]][i] = this._graph[i][this._parent[i]];
                this._mst[i][this._parent[i]] = this._graph[i][this._parent[i]];
            }
        }

    }

    public void updateMST() {
        this.newMaxEdge();//section 1
        this.newMinEdge();//section 2
    }

    public void newMaxEdge() {
        int row = -1;
        int col = -1;
        boolean findNewEdge = false;

        while (!findNewEdge) {
            row = this.rand.nextInt(this._sizeOfVertex);
            col = this.rand.nextInt(this._sizeOfVertex);
            if (this._mst[row][col] == 0 && row != col && this._graph[row][col] == 0) {
                findNewEdge = true;
            }
        }

        System.out.println();
        System.out.println("New edge: " + (char) (97 + row) + " -> " + (char) (97 + col) + " [" + (this._sizeOfVertex + 10) + "]");
        System.out.println("update the MST: ");
        this.printGraph();
    }

    public void newMinEdge() {
        int row = -1, col = -1;
        boolean findEdge = false;

        while (!findEdge) {//search a new edge
            row = rand.nextInt(this._sizeOfVertex);
            col = rand.nextInt(this._sizeOfVertex);
            if (this._mst[row][col] == 0 && row != col && this._graph[row][col] == 0)
                findEdge = true;

        }

        int[] previous = BFS(row);

        this._mst[row][col] = 2;
        this._mst[col][row] = 2;

        int start = col;

        int maxCircleI = start;
        int maxCircleJ = previous[start];

        //run on the circle and find the max edge
        while (start != row) {
            start = previous[start];

            if (start != row) {
                int currentW = this._mst[start][previous[start]];
                if (currentW > this._mst[maxCircleI][maxCircleJ]) {
                    maxCircleI = start;
                    maxCircleJ = previous[start];
                }
            }
        }

        //remove the max edge
        this._mst[maxCircleI][maxCircleJ] = 0;
        this._mst[maxCircleJ][maxCircleI] = 0;

        ///find the source in the old graph
        int minusOneIndex = 0;
        while (this._parent[minusOneIndex] != -1) {
            minusOneIndex++;
        }

        this._parent = BFS(minusOneIndex);//update the parent

        System.out.println("\nNew edge: " + (char) (97 + row) + " -> " + (char) (97 + col) + " [2]");
        System.out.println("Update The MST:");
        printGraph();
    }

    public int[] BFS(int s) {
        boolean[] visited = new boolean[this._sizeOfVertex];
        int[] previous = new int[this._sizeOfVertex];
        Arrays.fill(previous, -1);
        Queue<Integer> queue = new PriorityQueue<>();
        visited[s] = true;
        queue.add(s);

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (int i = 0; i < this._sizeOfVertex; ++i) {
                if (this._mst[v][i] > 0 && !visited[i]) {
                    queue.add(i);
                    previous[i] = v;
                    visited[i] = true;
                }
            }
        }

        return previous;
    }

    public void printGraph() {
        for (int i = 1; i < this._sizeOfVertex; ++i) {
            System.out.println((char) (97 + this._parent[i]) + " -> " + (char) (97 + i) + " [" + this._mst[i][this._parent[i]] + "]");
        }

    }
}
