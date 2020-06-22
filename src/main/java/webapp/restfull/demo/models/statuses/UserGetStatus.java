package webapp.restfull.demo.models.statuses;

import webapp.restfull.demo.accessingdatajpa.models.User;
import webapp.restfull.demo.models.UserError;

import java.util.List;

public class UserGetStatus {

    private final boolean status;
    private List<UserError> userErrors;
    private User user;

    public List<UserError> getUserErrors() {
        return userErrors;
    }

    public UserGetStatus(boolean status, List<UserError> userErrors) {
        this.status = status;
        this.userErrors = userErrors;
    }

    public UserGetStatus(boolean status, User user) {
        this.status = status;
        this.user = user;
    }

    public boolean getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }
}
