package graphics;

import java.util.Objects;

public class Player implements Comparable<Player>{

    private final int score;

    private final String name;

    public Player(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(o.score, score);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        return score == other.score && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, name);
    }

    @Override
    public String toString() {
        return name + " : " + score + ", ";
    }

}
