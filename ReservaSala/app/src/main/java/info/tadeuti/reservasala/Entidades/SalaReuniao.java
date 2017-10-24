package info.tadeuti.reservasala.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import info.tadeuti.reservasala.DAO.ConfiguracaoFireBase;

public class SalaReuniao {

    private String id, descricao;

    public SalaReuniao(){

    }

    public void salvarSalaReuniao(){
        DatabaseReference referencia = ConfiguracaoFireBase.getFireBase();
        referencia.child("salasDeReuniao").child(getId()).child(getDescricao()).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapSalaReuniao = new HashMap<>();

        hashMapSalaReuniao.put("id", getId());
        hashMapSalaReuniao.put("descricao", getDescricao());

        return hashMapSalaReuniao;
    }

    public String getId() {
        return UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
