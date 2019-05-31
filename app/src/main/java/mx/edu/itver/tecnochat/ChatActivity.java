package mx.edu.itver.tecnochat;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ChatActivity extends AppCompatActivity {

    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private ListView chat;
    private HiloMensajes tareaCliente;

    final Context context = this;

    SimpleDateFormat formatterMDY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();

        String direccionIP = intent.getStringExtra(MainActivity.EXTRA_DIP);
        String usuario = intent.getStringExtra(MainActivity.EXTRA_USR);

        formatterMDY = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        listItems = new ArrayList<String>();

        chat = findViewById(R.id.chat);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listItems);


        chat.setAdapter(adapter);

        String response = "";

        // Estas dos lineas se tienen que colocar para evitar un error de aviso de seguridad
        // Esta aplicacion se tiene que convertir a una implementacion con AsyncTask
        // Se deja así para que coincida con la implementación en la practica con Java Estandar
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tareaCliente = new HiloMensajes(this, direccionIP, 3333, usuario);

        tareaCliente.start();

        findViewById(R.id.btnEnviar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edtMensaje = findViewById(R.id.edtMensaje);
                String mensaje = edtMensaje.getText().toString();

                addRespuesta(mensaje);

                edtMensaje.onEditorAction(EditorInfo.IME_ACTION_DONE);

                tareaCliente.enviar(mensaje);
            }
        });

    }

    void addRespuesta(String respuesta) {
        String fechaHora = formatterMDY.format(Calendar.getInstance().getTime());
        listItems.add(fechaHora + " < " + respuesta);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        tareaCliente.salir = true;

    }

    public class HiloMensajes extends Thread {

        Socket clientSocket;
        PrintWriter out;
        BufferedReader in;
        String buffer;

        String direccionIP = "";
        int puerto = 0;
        String usuario = "";

        ChatActivity parent = null;

        boolean salir = false;

        HiloMensajes(ChatActivity _parent, String _direccionIP, int _puerto, String _usuario) {
            parent = _parent;
            direccionIP = _direccionIP;
            puerto = _puerto;
            usuario = _usuario;
        }

        public void run() {
            String fechaHora;


            try {
                clientSocket = new Socket(direccionIP, puerto);

                out = new PrintWriter(clientSocket.getOutputStream(), true);

                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                buffer = in.readLine(); // Leer bienvenida

                parent.addRespuesta(buffer);

                out.println(usuario);

                while (!this.salir) {
                    buffer = in.readLine();

                    if (buffer != null) {
                        parent.addRespuesta(buffer);
                    }
                }
            } catch (IOException ioe) {

                Log.v("IOExcepcion:", ioe.getMessage());

            } catch (Exception e) {

                Log.v("Excepcion!:", e.getMessage());
            }
        }

        void enviar(String mensaje) {
            out.println(mensaje);
        }

        void cerrar() {
            try {
                clientSocket.close();
                in.close();
                out.close();
                this.salir = true;
            } catch (IOException ex) {
                Log.v("error al cerrar", ex.getMessage());
            }
        }


    }
}

/*


 */