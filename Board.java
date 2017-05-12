import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.awt.*;
import java.util.*; 


public class Board 
  extends JComponent 
  implements MouseListener{
  
  //Used to add button
  //JPanel panel;
  //Window where game will be
  JFrame gameWindow;
  //Pieces that are placed
  ArrayList<TicTacToe> pieces = new ArrayList<TicTacToe>();
  //Used to keep track of who goes next
  int nextMove = 0;
  int moveNum = 0;
  //Used to show number of wins
  int numBlack = 0;
  int numRed = 0;
  int numDraw = 0;
  //Status used to say if ended
  int status = 0;
  //Used to store the moves each pieces make.
  ArrayList<Integer> redMoves = new ArrayList<Integer>();
  ArrayList<Integer> blackMoves = new ArrayList<Integer>();
  //Used to tally number of wins with posistions
  ArrayList<Integer> winWithMove = new ArrayList<Integer>();
  
  //Possible wins.
  ArrayList<Integer> winOne = new ArrayList<Integer>()
  {{add(0);add(1);add(2);}};
  ArrayList<Integer> winTwo = new ArrayList<Integer>()
  {{add(3);add(4);add(5);}};
  ArrayList<Integer> winThree = new ArrayList<Integer>()
  {{add(6);add(7);add(8);}};
  ArrayList<Integer> winFour = new ArrayList<Integer>()
  {{add(2);add(4);add(6);}};
  ArrayList<Integer> winFive = new ArrayList<Integer>()
  {{add(0);add(4);add(8);}};
  ArrayList<Integer> winSix = new ArrayList<Integer>()
  {{add(0);add(3);add(6);}};
  ArrayList<Integer> winSeven = new ArrayList<Integer>()
  {{add(1);add(4);add(7);}};
  ArrayList<Integer> winEight = new ArrayList<Integer>()
  {{add(2);add(5);add(8);}};
  
  
  //   Imported from MouseListener
  public void mouseClicked(MouseEvent e){  
    if (status != -1){
      int move = this.findMove();
      if (this.nextMove == 0){
        this.pieces.add(new Red(move));
        this.nextMove = 1;
        redMoves.add(move);
      }else {
        this.pieces.add(new Black(move));
        this.nextMove = 0;
        blackMoves.add(move);
      }
    }else {
      pieces.removeAll(pieces);
      redMoves.removeAll(redMoves);
      blackMoves.removeAll(blackMoves);
      status = 0;
    }
    moveNum += 1;
    if (checkWin()){
      status = -1;
      moveNum = 0;
    }
    repaint();
  }
  
  public void mouseExited(MouseEvent e){    }
  
  public void mouseEntered(MouseEvent e){    }
  
  public void mousePressed(MouseEvent e){    }
  
  public void mouseReleased(MouseEvent e){   }
  
  
  public void paintComponent(Graphics g){
    g.setColor(Color.blue);
    g.fillRect(100,200,300,15);
    g.fillRect(100,300,300,15);
    g.fillRect(200,100,15,300);
    g.fillRect(300,100,15,300);
    for (TicTacToe t : this.pieces){
      t.draw(g);
    }
    g.setColor(Color.black);
    Graphics2D blackWins = (Graphics2D)g;
    blackWins.setRenderingHint(
                               RenderingHints.KEY_ANTIALIASING,
                               RenderingHints.VALUE_ANTIALIAS_ON);
    blackWins.drawString("Black: " + numBlack,70,20);
    
    Graphics2D redWins = (Graphics2D)g;
    redWins.setRenderingHint(
                             RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
    redWins.drawString("Red: " + numRed,70,40);
    
    Graphics2D draws = (Graphics2D)g;
    draws.setRenderingHint(
                           RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
    draws.drawString("Draws: " + numDraw,70,60);
  }
  
  Board( int zero, int one, int two, int three, int four, 
        int five, int six, int seven, int eight){
    winWithMove.add(zero);
    winWithMove.add(one);
    winWithMove.add(two);
    winWithMove.add(three);
    winWithMove.add(four);
    winWithMove.add(five);
    winWithMove.add(six);
    winWithMove.add(seven);
    winWithMove.add(eight);
    
    gameWindow = 
      new JFrame("Tic Tac Toe created by Michael McCann");
    gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
    gameWindow.setSize(500, 500);
    gameWindow.setResizable(false);
    gameWindow.add( this );
    this.addMouseListener(this);
    gameWindow.setVisible(true);
  }
  
  Board(){
    this(1,1,1,1,1,1,1,1,1);
  }
  
  private boolean checkWin(){
    int ranAdd = (int)(Math.random() * 9);
    int ranAddTwo = (int)(Math.random() * 9);
    if (redMoves.containsAll(winOne) || 
        redMoves.containsAll(winTwo) || 
        redMoves.containsAll(winThree) || 
        redMoves.containsAll(winFour) || 
        redMoves.containsAll(winFive) || 
        redMoves.containsAll(winSix) || 
        redMoves.containsAll(winSeven) || 
        redMoves.containsAll(winEight)){
      
      numRed += 1;
      for (int r : redMoves){
        winWithMove.set(r, winWithMove.get(r) + 1);
      }
      winWithMove.set(ranAdd, winWithMove.get(ranAdd) + 1);
      return true;
    }else if (blackMoves.containsAll(winOne) || 
              blackMoves.containsAll(winTwo) || 
              blackMoves.containsAll(winThree) || 
              blackMoves.containsAll(winFour) || 
              blackMoves.containsAll(winFive) || 
              blackMoves.containsAll(winSix) || 
              blackMoves.containsAll(winSeven) || 
              blackMoves.containsAll(winEight)){
      numBlack += 1;
      for (int b : blackMoves){
        winWithMove.set(b, winWithMove.get(b) + 1);
      }
      winWithMove.set(ranAdd, winWithMove.get(ranAdd) + 1);
      winWithMove.set(ranAdd, winWithMove.get(ranAddTwo) + 1);
      return true;
    }else if (this.moveNum > 9){
      numDraw += 1;
      winWithMove.set(ranAdd, winWithMove.get(ranAdd) + 1);
      winWithMove.set(ranAdd, winWithMove.get(ranAddTwo) + 1);
      return true;
    }
    return false;
  }
  
  private int findMove(){
    boolean moveNotMade = true;
    ArrayList<Integer> newWinMove = new ArrayList<Integer>(winWithMove);
    int index;
    do{ 
      int bestMove =  Collections.max(newWinMove);
      index = newWinMove.indexOf(bestMove);
      if(!(redMoves.contains(index))){
        if(!(blackMoves.contains(index))){
          return index;
        }else{ newWinMove.set(index,0);};
      }else{ newWinMove.set(index,0);};
    }while(moveNotMade);
    return index;
  }
  
  public static void main(String[] args){
    new Board();
  } 
}