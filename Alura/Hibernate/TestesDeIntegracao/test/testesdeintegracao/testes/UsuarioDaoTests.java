package testesdeintegracao.testes;

import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import testesdeintegracao.dao.CriadorDeSessao;
import testesdeintegracao.dao.UsuarioDao;
import testesdeintegracao.dominio.Leilao;
import testesdeintegracao.dominio.Usuario;

public class UsuarioDaoTests {

    private Session session;
    private UsuarioDao usuarioDao;

    @Before
    public void inicializaCenarios() {
        session = (Session) new CriadorDeSessao().getSession();
        usuarioDao = new UsuarioDao(session);
    }

    @After
    public void encerraCenarios() {
        session.close();
    }

    @Test
    public void deveEncontrarPeloNomeEEmail() {

        // criando um usuario e salvando antes
        // de invocar o porNomeEEmail
        Usuario novoUsuario = new Usuario("João da Silva", "joao@dasilva.com.br");
        usuarioDao.salvar(novoUsuario);

        // agora buscamos no banco
        Usuario usuarioDoBanco = usuarioDao
                .porNomeEEmail("João da Silva", "joao@dasilva.com.br");

        assertEquals("João da Silva", usuarioDoBanco.getNome());
        assertEquals("joao@dasilva.com.br", usuarioDoBanco.getEmail());

        Usuario usuarioNulo = usuarioDao
                .porNomeEEmail("Jose da Silva", "jose@dasilva.com.br");

        Assert.assertNull(usuarioNulo);

    }
    
}
