/*
 * CSCI 446 A.I.
 * Completed by: Robert Tiller, Kyle Ungersma, Jason Armstrong, Beau Anderson
 */


public class Advanced extends FreeFlowCSP
{
    
    public Advanced(Maze maze) 
    {
        super(maze);
    }

    
    @Override
    public Node pickNode() 
    {
        
        int constraintByLevel = Integer.MAX_VALUE;
        Node cellConstraint = null;
        for(int i=0; i<maze.width; i++) 
        {
            for(int j=0; j<maze.height; j++) 
            {
                Node cell = maze.coordinates(i, j);
                if(!cell.visited) 
                {
                    cell.updateDomain(domain);
                }
            }
        }

        for(int i=0; i<maze.width; i++) 
        {
            for(int j=0; j<maze.height; j++) 
            {
                Node cell = maze.coordinates(i, j);
                if(cell.visited) 
                {
                    continue;
                }

                int level = cell.domain.size();
                if(level < constraintByLevel) 
                {
                    cellConstraint = cell;
                    constraintByLevel = level;
                }
            }
        }
        return cellConstraint;
    }
    
    @Override
    public Maze solveMaze() 
    {
        if(maze.completed()) 
        {
            return maze;
        }

        Node temp = pickNode();

        for(char value: temp.domain) 
        {
            temp.setColor(value);
            if(mazeConstraints()) 
            {
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

    
}
