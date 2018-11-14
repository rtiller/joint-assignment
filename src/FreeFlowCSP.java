/*
 * CSCI 446 A.I.
 * Completed by: Robert Tiller, Kyle Ungersma, Jason Armstrong, Beau Anderson
 */


import java.util.ArrayList;
import java.util.HashMap;

public class FreeFlowCSP
{
 
    protected final Maze maze;
    protected final ArrayList<Character> domain;

    public FreeFlowCSP(Maze mazeIn) 
    {
        maze = mazeIn;
        domain = mazeIn.domain;
    }
    
    public Maze solveMaze() 
    {
        if(maze.completed()) 
        {
            return maze;
        }
        
        Node temp = getNode();
        
        for(char value: temp.domain) 
        {
            
            temp.setColor(value);
            
            if(mazeConstraints()) 
            {
                System.out.println(maze+"\n");
                Maze result = solveMaze();
                if(result != null) 
                {
                    return result;
                }
                temp.resetColor();
            } 
            else 
            {
                temp.resetColor();
            }
        }
        return null;
    }
    
     public Node getNode() 
    {
        for(int x=0; x<maze.width; x++) 
        {
            for(int y=0; y<maze.height; y++) 
            {
                Node cell = maze.coorindates(x, y);
                if(!cell.visited())
                {
                return cell;
                }
            }
        }
        return null;
    }
   
    
    public boolean mazeConstraints() 
    {
        for(int x=0; x<maze.width; x++) 
        {
            for(int y=0; y<maze.height; y++) 
            {
                Node cell = maze.coorindates(x, y);
                if(!cardinalityConstraint(cell)) 
                {
                    return false;
                }
                if(!connectedToSourceConstraint(cell)) 
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean cardinalityConstraint(Node cell) {
        HashMap<Character, Integer> neighborColors = cell.getNeighborColors();
        boolean hasUnassignedNeighbor = neighborColors.containsKey(Node.EmptyCell);
        boolean isSource = cell.isSource();
        boolean visited = cell.visited();

        if(!visited) return true;

        if(neighborColors.containsKey(cell.getColor())) {
            if(neighborColors.get(cell.getColor()) > 2) return false;
        }

        if(!hasUnassignedNeighbor && visited) {
            // All cells with no unassigned neighbors needs to contain at least one neighbor of the same color...
            if(!neighborColors.containsKey(cell.getColor())) return false;

            // If the cell is a source, it should have specifically one neighbor of the same color.
            if(isSource) 
            {
                if(neighborColors.get(cell.getColor()) != 1) return false;
                // If the cell is not a source but is assigned, it should have exactly two neighbors with the same color.
            } 
            else 
            {
                if(neighborColors.get(cell.getColor()) != 2) return false;
            }
        }
        // Otherwise, all cardinality constraints have been met.
        return true;
    }

    public boolean connectedToSourceConstraint(Node cell) {
        if(cell.isSource() || !cell.visited()) return true;
        return hasPathToSource(cell, new ArrayList<>());
    }

    private boolean hasPathToSource(Node cell, ArrayList<Node> visited) {
        HashMap<Character, Integer> neighborColors = cell.getNeighborColors();
        if(neighborColors.containsKey(Node.EmptyCell)) return true;
        if(neighborColors.containsKey(cell.getColor())) return true;
        visited.add(cell);

        for(Node neighbor: cell.getNeighbors()) 
        {
            if(neighbor.getColor() == cell.getColor() && !visited.contains(neighbor)) 
            {
                if(hasPathToSource(neighbor, visited)) 
                {
                    return true;
                }
            }
        }
        return false;
    }
    
}
