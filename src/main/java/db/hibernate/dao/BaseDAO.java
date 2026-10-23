package db.hibernate.dao;

import db.hibernate.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Objects;
import java.util.function.Function;

public class BaseDAO<T, ID> {

    private final Class<T> entityClass;
    private final Function<T, ID> idExtractor;

    private final Logger logger =
            LogManager.getLogger(getClass());

    protected BaseDAO(
            Class<T> entityClass,
            Function<T, ID> idExtractor) {

        this.entityClass = entityClass;
        this.idExtractor = idExtractor;
    }

    public ID save(T entity) {
        Objects.requireNonNull(entity, "Entity не должна быть null");

        return executeInTransaction(
                "Сохранение " + entityClass.getSimpleName(),
                session -> {
                    session.persist(entity);
                    session.flush();

                    return idExtractor.apply(entity);
                }
        );
    }

    public T findById(ID id) {
        return executeWithSession(
                "Поиск " + entityClass.getCanonicalName() + ", ID=" + id,
                session -> session.find(entityClass, id)
        );
    }

    public void deleteById(ID id) {
        executeInTransaction(
                "Удаление " + entityClass.getSimpleName() + ", ID=" + id,
                session -> {
                    T entity = session.find(entityClass, id);

                    if (entity != null) {
                        session.remove(entity);
                    }

                    return null;
                }
        );
    }

    protected final <R> R executeInTransaction(
            String operationName,
            Function<Session, R> operation) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                R result = operation.apply(session);

                transaction.commit();

                logger.info(
                        "{} выполнено успешно",
                        operationName
                );

                return result;

            } catch (RuntimeException e) {
                rollback(transaction, e);

                logger.error(
                        "{}: ошибка ",
                        operationName,
                        e
                );

                throw e;
            }
        }
    }

    protected final <R> R executeWithSession(
            String operationName,
            Function<Session, R> operation) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            try {
                R result = operation.apply(session);

                logger.debug(
                        "{} выполнено ; запись найдена: {}",
                        operationName,
                        result != null
                );

                return result;

            } catch (RuntimeException e) {
                logger.error(
                        "{}: ошибка при чтении",
                        operationName,
                        e
                );

                throw e;
            }
        }
    }

    private void rollback(
            Transaction transaction,
            RuntimeException originalException) {

        if (transaction == null || !transaction.isActive()) {
            return;
        }

        try {
            transaction.rollback();
            logger.debug("Транзакция отменена после ошибки");

        } catch (RuntimeException rollbackException) {
            originalException.addSuppressed(rollbackException);
        }
    }
}
