package db.utils;

import db.model.Admin;
import db.model.Applicant;
import db.model.Citizen;
import db.model.DeathCertificate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import utils.TestConfig;

public final class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY =
            new Configuration()
                    .addAnnotatedClass(Applicant.class)
                    .addAnnotatedClass(Citizen.class)
                    .addAnnotatedClass(DeathCertificate.class)
                    .addAnnotatedClass(Admin.class)
                    .setProperty("hibernate.connection.driver_class",
                            "org.postgresql.Driver")
                    .setProperty("hibernate.connection.url",
                            TestConfig.getDBUrl())
                    .setProperty("hibernate.connection.username",
                            TestConfig.getDbUsername())
                    .setProperty("hibernate.connection.password",
                            TestConfig.getDbPassword())
                    .setProperty("hibernate.hbm2ddl.auto", "validate")
                    .setProperty("hibernate.default_schema","reg_office")
                    .buildSessionFactory();

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

}
