import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Test {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("145.24.222.190", 665);
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            DataInputStream dIn = new DataInputStream(socket.getInputStream());

            JSONObject jason = new JSONObject();

            JSONObject header = new JSONObject();

            JSONObject body = new JSONObject();

            header.put("receiveCountry", "US");
            header.put("receiveBank", "TEST");
            header.put("originBank", "EVIL");
            header.put("originCountry", "US");

            body.put("pin", "0420");
            body.put("account", "00000001");
            //body.put("amount", 2000);

            jason.put("header", header);
            jason.put("body", body);

            dOut.writeUTF("balance");
            dOut.writeUTF(jason.toString());

            System.out.println(dIn.readUTF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
