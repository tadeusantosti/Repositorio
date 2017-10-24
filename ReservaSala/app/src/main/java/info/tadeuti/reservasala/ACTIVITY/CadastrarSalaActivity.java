package info.tadeuti.reservasala.ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import info.tadeuti.reservasala.DAO.ConfiguracaoFireBase;
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
                    sala.setId(sala.getId());
                    sala.setDescricao(descricao.getText().toString());
                    salvarSalaReuniao();
                } else {
                    Toast.makeText(CadastrarSalaActivity.this, "É necessário informar uma descricao (Ex: Sala1)", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void salvarSalaReuniao() {
        DatabaseReference referencia = ConfiguracaoFireBase.getFireBase();
        referencia.child("salareuniao").child("descricao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (!value.equals(sala.getDescricao())) {
                    sala.salvarSalaReuniao();
                    Toast.makeText(CadastrarSalaActivity.this, "Sala " + sala.getDescricao() + " cadastrada com sucesso!", Toast.LENGTH_LONG).show();
                    retornarMenuPrincipal();
                } else {
                    Toast.makeText(CadastrarSalaActivity.this, "A Sala " + sala.getDescricao() + " ja esta cadastrada!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void retornarMenuPrincipal(){
        Intent intent = new Intent(CadastrarSalaActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }
}
