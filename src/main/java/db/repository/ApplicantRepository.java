package db.repository;

import db.model.Applicant;
import io.qameta.allure.Step;

public class ApplicantRepository extends BaseRepository<Applicant, Integer> {

    public ApplicantRepository() {
        super(Applicant.class, Applicant::getApplicantId);
    }

    @Step("Сохранение в БД заявителя с данными:фамилия {applicant.surname},имя {applicant.name},отчество {applicant.middleName}")
    @Override
    public Integer save(Applicant applicant) {
        return super.save(applicant);
    }

    @Step("Получение из БД заявителя с id: {applicantId}")
    @Override
    public Applicant findById(Integer applicantId) {
        return super.findById(applicantId);
    }

    @Step("Удаление заявителя c id: {applicantId}")
    @Override
    public void deleteById(Integer applicantId) {
        super.deleteById(applicantId);
    }
}
