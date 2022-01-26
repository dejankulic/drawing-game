package server;

import com.google.gson.Gson;
import model.*;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    private Gson gson;
    private Table table;

    public ServerThread(Socket socket, Table table) {
        this.socket = socket;
        this.table = table;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gson = new Gson();
    }

    public void run() {

        try {
            Request request = receiveRequest();

            Player player = new Player(request.getId());

            Response response = new Response();
            response.setResult(Result.FAILURE);

            if(request.getAction() == Action.REQUEST_CHAIR) {
                if(table.giveSeat(player)){
                    response.setResult(Result.SUCCESS);
                }
                sendResponse(response);
            }
            request = receiveRequest();
            if(request.getAction() == Action.REQUEST_IGRA){
                if(table.izvlacenje().equals(request.getId())){
                    response.setResult(Result.IZVUCI);
                }else if(table.pogadjaju(request.getId())){
                  //  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    response.setResult(Result.POGADJAJ);
                }else{
                    response.setResult(Result.UNAUTHORIZED);
                  //  System.out.println("asdasdasdasdasdasd");
                }
                sendResponse(response);
           // System.out.print("asdasdasdasdasdasd3423234234234234234234");
            }
            request = receiveRequest();
            if(request.getAction() == Action.IZVLACIM){
                table.setBrojKojiSePogadja(request.getIzvucena());
            }
            if(request.getAction() == Action.SALJEM_GUESS){
                int opklada = request.getGuessed();
                if(opklada == table.getBrojKojiSePogadja()){
                    player.setPoints(player.getPoints()+1);
                    System.out.println("Igrac sa id-em: " + request.getId() + " je pogodio broj" +
                            " i trenutno ima poena: " + player.getPoints());
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(request.getAction() != Action.NEBITNO)
                table.metoda();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Request receiveRequest() throws IOException {
        return gson.fromJson(in.readLine(), Request.class);
    }

    private void sendResponse(Response response) {
        out.println(gson.toJson(response));
    }
}
