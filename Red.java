import java.awt.Graphics;
import java.awt.Color;

public class Red extends TicTacToe{
    
    Red(int pos){
        super(pos, 1); 
    }
    
    public void draw(Graphics g) { 
        g.setColor(Color.RED);     
        g.fillOval(this.getX(),
               this.getY(), 
               50, 
               50);  
    
  } 
}