package db.dao;

import db.utils.DatabaseConnection;
import io.qameta.allure.Step;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApplicationDAO {

    @Step("Удаление заявки c id гражданина: {citizenId}")
    public void deleteByCitizenId(Integer citizenId) throws SQLException {
        String deleteApplicationSql = """
                DELETE FROM applications
                WHERE citizenid = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                try (PreparedStatement statement =
                             connection.prepareStatement(deleteApplicationSql)) {
                    statement.setInt(1, citizenId);
                    statement.executeUpdate();
                }

                connection.commit();

            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    e.addSuppressed(rollbackException);
                }

                throw e;
            }
        }
    }
}
