import java.awt.Graphics;

public abstract class TicTacToe{
    private int posistion;
    private int color;
    
    TicTacToe(int pos, int color){
        this.posistion = pos;
        this.color = color;
    }
    
    public int getX(){
        int xPosn = 1110;
        switch (posistion){
         case 0: xPosn = 125;
                break;   
         case 1: xPosn = 232;
                break; 
         case 2: xPosn = 335;
                break; 
         case 3: xPosn = 125;
                break;   
         case 4: xPosn = 232;
                break; 
         case 5: xPosn = 335;
                break; 
         case 6: xPosn = 125;
                break;   
         case 7: xPosn = 232;
                break; 
         case 8: xPosn = 335;
                break; 
        }
        return xPosn;
    }
    
    public int getY(){
        int yPosn = 1110;
        switch (posistion){
         case 0: yPosn = 125;
                break;   
         case 1: yPosn = 125;
                break; 
         case 2: yPosn = 125;
                break; 
         case 3: yPosn = 225;
                break;   
         case 4: yPosn = 225;
                break; 
         case 5: yPosn = 225;
                break; 
         case 6: yPosn = 325;
                break;   
         case 7: yPosn = 325;
                break; 
         case 8: yPosn = 325;
                break; 
        }
        return yPosn;
    }
    
    public int getColor(){
        return this.color;
    }
    
    public void draw(Graphics g) {  }
    
}