package db.repository;

import db.model.Admin;
import io.qameta.allure.Step;

public class AdminRepository extends BaseRepository {
    @Step("Сохранение в БД администратора с данными:фамилия {admin.surname},имя {admin.name},отчество {admin.middleName}")
    public Integer save(Admin admin) {
        return executeInTransaction(session -> {
            session.persist(admin);
            return admin.getStaffid();
        });
    }
    @Step("Получение из БД администратора c id: {staffId}")
    public Admin findById(Integer staffId) {
        return executeWithSession(
                session -> session.find(Admin.class, staffId)
        );
    }
    @Step("Удаление администратора c id: {staffId}")
    public void deleteById(Integer staffId) {
        executeInTransaction(session -> {
            Admin admin =
                    session.find(Admin.class, staffId);

            if (staffId != null) {
                session.remove(admin);
            }

            return null;
        });
    }
}
