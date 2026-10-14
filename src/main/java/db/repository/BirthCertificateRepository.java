package db.repository;

import db.model.BirthCertificate;
import db.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BirthCertificateRepository {

    public Integer save(BirthCertificate certificate)
            throws SQLException {

        String sql = """
                INSERT INTO reg_office.birthcertificates (
                    citizenid,
                    placeofbirth,
                    mother,
                    father,
                    grandma,
                    grandpa
                )
                VALUES (?, ?, ?, ?, ?, ?)
                RETURNING birthcertificateid
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, certificate.getCitizenId());
            statement.setString(2, certificate.getPlaceOfBirth());
            statement.setString(3, certificate.getMother());
            statement.setString(4, certificate.getFather());
            statement.setString(5, certificate.getGrandma());
            statement.setString(6, certificate.getGrandpa());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException(
                            "Не удалось получить ID свидетельства о рождении"
                    );
                }
                int id = resultSet.getInt("birthcertificateid");
                certificate.setBirthCertificateId(id);

                return id;
            }
        }
    }

    public BirthCertificate findById(Integer birthCertificateId)
            throws SQLException {

        String sql = """
                SELECT birthcertificateid,
                       citizenid,
                       placeofbirth,
                       mother,
                       father,
                       grandma,
                       grandpa
                FROM reg_office.birthcertificates
                WHERE birthcertificateid = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, birthCertificateId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                BirthCertificate certificate =
                        new BirthCertificate();

                certificate.setBirthCertificateId(resultSet.getInt("birthcertificateid"));
                certificate.setCitizenId(resultSet.getInt("citizenid"));
                certificate.setPlaceOfBirth(resultSet.getString("placeofbirth"));
                certificate.setMother(resultSet.getString("mother"));
                certificate.setFather(resultSet.getString("father"));
                certificate.setGrandma(resultSet.getString("grandma"));
                certificate.setGrandpa(resultSet.getString("grandpa"));

                return certificate;
            }
        }
    }

    public void deleteById(Integer birthCertificateId)
            throws SQLException {

        String sql = """
                DELETE FROM reg_office.birthcertificates
                WHERE birthcertificateid = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, birthCertificateId);

            statement.executeUpdate();
        }
    }

}
