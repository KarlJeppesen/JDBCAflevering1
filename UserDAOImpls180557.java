package dal;

import dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO Rename class
public class UserDAOImpls180557 implements IUserDAO {
    //TODO Make a connection to the database - Comment: SSL has been disabled

    //I have updated username and password for the database.

    private Connection createConnection() throws DALException {
        try {
            return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s180557?"
                    + "user=s180557&password=SnM6HsTt8iPhYpasthnCW&useSSL=false\")");
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public UserDTO getUser(int userId) throws DALException {
        //TODO Implement this

        UserDTO user = new UserDTO();

        try {

            Connection c = createConnection();
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM userTable where id = "+userId+";");

            //TODO: Make a user from the resultset

            user.setUserId(userId);
            user.setUserName(resultSet.getNString(2));
            user.setIni(resultSet.getNString(3));

            ArrayList<String> roller = new ArrayList<String>();
            roller.add(resultSet.getNString(4));
            user.setRoles(roller);

            c.close();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return user;
    }

    @Override
    public List<UserDTO> getUserList() throws DALException {
        //TODO Implement this

        ArrayList<UserDTO> users = new ArrayList<UserDTO>();

        try {

            Connection c = createConnection();
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM userTable;");

            while (resultSet.next()) {

                String userIDString = resultSet.getNString(1);
                int userID = Integer.parseInt(userIDString);
                users.add(getUser(userID));
            }

            c.close();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return users;
    }

    @Override
    public void createUser(UserDTO user) throws DALException {
        //TODO Implement this

        try {

            Connection c = createConnection();
            Statement statement = c.createStatement();

            ResultSet resultSet = statement.executeQuery("INSERT INTO userTable (userID, userName, ini, roles) VALUES ("+user.getUserId()+", '"+user.getUserName()+"', '"+user.getIni()+"', '"+user.getRoles()+"');");

            c.close();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }

    @Override
    public void updateUser(UserDTO user) throws DALException {
        //TODO Implement this - Comment: Update requires match for userID og userId cannot be changed.

        try {

            Connection c = createConnection();
            Statement statement = c.createStatement();

            ResultSet resultSet = statement.executeQuery("UPDATE userTable SET userName = '"+user.getUserName()+"', ini = '"+user.getIni()+"', roles = '"+user.getRoles()+"' WHERE userId = "+user.getUserId()+";");
            c.close();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        //TODO Implement this

        try {

            Connection c = createConnection();
            Statement statement = c.createStatement();

            ResultSet resultSet = statement.executeQuery("DELETE FROM userTable WHERE userId="+userId+";");
            c.close();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }
}