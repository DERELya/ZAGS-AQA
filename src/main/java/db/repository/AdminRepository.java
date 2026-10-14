package db.repository;

import db.model.Admin;

public class AdminRepository extends BaseRepository {

    public Integer save(Admin admin) {
        return executeInTransaction(session -> {
            session.persist(admin);
            return admin.getStaffid();
        });
    }

    public Admin findById(Integer staffid) {
        return executeWithSession(
                session -> session.find(Admin.class, staffid)
        );
    }

    public void deleteById(Integer staffid) {
        executeInTransaction(session -> {
            Admin admin =
                    session.find(Admin.class, staffid);

            if (staffid != null) {
                session.remove(admin);
            }

            return null;
        });
    }
}
