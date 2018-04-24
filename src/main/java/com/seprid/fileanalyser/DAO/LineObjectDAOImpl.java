package com.seprid.fileanalyser.DAO;

import com.seprid.fileanalyser.entity.LineObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Default implementation of <code>LineObjectDAO</code>.
 * @see LineObjectDAO
 */
public class LineObjectDAOImpl implements LineObjectDAO {

    private Connection connection;

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
    public int createLineObject(LineObject object) {
        try (Statement statement = connection.createStatement()) {
        String createQuery = "INSERT INTO \"PUBLIC\".\"LINEOBJECT\" (\"VALUE\", \"LONGESTWORD\", \"SHORTESTWORD\", \"AVERAGEWORDLENGTH\", \"CONTAINERNAME\") " +
                "VALUES (\'" + object.getValue() + "\', \'" + object.getLongestWord() + "\', \'" + object.getShortestWord() +
                "\', "+ object.getAverageWordLength() + ", \'" + object.getContainerName() + "\')";
            statement.execute(createQuery);

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }


        } catch (SQLException e) {
            System.out.println("Error during creating new entry. Entry value:\'" + object.getValue() +
            "\'. Entry container name:\'" + object.getContainerName() + "\'.");
        }
        return Integer.parseInt(null);
    }

    /**
     * Perform 'Read' operation.
     * @param ID integer value that is a primary key for LineObject entity.
     * @return instance of <code>com.seprid.fileanalyser.entity.LineObject</code> from DB.
     */
    @Override
    public LineObject getLineObject(Integer ID) {
        try (Statement statement = connection.createStatement()){
        String getQuery = "SELECT t.* FROM PUBLIC.LINEOBJECT t where t.LINEID = " + ID;
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
        String updateQuery = "UPDATE \"PUBLIC\".\"LINEOBJECT\" t " +
                "SET " +
                "t.\"VALUE\" = \'" + updatedObject.getValue() + "\', " +
                "t.\"LONGESTWORD\" = \'" + updatedObject.getLongestWord() + "\', " +
                "t.\"SHORTESTWORD\" = \'" + updatedObject.getShortestWord() + "\', " +
                "t.\"AVERAGEWORDLENGTH\" = " + updatedObject.getAverageWordLength() + ", " +
                "t.\"CONTAINERNAME\" = \'" + updatedObject.getContainerName() + "\' " +
                "WHERE " +
                "t.\"LINEID\" = 2";
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
            String deleteQuery = "DELETE FROM \"PUBLIC\".\"LINEOBJECT\" WHERE \"LINEID\" = " + ID;
            ResultSet rs = statement.executeQuery(deleteQuery);
        } catch (SQLException e) {
            System.out.println("Error during deleting entry. Entry ID:\'" + ID + "\'.");
        }
    }

}
