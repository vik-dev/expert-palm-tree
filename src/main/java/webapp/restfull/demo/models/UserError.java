package webapp.restfull.demo.models;


import webapp.restfull.demo.accessingdatajpa.models.User;

public class UserError {
    public enum Error {

        UPPER_CASE_NOT_EXIST("Некорректная заглавная буква в пароле"),
        DIGIT_NOT_EXIST("Отсутствует цифра в пароле"),
        LOGIN_NOT_EXIST("Не введен логин"),
        PASSWORD_NOT_EXIST("Не введен пароль"),
        NAME_NOT_EXIST("Не введено имя"),
        USER_NOT_FOUND("Пользователь не найден"),
        UNKNOWN_ERROR("Неизвестная ошибка"),
        UNKNOWN_ROLE("Неизвестная роль"),
        TRY_TO_ADD_NEW_USER("Попытка добавить новго пользователя");

        private final String value;

        Error(String error) {
            this.value = error;
        }

        public String getValue() {
            return value;
        }
    }


    private final String description;
    private User user;
    private Error error;

    public void setUser(User user) {
        this.user = user;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public UserError(Error error) {
        this.description = error.getValue();
        this.error = error;
    }

    public UserError(Error error, User user) {
        this.description = error.getValue();
        this.user = user;
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Ошибка{" +
                "описание='" + description + '\'' +
                ", пользователь=" + user +
                '}';
    }
}
