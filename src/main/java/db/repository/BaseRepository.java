package db.repository;

import db.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Function;

public class BaseRepository {

    private void rollback(
            Transaction transaction,
            RuntimeException originalException) {

        if (transaction == null || !transaction.isActive()) {
            return;
        }

        try {
            transaction.rollback();
        } catch (RuntimeException rollbackException) {
            originalException.addSuppressed(rollbackException);
        }
    }

    protected final <T> T executeInTransaction(
            Function<Session, T> operation) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                T result = operation.apply(session);

                transaction.commit();

                return result;

            } catch (RuntimeException e) {
                rollback(transaction, e);
                throw e;
            }
        }
    }

    protected final <T> T executeWithSession(
            Function<Session, T> operation) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return operation.apply(session);
        }
    }
}
