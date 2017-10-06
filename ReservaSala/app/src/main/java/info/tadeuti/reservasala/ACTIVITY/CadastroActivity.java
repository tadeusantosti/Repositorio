package info.tadeuti.reservasala.ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.R;
import android.R.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import info.tadeuti.reservasala.DAO.ConfiguracaoFireBase;
import info.tadeuti.reservasala.Entidades.Usuario;
import info.tadeuti.reservasala.Helper.Base64Custom;
import info.tadeuti.reservasala.Helper.Preferencias;

public class CadastroActivity extends AppCompatActivity {

    private Usuario usuario;
    private EditText editCadNome;
    private EditText editCadCargo;
    private EditText editCadEmail;
    private EditText editCadSenha;
    private EditText editCadConfirmarSenha;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private Button btnSalvar;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editCadNome = (EditText) findViewById(R.id.editCadNome);
        editCadCargo = (EditText) findViewById(R.id.editCadCargo);
        editCadEmail = (EditText) findViewById(R.id.editCadEmail);
        editCadSenha = (EditText) findViewById(R.id.editCadSenha);
        editCadConfirmarSenha = (EditText) findViewById(R.id.editCadConfirmarSenha);
        rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        rbFeminino = (RadioButton) findViewById(R.id.rbFeminino);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editCadConfirmarSenha.getText().toString().equals(editCadSenha.getText().toString())) {
                    usuario = new Usuario();
                    usuario.setNome(editCadNome.getText().toString());
                    usuario.setEmail(editCadEmail.getText().toString());
                    usuario.setSenha(editCadSenha.getText().toString());
                    usuario.setCargo(editCadCargo.getText().toString());

                    if (rbFeminino.isChecked()){
                        usuario.setSexo("FEMININO");
                    }else{
                        usuario.setSexo("MASCULINO");
                    }

                    cadastrarUsuario();

                } else {
                    Toast.makeText(CadastroActivity.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
         usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                             Toast.makeText(CadastroActivity.this, "Usuario cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                             String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                             FirebaseUser usuarioFireBase = task.getResult().getUser();
                             usuario.setId(identificadorUsuario);
                             usuario.salvarUsuario();

                             Preferencias pref = new Preferencias(CadastroActivity.this);
                             pref.salvarUsuarioPreferencias(identificadorUsuario, usuario.getNome());

                             abrirLoginUsuario();
                         } else {
                             String erroAoSalvarUsuario = "";
                             try {
                                 throw task.getException();
                             } catch (FirebaseAuthWeakPasswordException ex){
                                 erroAoSalvarUsuario = "Digite uma senha com no minimo 8 caracteres com letras e numeros";
                             } catch (FirebaseAuthInvalidCredentialsException ex){
                                 erroAoSalvarUsuario = "O e-mail informado é invalido! Informe um e-mail valido";
                             } catch (FirebaseAuthUserCollisionException ex){
                                 erroAoSalvarUsuario = "Usuario ja cadastrado!";
                             } catch (Exception e) {
                                 erroAoSalvarUsuario = "Erro ao salvar o cadastro! Contate o suporte";
                             }
                             Toast.makeText(CadastroActivity.this, "Erro: " + erroAoSalvarUsuario, Toast.LENGTH_LONG).show();
                         }
            }
        });
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
