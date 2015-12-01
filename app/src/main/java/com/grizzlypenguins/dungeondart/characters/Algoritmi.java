package com.grizzlypenguins.dungeondart.characters;

/**
 * Created by Darko on 01.12.2015.
 */
import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.myFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Edge implements Comparable<Edge> {

    int v1;
    int v2;
    double weight;

    Edge(int v1, int v2, double weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    int getFrom() {
        return v1;
    }

    int getTo() {
        return v2;
    }

    @Override
    public int compareTo(Edge o) {
        // po rastechki redosled
        if (weight < o.weight) {
            return -1;
        }
        if (weight > o.weight) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Edge:" + "v1: " + v1 + ", v2: " + v2 + ", weight:" + weight;
    }
}


class Graph {

    static double INFINITY = 1000000000;
    private int brJazli; // broj na jazli
    private int brRebra; // broj na rebra
    ArrayList<Edge> neighbours[]; // lista na site sosedi na site jazli
    HashMap<String, Edge> hm; // za da pristapime kon vrska vo konstantno vreme

    String encode(int i, int j) {
        return Integer.toString(i) + " " + Integer.toString(j);
    }

    int getBrJazli() {
        return brJazli;
    }

    int getE() {
        return brRebra;
    }

    Graph(int brJazli) {
        this.brJazli = brJazli;
        neighbours = new ArrayList[brJazli];
        for (int i = 0; i < brJazli; i++) {
            neighbours[i] = new ArrayList<Edge>(); // sosedi na i-tiot jazol
        }
        brRebra = 0;
        hm = new HashMap<String, Edge>();
    }

    void addEdge(int v1, int v2, double weight) {
        Edge e = new Edge(v1, v2, weight);
        addEdge(e);
    }

    void addEdge(Edge e) {
        neighbours[e.v1].add(e);
        hm.put(encode(e.v1, e.v2), e);
        brRebra++;
    }

    void deleteEdge(int v1, int v2) {
        Edge e = hm.get(encode(v1, v2));
        if (e != null) {
            deleteEdge(e);
        }
    }

    void deleteEdge(Edge e) {
        neighbours[e.v1].remove(e);
        hm.remove(encode(e.v1, e.v2));
        brRebra--;
    }

    // it is used only in graphs that do not have more than one edge
    // between two jazli
    Edge getEdge(int v1, int v2) {
        return hm.get(encode(v1, v2));
    }

    // dfs
    // bfs
    // kruskal
    // prim
    // dijkstra
    // floyd-warshall
    // bellman-ford
    // topological sort
    // this method generates array of all edges in the graph
    public Edge[] getAllEdges() {
        int i, j = 0;

        int totalEdges = 0;
        for (i = 0; i < brJazli; i++) {
            totalEdges += neighbours[i].size();
        }

        // this is NEORIENTIRAN graph
        totalEdges /= 2;

        Edge edges[] = new Edge[totalEdges];

        for (i = 0; i < brJazli; i++) {

            Iterator<Edge> it = neighbours[i].iterator();
            while (it.hasNext()) {
                Edge current = it.next();
                if (current.v2 > current.v1) {
                    edges[j] = new Edge(current.v1, current.v2, current.weight);
                    j++;
                }
            }

        }

        return edges;
    }

    /**
     * ********************KRUSKAL********************************************************
     */
    // Kruskal vraka lista od Edges koi go gradat MST
    // brojot na takvi rebra e size-ot na taa lista
    // sumata e presmetana vo funk. print  SUM od site edge.weight
    ArrayList<Edge> kruskal() {
        int i;

        Edge edges[] = getAllEdges();
        // the edges are sorted in ascending order
        Arrays.sort(edges); // demek topoloshko sortiranje

        // da proverime dali ima vrska megu teme x i teme y
        // namesto vo n-vreme go reshavame vo konstantno vreme zatoa koristime UnionFind
        UnionFind uf = new UnionFind(brJazli);
        // list that contains all mst edges
        ArrayList<Edge> kruskalList = new ArrayList<Edge>();
        for (i = 0; i < edges.length; i++) {
            Edge momRebro = edges[i];
            if (uf.find(momRebro.getFrom()) != uf.find(momRebro.getTo())) {
                kruskalList.add(momRebro);
                uf.union(momRebro.getFrom(), momRebro.getTo());
            }
            if (kruskalList.size() == brJazli - 1) {
                break;
            }
        }
        return kruskalList;
    }
    // vrakja  najmaloto rebro od celiot graph

    private Edge getMinimalEdge(boolean included[]) {
        int i, j, k;
        int index1 = Integer.MAX_VALUE;
        int index2 = Integer.MAX_VALUE;
        double minweight = INFINITY;

        for (i = 0; i < brJazli; i++) {
            if (included[i]) {
                Iterator<Edge> it = neighbours[i].iterator();
                while (it.hasNext()) {
                    Edge current = it.next();
                    if ((included[current.getTo()] == false) && (current.weight < minweight)) {
                        index1 = i;
                        index2 = current.getTo();
                        minweight = current.weight;
                    }
                }

            }
        }

        if (minweight < INFINITY) {
            Edge r = new Edge(index1, index2, minweight);
            return r;
        }

        return null;
    }

    ArrayList<Edge> prim(int startIndex) {
        int i;

        ArrayList<Edge> mstEdges = new ArrayList<Edge>();
        boolean included[] = new boolean[brJazli];

        for (i = 0; i < brJazli; i++) {
            included[i] = false;
        }

        included[startIndex] = true;

        for (i = 0; i < brJazli - 1; i++) {
            Edge e = getMinimalEdge(included);
            if (e == null) {
                System.out.println("All nodes cannot be connected together.");
                break;
            }
            included[e.getFrom()] = true;
            included[e.getTo()] = true;
            mstEdges.add(e);
        }

        return mstEdges;
    }
    Jazol dijkstraArray[];

    // dijkstra SO MNOZENJE ZA ZAD GOLEMATA VODA
    void dijkstraOdEdnoTeme(int source) {
        int i;

        dijkstraArray = new Jazol[brJazli];

        for (i = 0; i < brJazli; i++) {
            dijkstraArray[i] = new Jazol(i);
        }
        dijkstraArray[source].minDistance = 1;// vo start ima 1 litar voda

        PriorityQueue<Jazol> q = new PriorityQueue<Jazol>();
        q.add(dijkstraArray[source]);

        while (!q.isEmpty()) {
            Jazol momJazol = q.poll();

            for (Edge e : neighbours[momJazol.index]) {
                Jazol v = dijkstraArray[e.v2]; //daj mi go sosedot
                double weight = e.weight;
                double newDistance = momJazol.minDistance + weight;

                if (newDistance < v.minDistance) {
                    q.remove(v);
                    v.minDistance = newDistance;
                    v.previous = momJazol;
                    q.add(v);
                }
            }
        }

    }

    ArrayList<Jazol> getShortestPathTo(int target) {

        ArrayList<Jazol> path = new ArrayList<Jazol>();

        for (Jazol vertex = dijkstraArray[target]; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }
    // JA VRAKJA PATEKATA SPORED DIJKSTRA

    public ArrayList<Integer> getPateka(int target) {

        ArrayList<Integer> path = new ArrayList<Integer>();

        for (Jazol vertex = dijkstraArray[target]; vertex != null; vertex = vertex.previous) {
            path.add(vertex.index);
        }

        Collections.reverse(path);
        return path;
    }

    // floyd-warshall
    double[][] getAllShortestPaths() {
        int i, j, k;

        double dist[][] = new double[brJazli][brJazli];

        for (i = 0; i < brJazli; i++) {
            for (j = 0; j < brJazli; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    Edge e = getEdge(i, j);
                    if (e == null) {
                        dist[i][j] = 1000000000;
                    } else {
                        dist[i][j] = e.weight;
                    }
                }
            }
        }

        for (k = 0; k < brJazli; k++) {
            for (i = 0; i < brJazli; i++) {
                for (j = 0; j < brJazli; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }
    public static Jazol bermanArray[];
    boolean bermanFord(int source) {
        int i, u, v;

        bermanArray = new Jazol[brJazli];

        for (i = 0; i < brJazli; i++) {
            bermanArray[i] = new Jazol(i);
        }
        bermanArray[source].minDistance = 0;

        for (i = 1; i < brJazli; i++) {
            for (u = 0; u < brJazli; u++) {
                for (Edge e : neighbours[u]) {
                    v = e.getTo();
                    if (bermanArray[v].minDistance > bermanArray[u].minDistance + e.weight) {
                        bermanArray[v].minDistance = bermanArray[u].minDistance + e.weight;
                        bermanArray[v].previous = bermanArray[u];
                    }
                }
            }
        }

        for (u = 0; u < brJazli; u++) {
            for (Edge e : neighbours[u]) {
                v = e.getTo();
                if (bermanArray[v].minDistance > bermanArray[u].minDistance + e.weight) {
                    // Graph contains a negative-weight cycle
                    return true;
                }
            }
        }

        return false;
    }
    ///////////////////// BELLMAN FORD///////////////
    public static Jazol bellmanArray[];

    public boolean bellmanFord(int index) {


        bellmanArray = new Jazol[brJazli];

        for (int i = 0; i < brJazli; i++) {
            bellmanArray[i] = new Jazol(i);
        }
        bellmanArray[index].minDistance = 0;
        // relax edges repeatedly
        for (int i = 0; i < brJazli; i++) {
            ArrayList<Edge> sosedi = getSosedi(i);
            for (Edge edge : sosedi) {
                relax(edge);
            }
        }

        // check for negative-weight cycles
        for (int i = 0; i < brJazli; i++) {
            for (Edge e : getSosedi(i)) {
                int v = e.getTo();
                if (bellmanArray[v].minDistance > bellmanArray[i].minDistance + e.weight) {
                    // Graph contains a negative-weight cycle
                    return true;
                }
            }
        }
        return false;
    }

    private static void relax(Edge edge) {
        int vTo = edge.getTo();
        int vfrom = edge.getFrom();
        if (bellmanArray[vTo].minDistance > bellmanArray[vfrom].minDistance + edge.weight) {
            bellmanArray[vTo].minDistance = bellmanArray[vfrom].minDistance + edge.weight;
            bellmanArray[vTo].previous = bellmanArray[vfrom];
        }
    }
    boolean taken[];

    void dfs(int source) {
        int i;
        taken = new boolean[brJazli];
        for (i = 0; i < brJazli; i++) {
            taken[i] = false;
        }

        dfsR(source);
    }

    void dfsR(int current) {
        taken[current] = true;

        // we process the vertex
        System.out.println(current);

        Iterator<Edge> it = neighbours[current].iterator();
        while (it.hasNext()) {
            Edge e = it.next();
            if (taken[e.getTo()] == false) {
                dfsR(e.getTo());
            }
        }
    }

    void bfs(int source) {
        int i;

        taken = new boolean[brJazli];
        for (i = 0; i < brJazli; i++) {
            taken[i] = false;
        }

        Queue<Integer> q = new LinkedList<Integer>();
        q.add(source);
        taken[source] = true;

        // we process the vertex
        System.out.println(source);

        while (!q.isEmpty()) {
            int current = q.poll();

            Iterator<Edge> it = neighbours[current].iterator();
            while (it.hasNext()) {
                Edge e = it.next();

                if (taken[e.getTo()] == false) {

                    // we process the vertex
                    System.out.println(e.getTo());

                    taken[e.getTo()] = true;
                    q.add(e.getTo());
                }
            }

        }
    }

    private ArrayList<Edge> getSosedi(int index) {
        return neighbours[index];


    }
}

class Jazol implements Comparable<Jazol> {

    int index;
    double minDistance;
    Jazol previous;

    Jazol(int index) {
        this.index = index;
        minDistance = Graph.INFINITY;
        previous = null;
    }

    @Override
    public int compareTo(Jazol o) {
        // rastecki redosled
        return Double.compare(this.minDistance, o.minDistance);
    }

    @Override
    public String toString() {
        return "Jazol: " + "index: " + index + ", minDistance: " + minDistance + ", previous: " + previous;
    }
}
class UnionFind {

    UnionFind(int N) {
        initialize(N);
    }

    void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);

        if (xRoot == yRoot) {
            return;
        }

        // x and y are not already in the same set. Merge them
        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot;
        } else if (rank[xRoot] > rank[yRoot]) {
            parent[yRoot] = xRoot;
        } else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    int parent[];
    int rank[];

    private void initialize(int N) {
        parent = new int[N];
        rank = new int[N];

        int i;
        for (i = 0; i < N; i++) {
            makeSet(i);
        }
    }

    void makeSet(int x) {
        parent[x] = x;
        rank[x] = 0;
    }

//    public static void main(String[] args) {
//        int i;
//
//        UnionFind uf = new UnionFind(10);
//
//        uf.union(0, 5);
//        uf.union(1, 2);
//        uf.union(2, 3);
//        uf.union(1, 4);
//        uf.union(3, 5);
//        uf.union(6, 7);
//        uf.union(6, 8);
//        uf.union(7, 9);
//
//        for (i=0;i<10;i++)
//            System.out.println(i+"\t"+uf.find(i));
//
//    }
}
public class Algoritmi {

    public static int brJazli, brRebra, start, end, voda;

    public static void main(String args[]) {

        int niza[][] = {{1,1,1,1,0},{1,1,1,1,0},{0,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}};


    }
    public ArrayList<Jazol> returnPath(int niza[][],MyPoint monster, MyPoint heroj){
        ArrayList<String>jazli = new ArrayList<String>();
        Graph g = new Graph(66);
        for(int  i = 0; i < niza.length; i++){
            for(int j = 0; j < niza[0].length; j++){
                ArrayList sosedi = getSosedi(i,j,niza);
                for(int k = 0; k < sosedi.size(); k++){
                    int from = i*10+j;
                    int to = Integer.parseInt(sosedi.get(k)+"");
                    g.addEdge(from, to, 1);
                    g.addEdge(to,from,1);
                }
            }
        }
        int from = monster.x* myFactory.TILENUMBER+monster.y;
        int to = heroj.x*myFactory.TILENUMBER+heroj.y;
        g.dijkstraOdEdnoTeme(from);
        ArrayList<Jazol> d = g.getShortestPathTo(to);
        /*for(int i = 0; i < d.size(); i++){
            System.out.println(d.get(i).index+" "+d.get(i).minDistance);
        }*/
        return d;
    }
    public static ArrayList<String> getSosedi(int x, int y, int niza[][]){
        ArrayList nizi = new ArrayList<>();
        int k = x; int j = y; int  p = y;
        if(x+1 < niza.length && y < niza.length ){
            if(niza[x+1][y] == 1){
                k+=1;
                nizi.add(k+""+j);
            }
        }
        if(x < niza.length && y+1 < niza.length ){
            if(niza[x][y+1] == 1){
                j+=1;
                nizi.add(x+""+j);
            }
        }
        return nizi;
    }
}