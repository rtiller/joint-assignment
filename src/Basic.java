/*
 * CSCI 446 A.I.
 * Completed by: Robert Tiller, Kyle Ungersma, Jason Armstrong, Beau Anderson
 */


public class Basic extends FreeFlowCSP{
    
    
    public Basic(Maze maze) {
        super(maze);
    }
    
    @Override
     public Node pickNode() 
    {
        for(int i=0; i<maze.width; i++) 
        {
            for(int j=0; j<maze.height; j++) 
            {
                Node cell = maze.coordinates(i, j);
                if(!cell.visited)
                {
                return cell;
                }
            }
        }
        return null;
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
     
    
}
