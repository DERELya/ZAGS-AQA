package db.repository;

import db.model.DeathCertificate;
import io.qameta.allure.Step;

public class DeathCertificateRepository extends BaseRepository<DeathCertificate,Integer> {
    public  DeathCertificateRepository() {
        super(DeathCertificate.class,DeathCertificate::getDeathcertificateid);
    }
    @Step("Сохранение в БД свидетельства о смерти с данными: дата {deathCertificate.dateOfDeath}, место {deathCertificate.placeOfDeath}")
    @Override
    public Integer save(DeathCertificate deathCertificate) {
        return super.save(deathCertificate);
    }
    @Step("Получение из БД свидетельства о смерти с id: {deathCertificateId}")
    public DeathCertificate findById(Integer deathCertificateId) {
        return super.findById(deathCertificateId);
    }
    @Step("Удаление свидетельства о смерти с id: {deathCertificateId}")
    @Override
    public void deleteById(Integer deathCertificateId) {
        super.deleteById(deathCertificateId);
    }
}
