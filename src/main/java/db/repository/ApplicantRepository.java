package db.repository;

import db.model.Applicant;

public class ApplicantRepository extends BaseRepository {

    public Integer save(Applicant applicant) {
        return executeInTransaction(session -> {
            session.persist(applicant);
            return applicant.getApplicantId();
        });
    }

    public Applicant findById(Integer applicantId) {
        return executeWithSession(
                session -> session.find(Applicant.class, applicantId)
        );
    }

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
