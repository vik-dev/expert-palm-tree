package webapp.restfull.demo.models.statuses;


import webapp.restfull.demo.accessingdatajpa.models.User;
import webapp.restfull.demo.models.UserError;

import java.util.ArrayList;
import java.util.List;

public class UserUpdateStatus {

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean status;
    private List<UserError> errors = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public boolean getStatus() {
        return status;
    }

    public List<UserError> getErrors() {
        return errors;
    }

    public UserUpdateStatus(boolean status, List<UserError> errors) {
        this.status = status;
        this.errors = errors;
    }

    public UserUpdateStatus() {
    }

    public UserUpdateStatus(List<UserError> errors) {
        this.errors = errors;
    }

    public UserUpdateStatus(boolean status) {
        this.status = true;
    }
    public boolean addError(UserError error) {
        return errors.add(error);
    }
}
