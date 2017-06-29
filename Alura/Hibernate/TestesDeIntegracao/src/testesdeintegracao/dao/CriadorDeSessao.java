package testesdeintegracao.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import testesdeintegracao.dominio.Lance;
import testesdeintegracao.dominio.Leilao;
import testesdeintegracao.dominio.Usuario;

@SuppressWarnings("deprecation")
public class CriadorDeSessao {

    private static AnnotationConfiguration config;
    private static SessionFactory sf;

    public Session getSession() {
        if (sf == null) {
            sf = getConfig().buildSessionFactory();
        }

        return (Session) sf.openSession();
    }

    public Configuration getConfig() {
        if (config == null) {
            config = new AnnotationConfiguration()
                    .addAnnotatedClass(Lance.class)
                    .addAnnotatedClass(Leilao.class)
                    .addAnnotatedClass(Usuario.class)
                    .setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
                    .setProperty("hibernate.connection.url", "jdbc:hsqldb:caelum.db;shutdown=true")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
                    .setProperty("hibernate.connection.username", "sa")
                    .setProperty("hibernate.connection.password", "")
                    .setProperty("hibernate.show_sql", "true");
        }
        return config;
    }
}
