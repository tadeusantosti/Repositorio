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

    @Test
    public void deveDeletarUsuario() {
        Usuario mauricio = new Usuario("Mauricio", "mauricio@aniche.com.br");
        usuarioDao.salvar(mauricio);

        usuarioDao.deletar(mauricio);

        session.flush();
        session.clear();

        Usuario usuarioDeletado = usuarioDao.porNomeEEmail("Mauricio", "mauricio@aniche.com.br");

        Assert.assertNull(usuarioDeletado);

    }

    @Test
    public void deveAtualizarUsuario() {
        Usuario usuario = new Usuario("Mauricio", "mauricio@aniche.com.br");
        usuarioDao.salvar(usuario);

        session.flush();

        usuario.setNome("Tadeu");
        usuario.setEmail("tadeu@alura.com.br");

        usuarioDao.atualizar(usuario);

        session.flush();

        Usuario usuarioAntigo = usuarioDao.porNomeEEmail("Mauricio", "mauricio@aniche.com.br");
        Usuario usuarioAtualizado = usuarioDao.porNomeEEmail("Tadeu", "tadeu@alura.com.br");

        Assert.assertNull(usuarioAntigo);
        Assert.assertNotNull(usuarioAtualizado);

    }

}
