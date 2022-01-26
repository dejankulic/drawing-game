package model;

import java.util.UUID;

public class Table {

    private int brojKojiSePogadja;
    private Player[] players;
    private Object lock = 1;

    public Table() {
        this.players = new Player[6];
    }

    int flag = 0;
    public void metoda(){
        synchronized (lock) {
            if(flag == 0) {
                Player pobednik = players[0];
                for (int i = 1; i < 6; i++) {
                    if (pobednik.getPoints() < players[i].getPoints()) {
                        pobednik = players[i];
                    }
                }
                System.out.println("Pobeednik je igrac sa id-em: " + pobednik.getId() + " i ima " + pobednik.getPoints() + "" +
                        " poen");

            }
                flag = 1;
        }}

    public UUID izvlacenje(){
        if(players[0] != null)
            return players[0].getId();

        return null;
    }

    public boolean pogadjaju(UUID ajdi){
        for(int i = 1; i <6; i ++) {
            if(ajdi.equals(players[i].getId())){
              return true;
            }

        }
        return false;
    }
    public synchronized boolean giveSeat(Player player) {
        for (int i = 0; i < 6 ; i++) {
            if(players[i] == null) {
                players[i] = player;

                return true;
            }
        }

        return false;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getBrojKojiSePogadja() {
        return brojKojiSePogadja;
    }

    public void setBrojKojiSePogadja(int brojKojiSePogadja) {
        this.brojKojiSePogadja = brojKojiSePogadja;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
