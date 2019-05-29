package mx.edu.itver.tecnochat;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {

    private ArrayList<String> listItems=new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private ListView chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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

