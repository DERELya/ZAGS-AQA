package db.repository;

import db.model.Admin;
import io.qameta.allure.Step;

public class AdminRepository extends BaseRepository<Admin,Integer> {

    public AdminRepository() {
        super(Admin.class, Admin::getStaffid);
    }
    @Step("Сохранение в БД администратора с данными:фамилия {admin.surname},имя {admin.name},отчество {admin.middleName}")
    @Override
    public Integer save(Admin admin) {
        return super.save(admin);
    }
    @Step("Получение из БД администратора c id: {staffId}")
    @Override
    public Admin findById(Integer staffId) {
        return super.findById(staffId);
    }
    @Step("Удаление администратора c id: {staffId}")
    public void deleteById(Integer staffId) {
        super.deleteById(staffId);
    }
}
