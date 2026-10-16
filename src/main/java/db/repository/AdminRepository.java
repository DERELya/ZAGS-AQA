package db.repository;

import db.model.Admin;
import db.model.BirthCertificate;
import db.utils.DatabaseConnection;
import io.qameta.allure.Step;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AdminRepository {

    public AdminRepository() {
    }

    @Step("Получение из БД администратора c id: {staffId}")
    public Admin findById(long staffId) throws SQLException {
        String sql = """
                SELECT staffid,
                       surname,
                       name,
                       middlename,
                       dateofbirth,
                       passportnumber,
                       phonenumber
                FROM staff
                WHERE staffid = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, staffId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                Admin admin = new Admin();

                admin.setStaffid(resultSet.getInt("staffid"));
                admin.setSurname(resultSet.getString("surname"));
                admin.setName(resultSet.getString("name"));
                admin.setMiddleName(resultSet.getString("middlename"));
                admin.setDateofbirth(resultSet.getObject("dateofbirth", LocalDate.class));
                admin.setPassportNumber(resultSet.getString("passportnumber"));
                admin.setPhoneNumber(resultSet.getString("phonenumber"));


                return admin;
            }
        }
    }

    @Step("Удаление администратора c id: {staffId}")
    public void deleteById(long staffId) throws SQLException {
        String sql = """
                DELETE FROM staff
                WHERE staffid = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, staffId);

            statement.executeUpdate();
        }
    }
}
