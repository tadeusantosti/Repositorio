package leiloesTest;

import static org.junit.Assert.*;
import org.junit.Test;
import com.jayway.restassured.path.xml.XmlPath;
import br.com.caelum.leilao.modelo.Usuario;
import static com.jayway.restassured.RestAssured.*;
import java.util.List;

public class UsuariosWSTest {

    @Test
    public void deveRetornarListaDeUsuarios() {
        XmlPath path = given()
                .header("Accept", "application/xml")
                .get("/usuarios")
                .andReturn().xmlPath();

        Usuario usuario1 = path.getObject("list.usuario[0]", Usuario.class);
        Usuario usuario2 = path.getObject("list.usuario[1]", Usuario.class);

        Usuario esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Usuario esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");

        assertEquals(esperado1, usuario1);
        assertEquals(esperado2, usuario2);

    }

    @Test
    public void deveRetornarListaDeUsuarios2() {
        XmlPath path = given()
                .header("Accept", "application/xml")
                .get("/usuarios")
                .andReturn().xmlPath();

        List<Usuario> usuarios = path.getList("list.usuario", Usuario.class);

        Usuario esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Usuario esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");

        assertEquals(esperado1, usuarios.get(0));
        assertEquals(esperado2, usuarios.get(1));

    }
}
