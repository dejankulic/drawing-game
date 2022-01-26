package model;

import java.util.UUID;

public class Player {

    private UUID id;
    private int points;

    ///true- izvlaci ; false - gadja
    private boolean izvlaci = false;

    public Player(UUID id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        points++;
    }

    public UUID getId() {
        return id;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isIzvlaci() {
        return izvlaci;
    }

    public void setIzvlaci(boolean izvlaci) {
        this.izvlaci = izvlaci;
    }
}
