package client;

import com.google.gson.Gson;
import model.Action;
import model.Request;
import model.Response;
import model.Result;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.UUID;

public class ClientThread implements Runnable{

    private static final int PORT = 9999;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private Gson gson;

    public ClientThread() throws IOException {
        socket = new Socket("localhost", PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        gson = new Gson();
    }

    public void run() {
        Request request = new Request();
        UUID id = UUID.randomUUID();
        Random rand = new Random();

        System.out.println("Igrac " + id.toString() + " pokusava da pristupi igri.");

        request.setId(id);
        request.setAction(Action.REQUEST_CHAIR);
        sendRequest(request);

        Response response = receiveResponse();

        if(response.getResult() == Result.SUCCESS) {
            System.out.println("Igrac " + id.toString() + " je uspeo da se prikljuci igri.");
        } else {
            System.out.println("Igrac " + id.toString() + " nije uspeo da se prikljuci igri.");
        }
        request.setAction(Action.REQUEST_IGRA);
///        request.setId(id);
        sendRequest(request);

        response = receiveResponse();

        if(response.getResult() == Result.IZVUCI){
            /*
            0 - kraca; 1 - duza
            */
            int stapic = rand.nextInt(2);
            System.out.println("Igrac " + id + " je izvukao stapic");
            request.setAction(Action.IZVLACIM);
            request.setIzvucena(stapic);
            sendRequest(request);
        }else if(response.getResult() == Result.POGADJAJ){
            /*
            0 - kraca; 1 - duza
             */
            int guess = rand.nextInt(2);
            System.out.println("Igrac " + id + " je zakljucao svoju opkladu");
            request.setAction(Action.SALJEM_GUESS);
            request.setGuessed(guess);
            sendRequest(request);
        }else if(response.getResult() == Result.UNAUTHORIZED){
            System.out.println("Igrac sa id-em " + id + " je pokusao da igra ali nema mesto za stolom ");
            request.setAction(Action.NEBITNO);
            sendRequest(request);
        }


    }

    public void sendRequest(Request request) {
        out.println(gson.toJson(request));
    }

    public Response receiveResponse() {
        try {
            return gson.fromJson(in.readLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
