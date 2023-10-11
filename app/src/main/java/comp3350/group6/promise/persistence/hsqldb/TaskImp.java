/*
 * Please ignore this class. We started implementing before realizing
 * we don't need a functioning database until iteration 2 so we switched
 * to using the fake databases in the "stub" folder
 */

package comp3350.group6.promise.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import comp3350.group6.promise.objects.Exceptions.PersistenceException;
import comp3350.group6.promise.objects.Task;
import comp3350.group6.promise.objects.enumClasses.TaskType;
import comp3350.group6.promise.persistence.TaskDao;
import comp3350.group6.promise.util.DBConnectorUtil;

public class TaskImp implements TaskDao {


    private Task fromResultSet(final ResultSet rs) throws SQLException {
        final int taskId = rs.getInt("taskId");
        final String title = rs.getString("title");
        final String description = rs.getString("description");
        final int priority = rs.getInt("priority");
        final int statusNum = rs.getInt("statusNum");
        final int projectId = rs.getInt("projectId");
        final Timestamp createdTime = rs.getTimestamp("createdTime");
        final Timestamp estimatedEndTime = rs.getTimestamp("estimatedEndTime");
        final Timestamp deadline = rs.getTimestamp("deadline");
        final TaskType type = TaskType.valueOf(rs.getString("type"));
        return new Task(taskId, title, description, priority, statusNum, projectId, createdTime, estimatedEndTime, deadline, type);
    }


    @Override
    public List<Task> getTaskList() {
        List<Task> taskList = new ArrayList<>();

        try (final Connection con = DBConnectorUtil.getConnection()) {
            final PreparedStatement pre = con.prepareStatement("SELECT * FROM task");
            final ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Task task = fromResultSet(rs);
                taskList.add(task);
            }
            rs.close();
            pre.close();
            return taskList;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Task> getTasksByProjectId(int projectId, int value) {
        List<Task> taskList = new ArrayList<>();
        try (final Connection con = DBConnectorUtil.getConnection()) {
            assert con != null;
            String statement = null;
            if (value == 1)
                statement = "SELECT * FROM task WHERE projectId = ? and type = 'IP'";

            else
                statement = "SELECT * FROM task WHERE projectId = ? and type = 'FINISHED'";

            assert (statement != null);
            PreparedStatement ps = con.prepareStatement(statement);
            ps.setInt(1, projectId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = fromResultSet(rs);
                taskList.add(task);
            }
            return taskList;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Task getTask(int taskId) {
        Task result;
        try (final Connection con = DBConnectorUtil.getConnection()) {
            final PreparedStatement pre = con.prepareStatement("SELECT * FROM task WHERE taskId = ?");
            pre.setString(1, String.valueOf(taskId));

            final ResultSet rs = pre.executeQuery();
            rs.next(); // move to first row
            result = fromResultSet(rs);
            rs.close();
            pre.close();

            return result;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int insertTask(Task t) {
        try (final Connection con = DBConnectorUtil.getConnection()) {
            String query = "INSERT INTO task(title, description, priority, statusNum, projectId, createdTime, estimatedEndTime, deadline, type) VALUES(?,?,?,?,?,?,?,?,?)";
            final PreparedStatement pre = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pre.setString(1, t.getTitle());
            pre.setString(2, t.getDescription());
            pre.setInt(3, t.getPriority());
            pre.setInt(4, t.getStatusNum());
            pre.setInt(5, t.getProjectId());
            pre.setTimestamp(6, t.getCreatedTime());
            pre.setTimestamp(7, t.getEstimatedEndTime());
            pre.setTimestamp(8, t.getDeadline());
            pre.setString(9, t.getType().toString());
            pre.executeUpdate();

            ResultSet generatedKeys = pre.getGeneratedKeys();
            generatedKeys.next();
            t.setTaskId(generatedKeys.getInt(1)); // set column taskId

            pre.close();
            return t.getTaskId();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }// TODO check insert since new changes

    @Override
    public Task updateTask(Task t) {
        try (final Connection con = DBConnectorUtil.getConnection()) {
            final PreparedStatement pre = con.prepareStatement("UPDATE task SET title = ?, description = ?, priority = ?, statusNum = ?, estimatedEndTime = ?, deadline = ?, type = ? WHERE taskId = ?");
            pre.setString(1, t.getTitle());
            pre.setString(2, t.getDescription());
            pre.setInt(3, t.getPriority());
            pre.setInt(4, t.getStatusNum());
            pre.setTimestamp(5, t.getEstimatedEndTime());
            pre.setTimestamp(6, t.getDeadline());
            Log.i("anchor", "NEW TYPE: "+ t.getType().name());
            pre.setString(7, t.getType().toString());
            pre.setInt(8, t.getTaskId());
            pre.executeUpdate();
            pre.close();
            return t;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Task deleteTask(Task t) {
        try (final Connection con = DBConnectorUtil.getConnection()) {
            final PreparedStatement pre1 = con.prepareStatement("DELETE FROM task WHERE taskId = ?");
            pre1.setInt(1, t.getTaskId());
            pre1.executeUpdate();
            pre1.close();
            return t;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
