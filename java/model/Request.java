package model;

import java.util.UUID;

public class Request {

    private UUID id;
    private Action action;
    private int izvucena;
    private int guessed;

    public Request() {

    }

    public int getIzvucena() {
        return izvucena;
    }

    public void setIzvucena(int izvucena) {
        this.izvucena = izvucena;
    }

    public int getGuessed() {
        return guessed;
    }

    public void setGuessed(int guessed) {
        this.guessed = guessed;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
