import java.awt.Color;
import java.awt.Graphics;

public class Black extends TicTacToe{
    
    Black(int pos){
        super(pos, 0);
    }
    
    public void draw(Graphics g) { 
        g.setColor(Color.BLACK);     
        g.fillOval(this.getX(),
               this.getY(), 
               50, 
               50);  
    
  }
}