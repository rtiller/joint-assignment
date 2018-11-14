/*
 * CSCI 446 A.I.
 * Completed by: Robert Tiller, Kyle Ungersma, Jason Armstrong, Beau Anderson
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
    char mazeChar;
    int x;
    int y;
    char tempChar;
    boolean start;
    boolean assigned;
    ArrayList<Node> neighbors;
    ArrayList<Character> domain;
    static char EmptyCell = '_';

    private Node(int x, int y, char mazeChar) {
        this.x = x;
        this.y = y;
        this.mazeChar = mazeChar;
        this.tempChar = mazeChar;
        this.start = mazeChar != EmptyCell;
        this.assigned = this.start;
        this.neighbors = new ArrayList<>();
        this.domain = new ArrayList<>();
        
    }

    public static Node createSourceCell(int x, int y, char color) {
        return new Node(x, y, color);
    }

    public static Node createEmptyCell(int x, int y) {
        return new Node(x, y, EmptyCell);
    }

    public void addNeighbor(Node neighbor) {
        this.neighbors.add(neighbor);
    }

    public void updateDomain(ArrayList<Character> fullDomain) {
        domain.clear();
        HashMap<Character, Integer> colorCounts = getNeighborColors();
        ArrayList<Character> neighborColors = new ArrayList<>();

        neighborColors.addAll(colorCounts.keySet());

        for(char color: fullDomain) {
            if(color == EmptyCell) continue;
            setColor(color);

            boolean neighborsConstraintsMet = true;
            for(Node neighbor: getNeighbors()) {
                if(!cardinalityConstraint(neighbor) || !connectedToSourceConstraint(neighbor)) {
                    neighborsConstraintsMet = false;
                }
            }

            if(neighborsConstraintsMet) {
                domain.add(color);
            }
            resetColor();
        }
    }

    public void updateNeighborDomains(ArrayList<Character> fullDomain) {
        for(Node neighbor: getNeighbors()) {
            if(neighbor.visited()) continue;
            neighbor.updateDomain(fullDomain);
        }
    }

    public void setColor(char color) {
        if(this.start) throw new RuntimeException("Cannot assign a source cell a new color.");
        if(this.assigned) throw new RuntimeException("Cannot reassign a cell's color without resetting it first.");
        this.tempChar = color;
        this.assigned = true;
    }

    public void resetColor() {
        if(this.start) throw new RuntimeException("Cannot reset a source cell's color.");
        if(!this.assigned) throw new RuntimeException("Cannot unassign a cell's color that has not yet been assigned.");
        this.tempChar = mazeChar;
        this.assigned = false;
    }

    public char getColor() {
        return this.tempChar;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public ArrayList<Character> getDomain() {
        return this.domain;
    }

    public ArrayList<Node> getNeighbors() {
        return this.neighbors;
    }

    public HashMap<Character, Integer> getNeighborColors() {
        HashMap<Character, Integer> colorCounts = new HashMap<>();
        for(Node neighbor: neighbors) {
            if(colorCounts.containsKey(neighbor.getColor())) {
                int previousCount = colorCounts.get(neighbor.getColor());
                colorCounts.put(neighbor.getColor(), previousCount+1);
            } else {
                colorCounts.put(neighbor.getColor(), 1);
            }
        }
        return colorCounts;
    }

    public boolean isSource() {
        return this.start;
    }

    public boolean visited() {
        return this.assigned;
    }

    public boolean isComplete() {
        for(Node neighbor: neighbors) {
            if(!neighbor.visited()) return false;
        }
        return true;
    }

    public String toString() {
        return Character.toString(this.tempChar);
    }
}
