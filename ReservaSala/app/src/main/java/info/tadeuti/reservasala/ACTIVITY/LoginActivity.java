package info.tadeuti.reservasala.ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import info.tadeuti.reservasala.DAO.ConfiguracaoFireBase;
import info.tadeuti.reservasala.Entidades.Usuario;
import info.tadeuti.reservasala.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editSenha;
    private TextView tvAbreCadastro;
    private Button btnLogar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(info.tadeuti.reservasala.R.layout.activity_login);

        editEmail = (EditText) findViewById(info.tadeuti.reservasala.R.id.editEmail);
        editSenha = (EditText) findViewById(info.tadeuti.reservasala.R.id.editSenha);
        tvAbreCadastro = (TextView) findViewById(R.id.tvAbreCadastro);
        btnLogar = (Button) findViewById(info.tadeuti.reservasala.R.id.btnLogar);


        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editEmail.getText().toString().isEmpty() && !editSenha.getText().toString().isEmpty()) {
                    usuario = new Usuario();
                    usuario.setEmail(editEmail.getText().toString());
                    usuario.setSenha(editSenha.getText().toString());
                    validarLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastroUsuario();
            }
        });

    }

    private void validarLogin(){
        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario ou senha invalida!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaPrincipal(){
        Intent itentAbrirTelaPrincipal = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(itentAbrirTelaPrincipal);
    }

    public void abrirCadastroUsuario(){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }
}
