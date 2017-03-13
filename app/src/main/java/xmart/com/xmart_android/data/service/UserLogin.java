package xmart.com.xmart_android.data.service;

import com.google.gson.Gson;

/**
 * Created by LehongphuongCntt on 2/24/2017.
 */

public class UserLogin {
    private String Id;
    private String UserName;
    private String Token;
    private String FirstName;
    private String LastName;
    private String PhoneNumber;
    private String Birthday;
    private String Gender;
    private String Image;
    private String HomeAddr;
    private String HomeLatitude;
    private String HomeLongitude;
    private String WorkAddr;
    private String WorkLatitude;
    private String WorkLongitude;
    private String Point;

    public String objectToJson(UserLogin userLogin) {
        Gson gson = new Gson();
        String json = gson.toJson(userLogin);
        return json;
    }

    public UserLogin jsonToObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserLogin.class);
    }

    public UserLogin[] jsonToArrayObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserLogin[].class);
    }

    public UserLogin() {
    }

    public UserLogin(String id, String userName, String token, String firstName, String lastName, String phoneNumber, String birthday, String gender, String image, String homeAddr, String homeLatitude, String homeLongitude, String workAddr, String workLatitude, String workLongitude, String point) {
        Id = id;
        UserName = userName;
        Token = token;
        FirstName = firstName;
        LastName = lastName;
        PhoneNumber = phoneNumber;
        Birthday = birthday;
        Gender = gender;
        Image = image;
        HomeAddr = homeAddr;
        HomeLatitude = homeLatitude;
        HomeLongitude = homeLongitude;
        WorkAddr = workAddr;
        WorkLatitude = workLatitude;
        WorkLongitude = workLongitude;
        Point = point;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getHomeAddr() {
        return HomeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        HomeAddr = homeAddr;
    }

    public String getHomeLatitude() {
        return HomeLatitude;
    }

    public void setHomeLatitude(String homeLatitude) {
        HomeLatitude = homeLatitude;
    }

    public String getHomeLongitude() {
        return HomeLongitude;
    }

    public void setHomeLongitude(String homeLongitude) {
        HomeLongitude = homeLongitude;
    }

    public String getWorkAddr() {
        return WorkAddr;
    }

    public void setWorkAddr(String workAddr) {
        WorkAddr = workAddr;
    }

    public String getWorkLatitude() {
        return WorkLatitude;
    }

    public void setWorkLatitude(String workLatitude) {
        WorkLatitude = workLatitude;
    }

    public String getWorkLongitude() {
        return WorkLongitude;
    }

    public void setWorkLongitude(String workLongitude) {
        WorkLongitude = workLongitude;
    }

    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }
}
