/*
 * CSCI 446 A.I.
 * Completed by: Robert Tiller, Kyle Ungersma, Jason Armstrong, Beau Anderson
 */


public class Basic extends FreeFlowCSP{
    
    
    public Basic(Maze maze) {
        super(maze);
    }
    
    @Override
     public Node getNode() 
    {
        for(int x=0; x<maze.width; x++) 
        {
            for(int y=0; y<maze.height; y++) 
            {
                Node cell = maze.coorindates(x, y);
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
     
    
}
