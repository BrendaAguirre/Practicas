package mx.edu.itver.tecnochat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_DIP = "mx.edu.itver.ChatTecno.DIP";
    public final static String EXTRA_USR = "mx.edu.itver.ChatTecno.USR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnConectar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChatActivity.class);

                EditText edtDireccionIP = findViewById(R.id.edtDireccionIP);
                String direccionIP = edtDireccionIP.getText().toString();

                EditText edtUsuario = findViewById(R.id.edtUsuario);
                String usuario = edtUsuario.getText().toString();


                intent.putExtra(EXTRA_DIP,direccionIP);
                intent.putExtra(EXTRA_USR,usuario);

                startActivity(intent);
            }
        });

    }
}
