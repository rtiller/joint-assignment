/*
 * CSCI 446 A.I.
 * Completed by: Robert Tiller, Kyle Ungersma, Jason Armstrong, Beau Anderson
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Maze {
     
     Node[][]maze;

   ArrayList<Character> domain;

     int width;
     int height;



    private Maze(Node[][] mazeIn, ArrayList<Character> domain) 
    {
        this.maze = mazeIn;
        this.domain = domain;
        this.width = mazeIn[0].length;
        this.height = mazeIn.length;
    }



    public static Maze createFromInput(Scanner input, int width, int height) 
    {
        Node[][] grid = new Node[height][width];
        ArrayList<Character> domain = new ArrayList<>();

        for(int row=0; row<height; row++) 
        {
            // Extract line of input as array of characters (values/non-value).
            char[] line = input.nextLine().toCharArray();

            // Assign objects to grid based on each value/non-value.
            for(int col=0; col<width; col++) 
            {
                char cell = line[col];

                // If the cell's symbol refers to empty...
                if(cell == Node.EmptyCell)
                {
                    // Create empty cell.
                    grid[row][col] = Node.createEmptyCell(col, row);
                    // Otherwise...
                } 
                else 
                {
                    // Create source cell and add value to domain if not already included.
                    grid[row][col] = Node.createSourceCell(col, row, cell);
                    if(!domain.contains(cell)) domain.add(cell);
                }
            }
        }

        for(int x=0; x<width; x++) 
        {
            for(int y=0; y<height; y++) 
            {
                Node cell = grid[y][x];
                // Add neighbors of cell, if exist...
                if(x-1 >= 0)        cell.addNeighbor(grid[y][x-1]);
                if(x+1 <= width-1)  cell.addNeighbor(grid[y][x+1]);
                if(y-1 >= 0)        cell.addNeighbor(grid[y-1][x]);
                if(y+1 <= height-1) cell.addNeighbor(grid[y+1][x]);
            }
        }

        // Now update domains
        for(int x=0; x<width; x++) 
        {
            for(int y=0; y<height; y++) 
            {
                Node cell = grid[y][x];
                if(!cell.visited) cell.updateDomain(domain);
            }
        }
        return new Maze(grid, domain);
    }

    public Node coorindates(int x, int y) 
    {
        return this.maze[y][x];
    }

    public boolean completed() 
    {
        for(int x=0; x<width; x++) 
        {
            for(int y=0; y<height; y++) 
            {
                if(!coorindates(x, y).visited) 
                {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() 
    {
        StringBuilder builder = new StringBuilder();
        // Append each character onto the builder, with new lines for each row.
        for(Node[] row: maze) 
        {
            for(Node cell: row) 
            {
                builder.append(cell.color);
            }
            builder.append("\n");
        }
        return builder.toString();
    }


}
