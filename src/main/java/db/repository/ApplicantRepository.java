package db.repository;

import db.model.Applicant;
import io.qameta.allure.Step;

public class ApplicantRepository extends BaseRepository {
    @Step("Сохранение в БД заявителя с данными:фамилия {applicant.surname},имя {applicant.name},отчество {applicant.middleName}")
    public Integer save(Applicant applicant) {
        return executeInTransaction(session -> {
            session.persist(applicant);
            return applicant.getApplicantId();
        });
    }
    @Step("Получение из БД заявителя с id: {applicantId}")
    public Applicant findById(Integer applicantId) {
        return executeWithSession(
                session -> session.find(Applicant.class, applicantId)
        );
    }
    @Step("Удаление заявителя c id: {applicantId}")
    public void deleteById(Integer applicantId) {
        executeInTransaction(session -> {
            Applicant applicant =
                    session.find(Applicant.class, applicantId);

            if (applicant != null) {
                session.remove(applicant);
            }

            return null;
        });
    }
}
