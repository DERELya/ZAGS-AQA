package db.repository;

import db.model.Citizen;

public class CitizenRepository extends BaseRepository {

    public Integer save(Citizen citizen) {
        return executeInTransaction(session -> {
            session.persist(citizen);
            return citizen.getCitizenid();
        });
    }

    public Citizen findById(Integer citizenid) {
        return executeWithSession(
                session -> session.find(Citizen.class, citizenid)
        );
    }

    public void deleteById(Integer citizenid) {
        executeInTransaction(session -> {
            Citizen citizen =
                    session.find(Citizen.class, citizenid);

            if (citizen != null) {
                session.remove(citizen);
            }

            return null;
        });
    }
}
