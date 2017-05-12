import java.util.ArrayList;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TicTacToe extends JPanel implements ActionListener{
    int[][] board = new int[3][];
    ArrayList<int[][]> prevGames;
    int[][] oGoodMoves = new int[3][];
    int[][] xGoodMoves = new int[3][];
    int O = 0, X = 0, draws = 0, turn = 1, nextMoveR = 0, nextMoveC = 0, movesMade, status = 0, gameNum = 0, tim = 500;
    String game;
    PrintWriter saveFile;
    
    TicTacToe(){

        try(PrintWriter saveFile = new PrintWriter("saveFile.txt")){
            saveFile.println( "TicTacToe by: Michael McCann" );
        }catch(IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
        for (int i = 0; i < 3; i++) {
             board[i] = new int[3];
             oGoodMoves[i] = new int[3];
             xGoodMoves[i] = new int[3];
        }
        prevGames = new ArrayList<int[][]>();
        Timer timer = new Timer(tim, this); 
	    timer.start();
        this.game = ("Total moves made this game: " + movesMade 
                           + "\n   \"O\" wins: " + O
                           + "\n   \"X\" wins: " + X
                           + "\n    draws: " + draws
                           + this);
	}
    
    TicTacToe(int tim){
        this.tim = tim;
        try(PrintWriter saveFile = new PrintWriter("saveFile.txt")){
            saveFile.println( "TicTacToe by: Michael McCann" );
        }catch(IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
        for (int i = 0; i < 3; i++) {
             board[i] = new int[3];
             oGoodMoves[i] = new int[3];
             xGoodMoves[i] = new int[3];
        }
        prevGames = new ArrayList<int[][]>();
        Timer timer = new Timer(tim, this); 
	    timer.start();
        this.game = ("Total moves made this game: " + movesMade 
                           + "\n   \"O\" wins: " + O
                           + "\n   \"X\" wins: " + X
                           + "\n    draws: " + draws
                           + this);
	}
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawString(g, game, 15, 15);
    }
    public void drawString(Graphics g, String text, int x, int y){
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
    
    
    public String toString(){
        String print = "\n -------------- \n";
        for (int i = 0; i < 3; i++) {
             for (int j = 0; j < 3; j++) {
                 print = print + " | " + this.returnXO(board[i][j]);
             }
            print = print + "\n -------------- \n";
         }
        return print;
    }
    
    private String returnXO(int player){
        String p;
        switch (player){
            case 1: p = "X";
                    break;
            case 2: p = "O";
                    break;
            default: p = "  ";
                    break;
        }
        return p;
    }
    
    //used to simulate some random games for a basis or probabilities
    //also used to make first 2 moves to keep from getting in a loop quickly.
    private void firstGames(){
        for(int i = 0; i < 4; i++){
            Random rand = new Random();
            int r = rand.nextInt(3);
            int c = rand.nextInt(3);
            if(board[r][c] == 0){
                board[r][c] = turn;
                movesMade += 1;
                if(turn == 1){
                    turn = 2;
                }else{ 
                    turn = 1;}
                return;
            }
        }
        for(int i = 0; i < 3; i ++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == 0){
                    board[i][j] = turn;
                    movesMade += 1;
                    if(turn == 1){
                        turn = 2;
                    }else{ 
                        turn = 1;}
                    return;
                }
            }
        }
    }
    
    private void makeMove(){
        if(movesMade < 2){
            firstGames();
            return;
        }
        //Check for winner
        if(winner()){
            movesMade = 0;
            if(turn == 1){
                status = 2;
                gameNum += 1;
            }else{
                status = 1;
                gameNum += 1;
            }
            return;
        }else if(movesMade == 9){
            status = -1;
            gameNum += 1;
            return;
        }else{
            if(checkPosWin()){
                board[nextMoveR][nextMoveC] = turn;
                movesMade += 1;
                if(turn == 1){ turn = 2; }else{ turn = 1; }
                return;
            }
            //first Two games simulated random other than if possible win
            if(gameNum < 5){
                firstGames();
                return;
            }else{
                int max = 0;
                int r = 0;
                int c = 0;
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        if(board[i][j] == 0){
                            if(turn == 1){
                                if(oGoodMoves[i][j] > max){
                                    max = oGoodMoves[i][j];
                                    r = i;
                                    c = j;
                                }
                            }else{
                                if(xGoodMoves[i][j] > max){
                                    max = xGoodMoves[i][j];
                                    r = i;
                                    c = j;
                                }
                            }
                        }
                    }
                }
                if(max > 0){
                    board[r][c] = turn;
                    if(turn == 1){
                        turn = 2;
                    }else{
                        turn = 1;
                    }
                    movesMade += 1;
                    return;
                }else{
                    for(int i = 0; i < 3; i++){
                        for(int j = 0; j < 3; j++){
                            if(board[i][j] == 0){
                                board[i][j] = turn;
                                if(turn == 1){
                                    turn = 2;
                                }else{
                                    turn = 1;
                                }
                                movesMade += 1;
                                return;
                            }
                        }
                    }
                }
            }
        }   
    }
    
    public boolean winner(){
        for (int i = 0; i < 3; i++){
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && !(board[i][2] == 0)){
                return true;
            }else if (board[0][i] == board[1][i] && board[2][i] == board[0][i] && !(board[0][i] == 0)){
                return true;
            }
        }
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && !(board[0][0] == 0)){
            return true;
        }else if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && !(board[0][2] == 0)){
            return true;
        }
        return false;
    }
    
    private boolean checkPosWin(){ 
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == 0){
                    board[i][j] = turn;
                    if (winner()){
                        nextMoveR = i;
                        nextMoveC = j;
                        board[i][j] = 0;
                        return true;
                    }else{
                        board[i][j] = 0;
                    }
                }
            }
        }
        int oppMove;
        if (turn == 1){
            oppMove = 2;
        }else{
            oppMove = 1;
        }
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == 0){
                    board[i][j] = oppMove;
                    if (winner()){
                        nextMoveR = i;
                        nextMoveC = j;
                        board[i][j] = 0;
                        return true;
                    }else{
                        board[i][j] = 0;
                    }
                }
            }
        }
        return false;
    }
    
    public void actionPerformed(ActionEvent e) {
        makeMove();
        if (status == 1){
            X += 1;
            this.game = ("Total moves made this game: " + movesMade 
                           + "\n   \"O\" wins: " + O
                           + "\n   \"X\" wins: " + X
                           + "\n    draws: " + draws
                           + this 
                           + "\n\"X\" Wins");
            prevGames.add(board);
            System.out.println(this);
            saveToFile();
            clear();
        }else if (status == 2){
            O += 1;
            this.game = ("Total moves made this game: " + movesMade 
                           + "\n   \"O\" wins: " + O
                           + "\n   \"X\" wins: " + X
                           + "\n    draws: " + draws
                           + this 
                           + "\n\"O\" Wins");
            System.out.println(this);
            saveToFile();
            prevGames.add(board);
            clear();
        }else if (status == -1){
            draws += 1;
            this.game = ("Total moves made this game: " + movesMade 
                           + "\n   \"O\" wins: " + O
                           + "\n   \"X\" wins: " + X
                           + "\n    draws: " + draws
                           + this 
                           + "\nIt wa a draw");
            System.out.println(this);
            saveToFile();
            prevGames.add(board);
            clear();
        }else {
            this.game = ("Total moves made this game: " + movesMade 
                           + "\n   \"O\" wins: " + O
                           + "\n   \"X\" wins: " + X
                           + "\n    draws: " + draws
                           + this);
        }
        repaint();        
    }
    
    private void saveToFile(){
        try(PrintWriter saveFile = new PrintWriter("saveFile.txt")){
            saveFile.println( prevGamesToString() );
        }catch(IOException ee){
            System.err.println("Caught IOException: " + ee.getMessage());
        }
    }
    
    public String prevGamesToString(){
        String result = "Michael McCann\nTicTacToe\n\n";
        for(int k = 0; k < prevGames.size(); k++){
            result += "Game: " + k;
            String print = "\n -------------- \n";
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    print = print + " | " + this.returnXO((prevGames.get(k))[i][j]);
                }
                print = print + "\n -------------- \n";
            }
            result += print ;
        }
        result += "\n\n\"O\" wins: " + O
                    + "\n\"X\" wins: " + X
                    + "\nDraws: " + draws
                    + "\n\n\n";
        return result;
    }
    
    private void clear(){
        for (int i = 0; i < 3; i++) {
             for (int j= 0; j < 3; j++){
                 board[i][j] = 0;
             }
         }
        status = 0;
        movesMade = 0;
    }
    
    public static void main(String[] args){
        if(args.length > 0){
            TicTacToe a = new TicTacToe(Integer.parseInt(args[0]));
            JFrame frame = new JFrame("Automated Learning TicTacToe by: Michael McCann");
            frame.setSize(300,300);
            frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
            frame.add(a);
            frame.setVisible(true);
        }else{
            TicTacToe a = new TicTacToe();
            JFrame frame = new JFrame("Automated Learning TicTacToe by: Michael McCann");
            frame.setSize(300,300);
            frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
            frame.add(a);
            frame.setVisible(true);
        }
    }

}
