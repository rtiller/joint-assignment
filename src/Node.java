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
    char color;
    boolean start;
    boolean visited;
    ArrayList<Node> neighbors;
    ArrayList<Character> domain;
    static char EmptyCell = '_';

    private Node(int x, int y, char mazeChar) {
        this.x = x;
        this.y = y;
        this.mazeChar = mazeChar;
        this.color = mazeChar;
        this.start = mazeChar != EmptyCell;
        this.visited = this.start;
        this.neighbors = new ArrayList<>();
        this.domain = new ArrayList<>();
        
    }

    public static Node createSourceCell(int x, int y, char color) 
    {
        return new Node(x, y, color);
    }

    public static Node createEmptyCell(int x, int y) 
    {
        return new Node(x, y, EmptyCell);
    }

    public void addNeighbor(Node neighbor) 
    {
        this.neighbors.add(neighbor);
    }

    public void updateDomain(ArrayList<Character> fullDomain) 
    {
        domain.clear();
        HashMap<Character, Integer> colorCounts = getNeighborColors();
        ArrayList<Character> neighborColors = new ArrayList<>();

        neighborColors.addAll(colorCounts.keySet());

        for(char color: fullDomain) 
        {
            if(color == EmptyCell) 
            {
                continue;
            }
            setColor(color);

            boolean neighborsConstraintsMet = true;
            for(Node neighbor: neighbors) 
            {
                if(!cardinalityConstraint(neighbor) || !connectedToSourceConstraint(neighbor)) 
                {
                    neighborsConstraintsMet = false;
                }
            }

            if(neighborsConstraintsMet) 
            {
                domain.add(color);
            }
            resetColor();
        }
    }

    public void updateNeighborDomains(ArrayList<Character> fullDomain) 
    {
        for(Node neighbor: neighbors) 
        {
            if(neighbor.visited) 
            {
                continue;
            }
            
            neighbor.updateDomain(fullDomain);
        }
    }

    public boolean cardinalityConstraint(Node cell) 
    {
        HashMap<Character, Integer> neighborColors = cell.getNeighborColors();

        boolean hasUnassignedNeighbor = neighborColors.containsKey(Node.EmptyCell);
        boolean isSource = cell.start;
        boolean visited = cell.visited;

        if(!visited) return true;

        if(neighborColors.containsKey(cell.color)) 
        {
            if(neighborColors.get(cell.color) > 2) 
            {
                return false;
            }
        }

        if(!hasUnassignedNeighbor && visited) {
            // All cells with no unassigned neighbors needs to contain at least one neighbor of the same color...
            if(!neighborColors.containsKey(cell.color)) 
            {
                return false;
            }

            // If the cell is a source, it should have specifically one neighbor of the same color.
            if(isSource) {
                if(neighborColors.get(cell.color) != 1) 
                {
                    return false;
                }
                // If the cell is not a source but is assigned, it should have exactly two neighbors with the same color.
            } else {
                if(neighborColors.get(cell.color) != 2) 
                {
                    return false;
                }
            }
        }

        // Otherwise, all cardinality constraints have been met.
        return true;
    }

    public boolean connectedToSourceConstraint(Node cell) 
    {
        if(cell.start || !cell.visited) 
        {
            return true;
        }
        return hasPathToSource(cell, new ArrayList<>());
    }

    private boolean hasPathToSource(Node cell, ArrayList<Node> visited) 
    {
        HashMap<Character, Integer> neighborColors = cell.getNeighborColors();
        if(neighborColors.containsKey(Node.EmptyCell)) 
        {
            return true;
        }
        
        if(neighborColors.containsKey(cell.color)) 
        {
            return true;
        }

        visited.add(cell);

        for(Node neighbor: cell.neighbors) 
        {
            if(neighbor.color == cell.color && !visited.contains(neighbor)) 
            {
                if(hasPathToSource(neighbor, visited)) 
                {
                    return true;
                }
            }
        }

        return false;
    }

    public void setColor(char color) 
    {
        if(this.start) throw new RuntimeException("Cannot assign a source cell a new color.");
        if(this.visited) throw new RuntimeException("Cannot reassign a cell's color without resetting it first.");
        this.color = color;
        this.visited = true;
    }

    public void resetColor() 
    {
        if(this.start) throw new RuntimeException("Cannot reset a source cell's color.");
        if(!this.visited) throw new RuntimeException("Cannot unassign a cell's color that has not yet been assigned.");
        this.color = mazeChar;
        this.visited = false;
    }

    public HashMap<Character, Integer> getNeighborColors() 
    {
        HashMap<Character, Integer> colorCounts = new HashMap<>();
        for(Node neighbor: neighbors) 
        {
            if(colorCounts.containsKey(neighbor.color)) 
            {
                int previousCount = colorCounts.get(neighbor.color);
                colorCounts.put(neighbor.color, previousCount+1);
            } 
            else 
            {
                colorCounts.put(neighbor.color, 1);
            }
        }
        return colorCounts;
    }

    

    public boolean isComplete() 
    {
        for(Node neighbor: neighbors) 
        {
            if(!neighbor.visited) 
            {
                return false;
            }
        }
        return true;
    }

    public String toString() 
    {
        return Character.toString(this.color);
    }
}
