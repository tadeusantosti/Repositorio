package br.com.caelum.leilao.interfaces;

import br.com.caelum.leilao.dominio.Pagamento;

public interface RepositorioDePagamentos {
      void salva(Pagamento pagamento);

    public void salvar(br.com.caelum.leilao.dominio.Pagamento capture);
}
