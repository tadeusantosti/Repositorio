package info.tadeuti.reservasala.ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import info.tadeuti.reservasala.R;

public class PrincipalActivity extends AppCompatActivity {

    private Button abreCadastroSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(info.tadeuti.reservasala.R.layout.activity_principal);
        abreCadastroSala = (Button) findViewById(R.id.btnCadastrarSala);

        abreCadastroSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastroSalaReuniao();
            }
        });

    }

    public void abrirTelaCadastroSalaReuniao(){
        Intent intent = new Intent(PrincipalActivity.this, CadastrarSalaActivity.class);
        startActivity(intent);
        finish();
    }
}
