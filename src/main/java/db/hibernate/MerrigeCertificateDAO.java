package db.hibernate;

import db.model.MerrigeCertificate;
import io.qameta.allure.Step;

public class MerrigeCertificateDAO extends BaseDAO<MerrigeCertificate, Integer> {

    public MerrigeCertificateDAO() {
        super(MerrigeCertificate.class, MerrigeCertificate::getMerrigeCertificateId);
    }

    @Step("Получение из БД свидетельства о смерти с id: {merrigeCertificateId}")
    @Override
    public MerrigeCertificate findById(Integer merrigeCertificateId) {
        return super.findById(merrigeCertificateId);
    }

    @Step("Удаление свидетельства о смерти с id: {merrigeCertificateId}")
    @Override
    public void deleteById(Integer merrigeCertificateId) {
        super.deleteById(merrigeCertificateId);
    }
}
