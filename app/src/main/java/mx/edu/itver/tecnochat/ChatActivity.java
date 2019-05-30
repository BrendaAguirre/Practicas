package mx.edu.itver.tecnochat;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URISyntaxException;
import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {

    private ArrayList<String> listItems=new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private ListView chat;

    private Socket socket;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();

        String direccionIP = intent.getStringExtra(MainActivity.EXTRA_DIP);
        String usuario = intent.getStringExtra(MainActivity.EXTRA_USR);


        listItems = new ArrayList<String>();

        chat = findViewById(R.id.chat);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listItems);

        // Here, you set the data in your ListView
        chat.setAdapter(adapter);
/*
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        chat.setLayoutManager(layoutManager);
*/

        String response="";

        Cliente miCliente = new Cliente(direccionIP, 3333, response, usuario);
        miCliente.execute();

        if (response.contains("Exception")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Error en la conexion");

            alertDialogBuilder
                    .setMessage(response)
                    .setCancelable(false)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            ChatActivity.this.finish();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }

        findViewById(R.id.btnEnviar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtMensaje = findViewById(R.id.edtMensaje);
                listItems.add(edtMensaje.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

    }

}

/*


 */