package info.tadeuti.reservasala.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import info.tadeuti.reservasala.DAO.ConfiguracaoFireBase;

public class Usuario {

    private String id;
    private String email;
    private String nome;
    private String cargo;
    private String sexo;
    private String senha;

    public Usuario(){
    }

    public void salvarUsuario(){
        DatabaseReference referencia = ConfiguracaoFireBase.getFireBase();
        referencia.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("email", getEmail());
        hashMapUsuario.put("nome", getNome());
        hashMapUsuario.put("cargo", getCargo());
        hashMapUsuario.put("sexo", getSexo());
        hashMapUsuario.put("senha", getSenha());

        return hashMapUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
