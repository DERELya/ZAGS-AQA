package db.repository;

import db.model.Citizen;
import io.qameta.allure.Step;

public class CitizenRepository extends BaseRepository<Citizen,Integer> {

    public CitizenRepository() {
        super(Citizen.class,Citizen::getCitizenid);
    }
    @Step("Сохранение в БД заявителя с данными: фамилия {citizen.surname}, имя {citizen.name},отчество {citizen.middleName}")
    @Override
    public Integer save(Citizen citizen) {
        return super.save(citizen);
    }
    @Step("Получение из БД гражданина с id: {citizenid}")
    @Override
    public Citizen findById(Integer citizenId) {
       return super.findById(citizenId);
    }
    @Step("Удаление гражданина с id: {citizenId}")
    @Override
    public void deleteById(Integer citizenId) {
        super.deleteById(citizenId);
    }
}
