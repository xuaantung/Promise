package comp3350.group6.promise.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.group6.promise.objects.Access;
import comp3350.group6.promise.objects.Exceptions.DuplicateAccessException;
import comp3350.group6.promise.objects.Project;
import comp3350.group6.promise.objects.User;
import comp3350.group6.promise.objects.enumClasses.AccessRole;
import comp3350.group6.promise.persistence.AccessDao;
import comp3350.group6.promise.persistence.hsqldb.AccessImp;

/*
    Access = User has "Access" to a Project relationship
 */
public class AccessService {

    AccessDao accessDao;
    private static AccessService instance;

    public AccessService(){
        accessDao = new AccessImp();
    }

    public AccessService(AccessDao accessDao){
        this();
        this.accessDao = accessDao;
    }

    // returns a list of access a project has
    public List<Access> getProjectAccess(int projectId){
        List<Access> accessList = accessDao.getAccessByProject(projectId);
        return Collections.unmodifiableList(accessList);
    }

    public List<User> getUsers(int projectId) {
        List<Access> accessList = getProjectAccess(projectId);
        List<User> userList = new ArrayList<>();
        UserService userService = new UserService();

        for (Access access: accessList) {
            userList.add(userService.getUserByUserId(access.getUserId()));
        }

        return Collections.unmodifiableList(userList);
    }

    // returns a list of access a user has
    public List<Access> getUserAccess(int userId){
        List<Access> accessList = accessDao.getAccessByUser(userId);
        return Collections.unmodifiableList(accessList);
    }

    public List<Project> getProjects(int userId) {
        List<Access> accessList = getUserAccess(userId);
        List<Project> projectList = new ArrayList<>();

        for (Access access: accessList) {
            projectList.add(ProjectService.getInstance().getProjectByID(access.getProjectId()));
        }

        return Collections.unmodifiableList(projectList);
    }

    public Access getAccessByIDs(int userId, int projectId){
        return accessDao.getAccessByIDs(userId, projectId);
    }

    // insert a new access to the DB
    public Access insertAccess(Access access) throws DuplicateAccessException {
        if (hasAccess(access.getUserId(), access.getProjectId())){
            // user already had access to this project
            throw new DuplicateAccessException("User already had access to this project");
        }
        return accessDao.insertAccess(access);
    }

    // update the role of the access
    public Access updateAccess(Access access){ return accessDao.updateAccess(access); }

    public List<Integer> getRoleByProjectID( int projectID, AccessRole role ){

        return accessDao.getRoleByProjectID( projectID, role );

    }

    public boolean hasAccess( int userID, int projectID ){

        return accessDao.hasAccess( userID, projectID );

    }

    public static AccessService getInstance() {
        if(AccessService.instance == null) {
            AccessService.instance = new AccessService();
        }
        return AccessService.instance;
    }
}
