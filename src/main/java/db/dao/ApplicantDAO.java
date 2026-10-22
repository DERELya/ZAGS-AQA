package db.dao;

import db.model.Applicant;
import db.utils.DatabaseConnection;
import io.qameta.allure.Step;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicantDAO {

    public ApplicantDAO() {
    }


    @Step("Получение из БД заявителя с id: {applicantId}")

    public Applicant findById(Integer applicantId) throws SQLException {
        String sql = """
                SELECT applicantid,
                       surname,
                       name,
                       middlename,
                       passportnumber,
                       phonenumber,
                       registration_address
                FROM applicants
                WHERE applicantid = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, applicantId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                Applicant applicant = new Applicant();

                applicant.setApplicantId(resultSet.getInt("applicantid"));
                applicant.setSurname(resultSet.getString("surname"));
                applicant.setName(resultSet.getString("name"));
                applicant.setMiddleName(resultSet.getString("middlename"));
                applicant.setPassportNumber(resultSet.getString("passportnumber"));
                applicant.setPhoneNumber(resultSet.getString("phonenumber"));
                applicant.setRegistrationAddress(resultSet.getString("registration_address"));

                return applicant;
            }
        }
    }

    @Step("Удаление заявителя с id: {applicantId}")
    public void deleteById(Integer applicantId) throws SQLException {
        String deleteApplicantSql = """
                DELETE FROM applicants
                WHERE applicantid = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                try (PreparedStatement statement =
                             connection.prepareStatement(deleteApplicantSql)) {
                    statement.setInt(1, applicantId);
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
