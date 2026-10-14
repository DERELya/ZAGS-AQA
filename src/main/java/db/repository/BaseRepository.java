package db.repository;

import db.utils.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Function;

public class BaseRepository {

    private static final Logger logger =
            LogManager.getLogger(BaseRepository.class);

    private void rollback(
            Transaction transaction,
            RuntimeException originalException) {

        if (transaction == null || !transaction.isActive()) {
            return;
        }

        try {
            transaction.rollback();
            logger.info("Транзакция отменена через rollback");
        } catch (RuntimeException rollbackException) {
            originalException.addSuppressed(rollbackException);
        }
    }

    protected final <T> T executeInTransaction(
            Function<Session, T> operation) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();
            logger.info("Начало транзакции Hibernate");
            try {
                T result = operation.apply(session);

                transaction.commit();
                logger.info("Транзакция Hibernate успешно завершена");
                return result;

            } catch (RuntimeException e) {
                logger.error(
                        "Ошибка при выполнении операции в транзакции Hibernate",
                        e
                );
                rollback(transaction, e);
                throw e;
            }
        }
    }

    protected final <T> T executeWithSession(
            Function<Session, T> operation) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            try {
                T result = operation.apply(session);

                logger.debug("Операция чтения через Hibernate выполнена");

                return result;

            } catch (RuntimeException e) {
                logger.error(
                        "Ошибка при чтении данных через Hibernate",
                        e
                );

                throw e;
            }
        }
    }
}
