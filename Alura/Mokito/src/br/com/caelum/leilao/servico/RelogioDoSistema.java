package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.interfaces.Relogio;
import java.util.Calendar;

public class RelogioDoSistema implements Relogio {

    public Calendar hoje() {
        return Calendar.getInstance();
    }
}
