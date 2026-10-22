package db.dao;

import db.model.Citizen;
import db.utils.DatabaseConnection;
import io.qameta.allure.Step;
import model.data.Gender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CitizenDAO {

    public CitizenDAO() {

    }

    @Step("Получение из БД гражданина с id: {citizenId}")
    public Citizen findById(Integer citizenId) throws SQLException {
        String sql = """
                SELECT citizenid,
                       surname,
                       name,
                       middlename,
                       passportnumber,
                       dateofbirth,
                       gender,
                       registration_address
                FROM citizens
                WHERE citizenid = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, citizenId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                Citizen citizen = new Citizen();

                citizen.setCitizenId(resultSet.getInt("citizenid"));
                citizen.setSurname(resultSet.getString("surname"));
                citizen.setName(resultSet.getString("name"));
                citizen.setMiddleName(resultSet.getString("middlename"));
                citizen.setPassportNumber(resultSet.getString("passportnumber"));
                citizen.setDateOfBirth(resultSet.getString("dateofbirth"));
                citizen.setGender(Gender.fromValue(resultSet.getString("gender")));
                citizen.setRegistrationAddress(resultSet.getString("registration_address"));

                return citizen;
            }
        }
    }

    @Step("Удаление гражданина с id: {citizenId}")
    public void deleteById(Integer citizenId) throws SQLException {
        String deleteCitizenSql = """
                DELETE FROM citizens
                WHERE citizenid = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                try (PreparedStatement statement =
                             connection.prepareStatement(deleteCitizenSql)) {
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
