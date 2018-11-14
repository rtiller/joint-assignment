
import java.io.File;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rtill
 */
public class Advanced extends FreeFlowCSP{
    
    public Advanced(Maze maze) {

        super(maze);

    }



    @Override

    public Maze solveMaze() {

        if(maze.completed()) return maze;

        Node var = getNode();



        for(char value: var.domain) {

            var.setColor(value);

            if(mazeConstraints()) {



                Maze result = solveMaze();

                if(result != null) return result;

                var.resetColor();

            } else {

                var.resetColor();

            }

        }

        return null;

    }



    @Override

    public Node getNode() {

        Node mostConstrainedCell = null;

        int mostConstrainedLevel = Integer.MAX_VALUE;





        for(int x=0; x<maze.width; x++) {

            for(int y=0; y<maze.height; y++) {

                Node cell = maze.coordinates(x, y);

                if(!cell.visited) cell.updateDomain(domain);

            }

        }



        for(int x=0; x<maze.width; x++) {

            for(int y=0; y<maze.height; y++) {

                Node cell = maze.coordinates(x, y);

                if(cell.visited) continue;



                int level = cell.domain.size();



                if(level < mostConstrainedLevel) {

                    mostConstrainedCell = cell;

                    mostConstrainedLevel = level;

                }

            }

        }

        return mostConstrainedCell;

    }

    
 
    
}
