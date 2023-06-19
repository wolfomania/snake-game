package logic;

public class Snake {

    private Square square;

    private Snake previous;

    private Snake next;

    public Snake(int xBegin, int yBegin) {
        this.square = new Square(xBegin, yBegin);
        this.next = null;
        this.previous = null;
    }

    public Snake(int xBegin, int yBegin, Snake previous, Snake next) {
        this.square = new Square(xBegin, yBegin);
        this.previous = previous;
        this.next = next;
    }

    public void setPrevious(Snake next) {
        this.previous = next;
    }


    public void setNext(Snake next) {
        this.next = next;
    }

    public Snake getNext() {
        return next;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Square getSquare() {
        return square;
    }

    public Snake getPrevious() {
        return previous;
    }

}
