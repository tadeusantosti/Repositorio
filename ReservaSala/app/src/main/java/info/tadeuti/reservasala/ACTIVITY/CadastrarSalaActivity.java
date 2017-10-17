package info.tadeuti.reservasala.ACTIVITY;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import info.tadeuti.reservasala.Entidades.SalaReuniao;
import info.tadeuti.reservasala.R;


public class CadastrarSalaActivity extends AppCompatActivity {

    private SalaReuniao sala;
    private EditText descricao;
    private Button btnSalvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(info.tadeuti.reservasala.R.layout.activity_cadastrar_sala);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        descricao = (EditText) findViewById(info.tadeuti.reservasala.R.id.editCadDescricao);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!descricao.getText().toString().isEmpty()) {
                    sala = new SalaReuniao();
                    sala.setId(UUID.randomUUID().toString());
                    sala.setDescricao(descricao.getText().toString());
                    sala.salvarSalaReuniao();

                } else {
                    Toast.makeText(CadastrarSalaActivity.this, "É necessário informar uma descricao (Ex: Sala1)", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
