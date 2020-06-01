/*
    PROPERTY OF
           __
     ___  / /_________SOFTWARE__
    / _ \/  _/ _  / __\/ _ \/   \
    \___/\__/\___/\___/\___/_/__/
 */

import io.socket.client.IO;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class App {
    static io.socket.client.Socket sock = null;

    public static void main(String[] args) {
        ServerSocket server = null;
        DataInputStream dIn;
        try {
            sock = IO.socket("http://145.24.222.206:8085");
            server = new ServerSocket(666);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        sock.on("withdraw", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("Received withdraw packet from NOOB.");
                handlePacket("withdraw", new JSONObject(args[0]));
            }
        });

        sock.on("balance", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("Received balance packet from NOOB.");
                handlePacket("balance", new JSONObject(args[0]));
            }
        });

        sock.on("response", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("Received response packet from NOOB.");
                handlePacket("response", new JSONObject(args[0]));
            }
        });

        while (true) {
            try {
                Socket socket = server.accept();
                System.out.println("Received packet from US.");

                dIn = new DataInputStream(socket.getInputStream());

                String type = dIn.readUTF();
                JSONObject json = new JSONObject(dIn.readUTF());

                socket.close();

                handlePacket(type, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static String checkBankCode(String code) {
        File table = new File("lookuptable");
        try {
            Scanner scanner = new Scanner(table);
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data[1].equals(code)) {
                    return data[0];
                }
            }
        } catch (Exception e) {

        }
        return "-1";
    }

    static void handlePacket(String type, JSONObject json) {
        System.out.println("Attempting to send data.");
        try {
            String destination_country = json.getJSONObject("header").getString("receiveCountry");
            String destination_bank = json.getJSONObject("header").getString("receiveBank");


            System.out.println(destination_bank);
            System.out.println(destination_country);
            if (destination_country.equals("US")) {
                String ip = checkBankCode(destination_bank);
                if (ip.equals("-1")) return;

                Socket bankConnection = new Socket(ip, 665);

                DataOutputStream dOut = new DataOutputStream(bankConnection.getOutputStream());
                dOut.writeUTF(type);
                dOut.writeUTF(json.toString());

                System.out.println("Sent data through to next system.");

                bankConnection.close();
            } else {
                sock.emit(type, json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
