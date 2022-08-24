package model;
/** This class defines users. */
public class Users {

    private int userId;
    private String  userName;
    private String password;

    /** A constructor for users.
     * @param userId The user's ID
     * @param userName The user's username
     * @param password The user's password */
    public Users(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /** Sets the ID of a user.
     * @param userId ID for the user. */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /** Gets the ID of a user.
     * @return ID for the user. */
    public int getUserId(){
        return userId;
    }
    /** Sets the username of a user.
     * @param userName  username for the user. */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /** Gets the username of a user.
     * @return username for the user. */
    public String getUserName(){
        return userName;
    }
    /** Sets the password of a user.
     * @param password password for the user. */
    public void setPassword(String password) {
        this.password = password;
    }
    /** Gets the password of a user.
     * @return password for the user. */
    public String getPassword(){
        return password;
    }

    /** Returns a string for displaying in combo boxes.
     * @return user ID and name to a string */
    @Override
    public String toString(){
        return getUserId() + " " + getUserName();
    }

}
