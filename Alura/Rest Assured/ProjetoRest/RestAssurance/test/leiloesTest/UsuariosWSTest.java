package leiloesTest;

import br.com.caelum.leilao.modelo.Leilao;
import static org.junit.Assert.*;
import org.junit.Test;
import com.jayway.restassured.path.xml.XmlPath;
import br.com.caelum.leilao.modelo.Usuario;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import java.util.List;

public class UsuariosWSTest {

    @Test
    public void deveRetornarUmaListaDeUsuarios() {
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
    public void deveRetornarUmaListaDeUsuariosParteDois() {
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

    @Test
    public void deveRetornarUmUsuariosPassandoParametro() {
        JsonPath path = given()
                .queryParameter("usuario.id", 1)
                .header("Accept", "application/json")
                .get("/usuarios/show")
                .andReturn().jsonPath();

        Usuario usuario = path.getObject("usuario", Usuario.class);
        Usuario esperado = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");

        assertEquals(esperado, usuario);
    }

    @Test
    public void deveRetornarUmLeiloesComParametro() {
        JsonPath path = given()
                .parameter("leilao.id", 1)
                .header("Accept", "application/json")
                .get("/leiloes/show")
                .andReturn().jsonPath();

        Leilao leilao = path.getObject("leilao", Leilao.class);

        Usuario usuarioEsperado = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Leilao leilaoEsperado = new Leilao(1L, "Geladeira", 800.00, usuarioEsperado, false);

        assertEquals(leilaoEsperado, leilao);
    }

    @Test
    public void deveRetornarTodosOsLeiloesComParametro() {
        XmlPath path = given()
                .parameter("leilao.id", 1)
                .header("Accept", "application/xml")
                .get("/leiloes/total")
                .andReturn().xmlPath();

        int leiloes = path.getInt("int");

        assertEquals(2, leiloes);
    }

    @Test
    public void deveAdicionarUmUsuario() {
        Usuario jose = new Usuario("Jose dos Santos", "jose@dasilva.com");

        XmlPath retorno
                = given()
                        .header("Accept", "application/xml")
                        .contentType("application/xml")
                        .body(jose)
                        .expect()
                        .statusCode(200)
                        .when()
                        .post("/usuarios")
                        .andReturn()
                        .xmlPath();

        Usuario resposta = retorno.getObject("usuario", Usuario.class);

        assertEquals("Jose dos Santos", resposta.getNome());
        assertEquals("jose@dasilva.com", resposta.getEmail());
    }

    @Test
    public void deveDeletarUmUsuario() {
        Usuario jose = new Usuario("Jose dos Santos", "jose@dasilva.com");

        XmlPath retorno
                = given()
                        .header("Accept", "application/xml")
                        .contentType("application/xml")
                        .body(jose)
                        .expect()
                        .statusCode(200)
                        .when()
                        .post("/usuarios")
                        .andReturn()
                        .xmlPath();

        Usuario usuarioAdicionado = retorno.getObject("usuario", Usuario.class);

        assertEquals("Jose dos Santos", usuarioAdicionado.getNome());
        assertEquals("jose@dasilva.com", usuarioAdicionado.getEmail());

        given()
                .contentType("application/xml").body(usuarioAdicionado)
                .expect().statusCode(200)
                .when().delete("/usuarios/deleta").andReturn().asString();
    }

    @Test
    public void deveAdicionarERemoverUmLeilao() {
        Usuario jose = new Usuario("Jose dos Santos", "jose@dasilva.com");

        Leilao leilao = new Leilao();
        leilao.setNome("GELADEIRA");
        leilao.setUsado(false);
        leilao.setValorInicial(200.00);
        leilao.setUsuario(jose);

        XmlPath retorno
                = given()
                        .header("Accept", "application/xml")
                        .contentType("application/xml")
                        .body(leilao)
                        .expect()
                        .statusCode(200)
                        .when()
                        .post("/leiloes")
                        .andReturn()
                        .xmlPath();

        Leilao resposta = retorno.getObject("usuario", Leilao.class);

        assertEquals("GELADEIRA", resposta.getNome());
        assertEquals(leilao.getValorInicial(), resposta.getValorInicial());
        assertEquals(jose, resposta.getUsuario());

        given()
                .contentType("application/xml").body(resposta)
                .expect().statusCode(200)
                .when().delete("/leiloes/deletar").andReturn().asString();
    }

    @Test
    public void deveGerarUmCookie() {
        expect()
                .cookie("rest-assured", "funciona")
                .when()
                .get("/cookie/teste");
    }

    @Test
    public void deveGerarUmHeader() {
        expect()
                .header("novo-header", "abc")
                .when()
                .get("/cookie/teste");
    }
}
