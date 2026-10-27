package db.hibernate;

import db.model.BirthCertificate;
import db.model.DeathCertificate;
import db.model.MerrigeCertificate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import utils.TestConfig;

public final class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY =
            new Configuration()
                    .addAnnotatedClass(DeathCertificate.class)
                    .addAnnotatedClass(BirthCertificate.class)
                    .addAnnotatedClass(MerrigeCertificate.class)
                    .setProperty("hibernate.connection.driver_class",
                            "org.postgresql.Driver")
                    .setProperty("hibernate.connection.url",
                            TestConfig.getDBUrl())
                    .setProperty("hibernate.connection.username",
                            TestConfig.getDbUsername())
                    .setProperty("hibernate.connection.password",
                            TestConfig.getDbPassword())
                    .setProperty("hibernate.hbm2ddl.auto", "validate")
                    .setProperty("hibernate.default_schema", "reg_office")
                    .buildSessionFactory();

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

}
