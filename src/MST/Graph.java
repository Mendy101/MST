package MST;

import java.util.*;

public class Graph {
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
}
