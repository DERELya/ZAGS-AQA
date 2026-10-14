package db.repository;

import db.model.Citizen;
import io.qameta.allure.Step;

public class CitizenRepository extends BaseRepository {
    @Step("Сохранение в БД заявителя с данными: фамилия {citizen.surname}, имя {citizen.name},отчество {citizen.middleName}")
    public Integer save(Citizen citizen) {
        return executeInTransaction(session -> {
            session.persist(citizen);
            return citizen.getCitizenid();
        });
    }
    @Step("Получение из БД гражданина с id: {citizenid}")
    public Citizen findById(Integer citizenId) {
        return executeWithSession(
                session -> session.find(Citizen.class, citizenId)
        );
    }
    @Step("Удаление гражданина с id: {citizenId}")
    public void deleteById(Integer citizenId) {
        executeInTransaction(session -> {
            Citizen citizen =
                    session.find(Citizen.class, citizenId);

            if (citizen != null) {
                session.remove(citizen);
            }

            return null;
        });
    }
}
