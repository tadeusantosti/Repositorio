package testesdeintegracao.testes;

import java.util.Calendar;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testesdeintegracao.dao.CriadorDeSessao;
import testesdeintegracao.dao.LeilaoDao;
import testesdeintegracao.dao.UsuarioDao;
import testesdeintegracao.dominio.Lance;
import testesdeintegracao.dominio.Leilao;
import testesdeintegracao.dominio.Usuario;

public class LeilaoDaoTests {

    private Session session;
    private LeilaoDao leilaoDao;
    private UsuarioDao usuarioDao;

    @Before
    public void antes() {
        session = new CriadorDeSessao().getSession();
        leilaoDao = new LeilaoDao(session);
        usuarioDao = new UsuarioDao(session);
        session.beginTransaction();
    }

    @After
    public void depois() {
        // faz o rollback
        session.getTransaction().rollback();
        session.close();
    }

    @Test
    public void deveContarLeiloesNaoEncerrados() {
        // criamos um usuario
        LeilaoBuilder criadorDeLeiloes = new LeilaoBuilder();

        Usuario mauricio
                = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        Leilao ativo = criadorDeLeiloes.comDono(mauricio).comNome("Geladeira").comValor(1500.0).constroi();
        Leilao encerrado = criadorDeLeiloes.comDono(mauricio).comNome("XBox").comValor(700.0).encerrado().constroi();

        // persistimos todos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(ativo);
        leilaoDao.salvar(encerrado);

        // invocamos a acao que queremos testar
        // pedimos o total para o DAO
        long total = leilaoDao.total();

        assertEquals(1L, total);
    }

    @Test
    public void deveContarLeiloesEncerrados() {
        LeilaoBuilder criadorDeLeiloes = new LeilaoBuilder();

        Usuario mauricio
                = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        Leilao encerrado1 = criadorDeLeiloes.comDono(mauricio).comNome("Geladeira").comValor(1500.0).encerrado().constroi();

        Leilao encerrado2 = criadorDeLeiloes.comDono(mauricio).comNome("XBox").comValor(700.0).encerrado().constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(encerrado1);
        leilaoDao.salvar(encerrado2);

        // invocamos a acao que queremos testar
        // pedimos o total para o DAO
        long total = leilaoDao.total();

        assertEquals(0, total);
    }

    @Test
    public void deveContarLeiloesNaoUsados() {
        LeilaoBuilder criarLeilaoUsado = new LeilaoBuilder();
        LeilaoBuilder criarLeilaoNaoUtilizado = new LeilaoBuilder();

        Usuario mauricio
                = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        Leilao usado = criarLeilaoUsado.comDono(mauricio).comNome("Geladeira").comValor(1500.0).usado().constroi();

        Leilao naoUtilizado = criarLeilaoNaoUtilizado.comDono(mauricio).comNome("Xbox").comValor(700.0).constroi();

        // persistimos todos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(usado);
        leilaoDao.salvar(naoUtilizado);

        List<Leilao> leiloesNaoUtilizados = leilaoDao.novos();

        assertEquals(1, leiloesNaoUtilizados.size());
    }

    @Test
    public void deveContarLeiloesCriadosHaMaisDeUmaSemana() {

        LeilaoBuilder criarLeilaoRecente = new LeilaoBuilder();
        LeilaoBuilder criarLeilaoAntigo = new LeilaoBuilder();
        LeilaoBuilder criarLeilaoAntigo2 = new LeilaoBuilder();

        Usuario mauricio
                = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        Leilao recente = criarLeilaoRecente.comDono(mauricio).comNome("Geladeira").comValor(1500.0).constroi();
        Leilao antigo = criarLeilaoAntigo.comDono(mauricio).comNome("Xbox").comValor(700.0).diasAtras(7).constroi();
        Leilao antigoDois = criarLeilaoAntigo2.comDono(mauricio).comNome("SuperNintendo").comValor(200.0).diasAtras(7).constroi();

        // persistimos todos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(recente);
        leilaoDao.salvar(antigo);
        leilaoDao.salvar(antigoDois);

        List<Leilao> leiloesAntigos = leilaoDao.antigos();

        assertEquals(2, leiloesAntigos.size());
    }

    @Test
    public void leiloesNaoEncerradosComDataDentroDoIntervaloDevemAparecer() {

        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();

        LeilaoBuilder criarLeilao1 = new LeilaoBuilder();
        LeilaoBuilder criarLeilao2 = new LeilaoBuilder();
        LeilaoBuilder criarLeilao3 = new LeilaoBuilder();

        Usuario mauricio
                = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        Leilao leilao1 = criarLeilao1.comDono(mauricio).comNome("Geladeira").comValor(1500.0).diasAtras(2).constroi();

        Leilao leilao2 = criarLeilao2.comDono(mauricio).comNome("Xbox").comValor(700.0).diasAtras(5).constroi();

        Leilao leilao3 = criarLeilao3.comDono(mauricio).comNome("SuperNintendo").comValor(200.0).diasAtras(10).constroi();

        // persistimos todos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);
        leilaoDao.salvar(leilao3);

        List<Leilao> leiloesAntigos = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        assertEquals(3, leiloesAntigos.size());
    }

    @Test
    public void leiloesNaoEncerradosComDataDentroDoIntervaloDevemAparecer_Parte2() {
        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();

        LeilaoBuilder criarLeilao1 = new LeilaoBuilder();
        LeilaoBuilder criarLeilao2 = new LeilaoBuilder();
        LeilaoBuilder criarLeilao3 = new LeilaoBuilder();

        Usuario mauricio
                = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        Leilao leilao1 = criarLeilao1.comDono(mauricio).comNome("Geladeira").comValor(1500.0).diasAtras(2).encerrado().constroi();

        Leilao leilao2 = criarLeilao2.comDono(mauricio).comNome("Xbox").comValor(700.0).diasAtras(5).encerrado().constroi();

        Leilao leilao3 = criarLeilao3.comDono(mauricio).comNome("SuperNintendo").comValor(200.0).diasAtras(10).constroi();

        // persistimos todos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);
        leilaoDao.salvar(leilao3);

        List<Leilao> leiloesAntigos = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        assertEquals(1, leiloesAntigos.size());

    }

    @Test
    public void leiloesEncerradosComDataDentroDoIntervaloNaoDevemAparecer() {

        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();

        LeilaoBuilder criarLeilao1 = new LeilaoBuilder();
        LeilaoBuilder criarLeilao2 = new LeilaoBuilder();
        LeilaoBuilder criarLeilao3 = new LeilaoBuilder();

        Usuario mauricio
                = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        Leilao leilao1 = criarLeilao1.comDono(mauricio).comNome("Geladeira").comValor(1500.0).diasAtras(2).encerrado().constroi();

        Leilao leilao2 = criarLeilao2.comDono(mauricio).comNome("Xbox").comValor(700.0).diasAtras(5).encerrado().constroi();

        Leilao leilao3 = criarLeilao3.comDono(mauricio).comNome("SuperNintendo").comValor(200.0).diasAtras(10).encerrado().constroi();

        // persistimos todos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);
        leilaoDao.salvar(leilao3);

        List<Leilao> leiloesAntigos = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        assertEquals(0, leiloesAntigos.size());
    }

    @Test
    public void deveRetornarLeiloesDisputados() {
        Usuario mauricio = new Usuario("Mauricio", "mauricio@aniche.com.br");
        Usuario marcelo = new Usuario("Marcelo", "marcelo@aniche.com.br");

        Leilao leilao1 = new LeilaoBuilder()
                .comDono(marcelo)
                .comValor(3000.0)
                .constroi();

        Lance lance1 = new Lance(Calendar.getInstance(), mauricio, 3000.0, leilao1);
        Lance lance2 = new Lance(Calendar.getInstance(), marcelo, 3100.0, leilao1);

        leilao1.adicionaLance(lance1);
        leilao1.adicionaLance(lance2);

        Leilao leilao2 = new LeilaoBuilder()
                .comDono(mauricio)
                .comValor(3200.0)
                .constroi();

        Lance lance3 = new Lance(Calendar.getInstance(), mauricio, 3000.0, leilao2);
        Lance lance4 = new Lance(Calendar.getInstance(), marcelo, 3100.0, leilao2);
        Lance lance5 = new Lance(Calendar.getInstance(), mauricio, 3200.0, leilao2);
        Lance lance6 = new Lance(Calendar.getInstance(), marcelo, 3300.0, leilao2);
        Lance lance7 = new Lance(Calendar.getInstance(), mauricio, 3300.0, leilao2);
        Lance lance8 = new Lance(Calendar.getInstance(), marcelo, 3400.0, leilao2);
        Lance lance9 = new Lance(Calendar.getInstance(), mauricio, 3500.0, leilao2);

        leilao2.adicionaLance(lance3);
        leilao2.adicionaLance(lance4);
        leilao2.adicionaLance(lance5);
        leilao2.adicionaLance(lance6);
        leilao2.adicionaLance(lance7);
        leilao2.adicionaLance(lance8);
        leilao2.adicionaLance(lance9);

        usuarioDao.salvar(marcelo);
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes = leilaoDao.disputadosEntre(2500, 3500);

        assertEquals(1, leiloes.size());
        assertEquals(3200.0, leiloes.get(0).getValorInicial(), 0.00001);
    }

    @Test
    public void deveRetornarLeiloesComLanceSemRepetir() {
        Usuario mauricio = new Usuario("Mauricio", "mauricio@aniche.com.br");

        Leilao leilao1 = new LeilaoBuilder()
                .comDono(mauricio)
                .comValor(3000.0)
                .constroi();

        Lance lance1 = new Lance(Calendar.getInstance(), mauricio, 3000.0, leilao1);
        Lance lance2 = new Lance(Calendar.getInstance(), mauricio, 3000.0, leilao1);

        leilao1.adicionaLance(lance1);
        leilao1.adicionaLance(lance2);

        Leilao leilao2 = new LeilaoBuilder()
                .comDono(mauricio)
                .comValor(3200.0)
                .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(mauricio);

        assertEquals(1, leiloes.size());
    }

    @Test
    public void deveRetornarLeiloesValorMedioInicialLeiloes() {
        Usuario mauricio = new Usuario("Mauricio", "mauricio@aniche.com.br");

        Leilao leilao1 = new LeilaoBuilder()
                .comDono(mauricio)
                .comValor(3000.0)
                .constroi();

        Lance lance1 = new Lance(Calendar.getInstance(), mauricio, 100.0, leilao1);
        Lance lance2 = new Lance(Calendar.getInstance(), mauricio, 200.0, leilao1);

        leilao1.adicionaLance(lance1);
        leilao1.adicionaLance(lance2);

        Leilao leilao2 = new LeilaoBuilder()
                .comDono(mauricio)
                .comValor(3200.0)
                .constroi();

        Lance lance3 = new Lance(Calendar.getInstance(), mauricio, 100.0, leilao2);
        leilao2.adicionaLance(lance3);
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        double valorMedioDoUsuario = leilaoDao.getValorInicialMedioDoUsuario(mauricio);

        assertEquals(133.333, valorMedioDoUsuario, 0.001);
    }

    @Test
    public void deveDeletarLeilao() {
        Usuario mauricio = new Usuario("Mauricio", "mauricio@aniche.com.br");
        usuarioDao.salvar(mauricio);

        Leilao leilao = new LeilaoBuilder()
                .comDono(mauricio)
                .comValor(3000.0)
                .constroi();

        leilaoDao.deleta(leilao);

        session.flush();
        session.clear();

        assertNull(leilaoDao.porId(leilao.getId()));        

    }
}
