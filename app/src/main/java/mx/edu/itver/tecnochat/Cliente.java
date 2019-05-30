package mx.edu.itver.tecnochat;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente extends AsyncTask<String, String, String> {

    String  dstAddress;
    int     dstPort;
    String  response = "";
    String  usuario = "";

    String buffer="";

    PrintWriter out;
    BufferedReader in;


    Cliente(String addr, int port, String _response, String _usuario) {
        dstAddress = addr;
        dstPort = port;
        response = _response;
        usuario = _usuario;
    }


    protected String doInBackground(String... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);

            byte[] bbuffer = new byte[1024];
            int bytesRead;

            Log.v("usuario",usuario);

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            out = new    PrintWriter(outputStream,true);
            in = new BufferedReader(new InputStreamReader(inputStream));

            buffer = in.readLine();

            publishProgress(buffer);

            out.print(usuario);

            /*
            while(true){
                try {
                    buffer = in.readLine();

                    if (buffer!=null){

                        publishProgress(buffer);

                        if (buffer.equals("Salir")){
                            break;
                        }
                    }

                } catch (IOException ex) {

                }
            }
*/
            /*
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
                publishProgress(response);
            }
            */

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return response;
    }


    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}