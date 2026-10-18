package db.hibernate;

import db.model.DeathCertificate;
import io.qameta.allure.Step;

public class DeathCertificateDAO extends BaseDAO<DeathCertificate,Integer> {
    public DeathCertificateDAO() {
        super(DeathCertificate.class,DeathCertificate::getDeathCertificateId);
    }

    @Step("Получение из БД свидетельства о смерти с id: {deathCertificateId}")
    @Override
    public DeathCertificate findById(Integer deathCertificateId) {
        return super.findById(deathCertificateId);
    }

    @Step("Удаление свидетельства о смерти с id: {deathCertificateId}")
    @Override
    public void deleteById(Integer deathCertificateId) {
        super.deleteById(deathCertificateId);
    }
}
