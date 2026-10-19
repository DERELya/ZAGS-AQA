package db.hibernate.dao;

import db.model.BirthCertificate;
import io.qameta.allure.Step;

public class BirthCertificateDAO extends BaseDAO<BirthCertificate, Integer> {

    public BirthCertificateDAO() {
        super(BirthCertificate.class, BirthCertificate::getBirthCertificateId);
    }

    @Step("Получение из БД свидетельства о рождении с id: {birthCertificateId}")
    @Override
    public BirthCertificate findById(Integer birthCertificateId) {
        return super.findById(birthCertificateId);
    }

    @Step("Удаление свидетельства о рождении с id: {birthCertificateId}")
    @Override
    public void deleteById(Integer birthCertificateId) {
        super.deleteById(birthCertificateId);
    }

}
