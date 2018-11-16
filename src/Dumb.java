/*
 * CSCI 446 A.I.
 * Completed by: Robert Tiller, Kyle Ungersma, Jason Armstrong, Beau Anderson
 */


import java.util.ArrayList;
import java.util.HashMap;

public class Dumb
{
    
    public Maze maze;
    public ArrayList<Character> domain;
    
    public Dumb(Maze mazeIn)
    {
        maze = mazeIn;
        domain = mazeIn.domain;
    }
    
    public Maze solveMaze()
    {
        if (maze.isFilled())
        {
            return maze;
        }
        
        Node temp = pickNode();
        
        for (int i = 0; i < temp.domain.size(); i++) {
            char value = temp.domain.get(i);
            
            temp.setColor(value);
            
            if (mazeConstraints())
            {
                maze.printMaze();System.out.println();
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
    
    public Node pickNode()
    {
        for(int i=0; i<maze.width; i++)
        {
            for(int j=0; j<maze.height; j++)
            {
                Node cell = maze.getNode(i, j);
                if(!cell.visited)
                {
                    return cell;
                }
            }
        }
        return null;
    }
    
    
    public boolean mazeConstraints()
    {
        for(int i=0; i<maze.width; i++)
        {
            for(int j=0; j<maze.height; j++)
            {
                Node cell = maze.getNode(i, j);
                if(!constraints(cell))
                {
                    return false;
                }
                if(!sourceConstraints(cell))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean constraints(Node cell)
    {
        HashMap<Character, Integer> neighborColors = cell.neighborColor();
        
        boolean isSource = cell.start;
        boolean noNeighborAssignment = neighborColors.containsKey(Node.EmptyCell);
        boolean visited = cell.visited;
        
        if(!visited) return true;
        
        if(neighborColors.containsKey(cell.color))
        {
            if(neighborColors.get(cell.color) > 2) return false;
        }
        
        if(!noNeighborAssignment && visited)
        {
            // All cells with no unassigned neighbors needs to contain at least one neighbor of the same color...
            if(!neighborColors.containsKey(cell.color))
            {
                return false;
            }
            
            // If the cell is a source, it should have specifically one neighbor of the same color.
            if(isSource)
            {
                if(neighborColors.get(cell.color) != 1)
                {
                    return false;
                }
                // If the cell is not a source but is assigned, it should have exactly two neighbors with the same color.
            }
            else
            {
                if(neighborColors.get(cell.color) != 2)
                {
                    return false;
                }
            }
        }
        // Otherwise, all cardinality constraints have been met.
        return true;
    }
    
    public boolean sourceConstraints(Node cell)
    {
        if(cell.start || !cell.visited)
        {
            return true;
        }
        return path(cell, new ArrayList<>());
    }
    
    private boolean path(Node cell, ArrayList<Node> visited)
    {
        HashMap<Character, Integer> colorComparison = cell.neighborColor();
        if(colorComparison.containsKey(Node.EmptyCell))
        {
            return true;
        }
        if(colorComparison.containsKey(cell.color))
        {
            return true;
        }
        visited.add(cell);
        
        for(Node neighbor: cell.neighbors)
        {
            if(neighbor.color == cell.color && !visited.contains(neighbor))
            {
                if(path(neighbor, visited))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
}
