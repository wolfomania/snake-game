package logic;

import events.*;
import graphics.MyGraphic;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Random;

public class Board extends AbstractTableModel implements DirectionChangeListener, DifficultySetterListener {

    private final int[][] gameBoard;

    private ScoreChangeListener scoreChangeListener;

    private EndGameListener endGameListener;

    private final Snake head;

    private Snake tail;

    private boolean firstMove;
    private int score;

    private volatile int direction;

    private int speed;

    private final Thread game;

    public Board() {
        speed = 50;
        firstMove = true;
        score = 1;
        direction = -5;
        gameBoard = new int[16][25];
        head = new Snake(8, 12);
        tail = new Snake(8,12);
        tail.setPrevious(head);
        head.setNext(tail);
        gameBoard[8][12] = 1;
        addApple();
        game = new Thread(new Game());
    }

    @Override
    public int getRowCount() {
        return gameBoard.length;
    }

    @Override
    public int getColumnCount() {
        return gameBoard[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return gameBoard[rowIndex][columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return JPanel.class;
    }

    @Override
    public void changeDirection(DirectionEvent evt) {
        if (firstMove) {
            game.start();
            firstMove = false;
        }
        if(Math.abs(direction - evt.getDirection()) != 2) {
            direction = evt.getDirection();
//            System.out.println("Direction changed to:" + direction);
        }
    }

    private void moveIn(int direction) throws RuntimeException{
        Square temp = head.getSquare();
        switch (direction) {
            case 0:
                temp.moveTo(temp.getX() + 1, temp.getY());
                break;
            case 1:
                temp.moveTo(temp.getX(), temp.getY() - 1);
                break;
            case 2:
                temp.moveTo(temp.getX() - 1, temp.getY());
                break;
            case 3:
                temp.moveTo(temp.getX(), temp.getY() + 1);
                break;
            default:
                break;
        }

        if(gameBoard[temp.getX()][temp.getY()] == 3) {
            Snake add = new Snake(tail.getSquare().getX(), tail.getSquare().getY(), tail.getPrevious(), tail);
            tail.setPrevious(add);
            score++;
            addApple();
            fireScoreChangeListener(score);
        }

        if(gameBoard[temp.getX()][temp.getY()] == 2) {
            throw new RuntimeException();
        }

        gameBoard[temp.getX()][temp.getY()] = 1;

        if(score == 1) {
            gameBoard[tail.getSquare().getX()][tail.getSquare().getY()] = 0;
            fireTableCellUpdated(tail.getSquare().getX(), tail.getSquare().getY());
            tail.getSquare().moveTo(head.getSquare().getX(), head.getSquare().getY());
        }
    }

    private void tailMoveTo() {

        Snake tempSnake = new Snake(head.getSquare().getX(), head.getSquare().getY(), head, head.getNext());
        head.getNext().setPrevious(tempSnake);
        head.setNext(tempSnake);

        gameBoard[tail.getSquare().getX()][tail.getSquare().getY()] = 0;

        moveIn(direction);

        gameBoard[tempSnake.getSquare().getX()][tempSnake.getSquare().getY()] = 2;

        tail = tail.getPrevious();

    }

    @Override
    public void setDifficulty(DifficultyEvent evt) {
        switch (evt.getDifficulty()){
            case 0:
                speed = 175;
                break;
            case 1:
                speed = 125;
                break;
            case 2:
                speed = 75;
                break;
            case 3:
                speed = 50;
                break;
            default:
                break;
        }
    }

    private class Game implements Runnable {

        public Game() {
            super();
        }

        @Override
        public void run() {

            int tempDirection = direction;
            try{

            while (direction > -1 && direction < 4) {

                if (Math.abs(tempDirection - direction) == 2)
                    direction = tempDirection;

                Square temp = new Square(tail.getSquare().getX(), tail.getSquare().getY());

                if(score == 1)
                    moveIn(direction);
                else tailMoveTo();
                updateCells(temp);

                tempDirection = direction;
                holdThread();
            }
            } catch (Exception e){
                fireGameOverEvent();
            }

        }

    }

    private void holdThread(){
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Random r = new Random();

    private void updateCells(Square last) {

        fireTableCellUpdated(head.getSquare().getX(), head.getSquare().getY());
        fireTableCellUpdated(head.getNext().getSquare().getX(), head.getNext().getSquare().getY());
        fireTableCellUpdated(last.getX(), last.getY());
    }
    private void addApple() {
        int randomPosition = (r.nextInt(((25 * 16) - score + 1)));
        int x = 0;
        int y = 0;
        for(; x < gameBoard.length && randomPosition > 0; x++) {
            for(y = 0; y < gameBoard[x].length && randomPosition > 0; y++){
                if(gameBoard[x][y] == 0)
                    randomPosition--;
            }
        }
        if(x == gameBoard.length)
            x--;
        if(y == gameBoard[x].length)
            y--;
        if(gameBoard[x][y] == 0) {
            gameBoard[x][y] = 3;
            fireTableCellUpdated(x, y);
        }
        else addApple();
    }

    public void init(MyGraphic myGraphic) {
        this.scoreChangeListener = myGraphic;
        this.endGameListener = myGraphic;
    }

    private void fireScoreChangeListener(int score){
        scoreChangeListener.changeScore(new ScoreEvent(this, score));
    }

    private void fireGameOverEvent(){
        endGameListener.endGame();
    }
}
