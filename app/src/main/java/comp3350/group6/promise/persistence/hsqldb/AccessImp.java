package comp3350.group6.promise.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import comp3350.group6.promise.objects.Access;
import comp3350.group6.promise.objects.Exceptions.PersistenceException;
import comp3350.group6.promise.objects.enumClasses.AccessRole;
import comp3350.group6.promise.persistence.AccessDao;
import comp3350.group6.promise.util.DBConnectorUtil;

public class AccessImp implements AccessDao {

    /*
     * Used to create a Access object from a SQL ResultSet
     */
    private Access createAccessObject(ResultSet rs) throws SQLException {
        int projectId = rs.getInt("projectId");
        int userId = rs.getInt("userId");
        String role = rs.getString("role");
        Timestamp startTime = rs.getTimestamp("startTime");

        return new Access(projectId, userId, role, startTime);
    }


    @Override
    public List<Access> getAccessByProject(int projectId) {
        final List<Access> userList = new ArrayList<Access>();

        try(Connection con = DBConnectorUtil.getConnection()){

            PreparedStatement pstmt = con.prepareStatement("select * from user, access where user.userId = access.userId and projectId = ?");
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Access acc = createAccessObject(rs);
                userList.add(acc);
            }

            pstmt.close();
            rs.close();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return userList;
    }

    @Override
    public List<Access> getAccessByUser(int userId) {
        final List<Access> userList = new ArrayList<Access>();

        try(Connection con = DBConnectorUtil.getConnection()){

            PreparedStatement pstmt = con.prepareStatement("select * from project, access where project.projectId = access.projectId and userId = ?");
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Access acc = createAccessObject(rs);
                userList.add(acc);
            }

            pstmt.close();
            rs.close();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return userList;
    }

    @Override
    public Access getAccessByIDs(int userId, int projectId){
        Access acc = null;

        try(Connection con = DBConnectorUtil.getConnection()){

            PreparedStatement pstmt = con.prepareStatement("select * from access where userId = ? and projectId = ?");
            pstmt.setInt(1, userId);
            pstmt.setInt(2, projectId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                acc = createAccessObject(rs);
            }

            pstmt.close();
            rs.close();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return acc;
    }

    @Override
    public Access insertAccess(Access access) {

        try(Connection con = DBConnectorUtil.getConnection()){

            PreparedStatement pstmt = con.prepareStatement("insert into access (projectId, userId, role, starttime) values (?, ?, ?, ?)");
            pstmt.setInt(1, access.getProjectId());
            pstmt.setInt(2, access.getUserId());
            pstmt.setString(3, access.getRole());
            pstmt.setTimestamp(4, access.getStartTime());
            pstmt.executeUpdate();

            pstmt.close();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return access;
    }

    @Override
    public Access updateAccess(Access access) {

        try(Connection con = DBConnectorUtil.getConnection()){

            PreparedStatement pstmt = con.prepareStatement("update access set role = ? where userId = ? and projectId = ?");
            pstmt.setString(1, access.getRole());
            pstmt.setInt(2, access.getUserId());
            pstmt.setInt(3, access.getProjectId());
            pstmt.executeUpdate();

            pstmt.close();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return access;
    }

    /* Returns the userID of the people that have this role */
    @Override
    public List<Integer> getRoleByProjectID( int projectID, AccessRole role ) {
        //CREATE MEMORY TABLE PUBLIC.ACCESS(PROJECTID INTEGER NOT NULL,USERID INTEGER NOT NULL,
        // ROLE VARCHAR(255) NOT NULL,STARTTIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP)
        final List<Integer> userList = new ArrayList<Integer>();

        try(Connection con = DBConnectorUtil.getConnection()){

            PreparedStatement pstmt = con.prepareStatement("SELECT userID FROM Access WHERE projectID = ? AND role = ?");
            pstmt.setInt(1, projectID);
            pstmt.setString(2, role.name());
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int userID = rs.getInt("userID");
                userList.add(userID);
            }

            pstmt.close();
            rs.close();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return userList;
    }


    /* check if this userID has access to this projectID */
    @Override
    public boolean hasAccess( int userID, int projectID ){

        boolean access; //false by default

        try(Connection con = DBConnectorUtil.getConnection()){

            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Access where userID = ? and projectID = ?");
            pstmt.setInt(1, userID);
            pstmt.setInt(2, projectID);
            ResultSet rs = pstmt.executeQuery();

            //if there are any more rows, this will return true (the user has access),
            //else, it'll return false (the user doesn't have access)
            access = rs.next();

            pstmt.close();
            rs.close();

        }

        catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return access;

    }

}
