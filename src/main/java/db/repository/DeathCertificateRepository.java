package db.repository;

import db.model.DeathCertificate;
import io.qameta.allure.Step;

public class DeathCertificateRepository extends BaseRepository {
    @Step("Сохранение в БД свидетельства о смерти с данными: дата {deathCertificate.dateOfDeath}, место {deathCertificate.placeOfDeath}")
    public Integer save(DeathCertificate deathCertificate) {
        return executeInTransaction(session -> {
            session.persist(deathCertificate);
            return deathCertificate.getDeathcertificateid();
        });
    }
    @Step("Получение из БД свидетельства о смерти с id: {deathCertificateId}")
    public DeathCertificate findById(Integer deathCertificateId) {
        return executeWithSession(
                session -> session.find(DeathCertificate.class, deathCertificateId)
        );
    }
    @Step("Удаление свидетельства о смерти с id: {deathCertificateId}")
    public void deleteById(Integer deathCertificateId) {
        executeInTransaction(session -> {
            DeathCertificate deathCertificate =
                    session.find(DeathCertificate.class, deathCertificateId);

            if (deathCertificate != null) {
                session.remove(deathCertificate);
            }

            return null;
        });
    }
}
