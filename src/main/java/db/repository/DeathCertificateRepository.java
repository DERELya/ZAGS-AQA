package db.repository;

import db.model.DeathCertificate;

public class DeathCertificateRepository extends BaseRepository {
    public Integer save(DeathCertificate deathCertificate) {
        return executeInTransaction(session -> {
            session.persist(deathCertificate);
            return deathCertificate.getDeathcertificateid();
        });
    }

    public DeathCertificate findById(Integer deathcertificateid) {
        return executeWithSession(
                session -> session.find(DeathCertificate.class, deathcertificateid)
        );
    }

    public void deleteById(Integer deathcertificateid) {
        executeInTransaction(session -> {
            DeathCertificate deathCertificate =
                    session.find(DeathCertificate.class, deathcertificateid);

            if (deathCertificate != null) {
                session.remove(deathCertificate);
            }

            return null;
        });
    }
}
