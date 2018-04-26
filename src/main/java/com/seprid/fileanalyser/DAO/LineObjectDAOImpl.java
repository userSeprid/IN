package com.seprid.fileanalyser.DAO;

import com.seprid.fileanalyser.entity.LineObject;

import java.sql.*;

/**
 * Default implementation of <code>LineObjectDAO</code>.
 * @see LineObjectDAO
 */
public class LineObjectDAOImpl implements LineObjectDAO {

    private Connection connection;

    String createQuery = "INSERT INTO `dev`.`line_object` " +
            "(value, longest_word, shortest_word, average_word_length, container_name, line_length) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Connection receives by constructor because it make tests a bit easy to write and decouple configuration from logic.
     * @param conn an java.sql.Connection that used here for accessing to DB.
     * @see Connection
     */
    public LineObjectDAOImpl(Connection conn) {
        this.connection = conn;
    }

    /**
     * Perform 'Create' operation.
     * @param object instance <code>com.seprid.fileanalyser.entity.LineObject</code> that will be persist in DB.
     * @return return ID of persisted object
     * @see LineObject
     */
    @Override
    public void createLineObject(LineObject object) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(createQuery);) {

            preparedStatement.setString(1, object.getValue());
            preparedStatement.setString(2, object.getLongestWord());
            preparedStatement.setString(3, object.getShortestWord());
            preparedStatement.setInt(4, object.getAverageWordLength());
            preparedStatement.setString(5, object.getContainerName());
            preparedStatement.setInt(6, object.getLineLength());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error during creating new entry. Entry value:\'" + object.getValue() +
            "\'. Entry container name:\'" + object.getContainerName() + "\'.");
            e.printStackTrace();
        }
    }

    /**
     * Perform 'Read' operation.
     * @param ID integer value that is a primary key for LineObject entity.
     * @return instance of <code>com.seprid.fileanalyser.entity.LineObject</code> from DB.
     */
    @Override
    public LineObject getLineObject(Integer ID) {
        try (Statement statement = connection.createStatement()){
        String getQuery = "SELECT t.* FROM dev.line_object t where t.LINEID = " + ID;
            ResultSet rs = statement.executeQuery(getQuery);
            while (rs.next()) {
                LineObject object = new LineObject();
                object.setLineID(rs.getInt("LINEID"));
                object.setValue(rs.getString("LONGESTWORD"));
                object.setShortestWord("SHORTESTWORD");
                object.setAverageWordLength(rs.getInt("AVERAGEWORDLENGTH"));
                object.setContainerName(rs.getString("CONTAINERNAME"));
                return object;
            }
        } catch (SQLException e) {
            System.out.println("Can't get data from DB. Used ID:\'" + ID + "\'");
        }
        return null;
    }

    /**
     * Perform 'Update' operation.
     * @param updatedObject instance of <code>com.seprid.fileanalyser.entity.LineObject</code> that will replace it copy in DB.
     */
    @Override
    public void updateLineObject(LineObject updatedObject) {
        try (Statement statement = connection.createStatement()) {
        String updateQuery = "UPDATE \"dev\".\"line_object\" t " +
                "SET " +
                "t.\"VALUE\" = \'" + updatedObject.getValue() + "\', " +
                "t.\"LONGESTWORD\" = \'" + updatedObject.getLongestWord() + "\', " +
                "t.\"SHORTESTWORD\" = \'" + updatedObject.getShortestWord() + "\', " +
                "t.\"AVERAGEWORDLENGTH\" = " + updatedObject.getAverageWordLength() + ", " +
                "t.\"CONTAINERNAME\" = \'" + updatedObject.getContainerName() + "\' " +
                "t.\"LINELENGTH\" = " + updatedObject.getLineLength() + " " +
                "WHERE " +
                "t.\"LINEID\" = " + updatedObject.getLineID();
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            System.out.println("Error during updating entry. Entry value:\'" + updatedObject.getValue() +
                    "\'. Entry container name:\'" + updatedObject.getContainerName() + "\'.");
        }
    }

    /**
     * Perform 'Delete' operation.
     * @param ID integer value that is a primary key for <code>com.seprid.fileanalyser.entity.LineObject</code> entity.
     */
    @Override
    public void deleteLineObject(int ID) {
        try (Statement statement = connection.createStatement()) {
            String deleteQuery = "DELETE FROM \"dev\".\"line_object\" WHERE \"LINEID\" = " + ID;
            ResultSet rs = statement.executeQuery(deleteQuery);
        } catch (SQLException e) {
            System.out.println("Error during deleting entry. Entry ID:\'" + ID + "\'.");
        }
    }

}
