package xmart.com.xmart_android.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

import com.google.gson.Gson;

/**
 * Entity mapped to table "OWNER".
 */
public class Owner {

    private String Id;
    private String UserName;
    private String Token ;
    private String ShopName;
    private String FirstName ;
    private String LastName;
    private String PhoneNumber ;
    private String Birthday;
    private String Gender;
    private String Image;
    private String ImageThumb ;
    private String Address;
    private String AddrLatitude;
    private String AddrLongitude;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Owner() {
    }

    public Owner(String Id) {
        this.Id = Id;
    }

    public Owner(String Id, String UserName, String Token , String ShopName, String FirstName , String LastName, String PhoneNumber , String Birthday, String Gender, String Image, String ImageThumb , String Address, String AddrLatitude, String AddrLongitude) {
        this.Id = Id;
        this.UserName = UserName;
        this.Token  = Token ;
        this.ShopName = ShopName;
        this.FirstName  = FirstName ;
        this.LastName = LastName;
        this.PhoneNumber  = PhoneNumber ;
        this.Birthday = Birthday;
        this.Gender = Gender;
        this.Image = Image;
        this.ImageThumb  = ImageThumb ;
        this.Address = Address;
        this.AddrLatitude = AddrLatitude;
        this.AddrLongitude = AddrLongitude;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getToken () {
        return Token ;
    }

    public void setToken (String Token ) {
        this.Token  = Token ;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public String getFirstName () {
        return FirstName ;
    }

    public void setFirstName (String FirstName ) {
        this.FirstName  = FirstName ;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getPhoneNumber () {
        return PhoneNumber ;
    }

    public void setPhoneNumber (String PhoneNumber ) {
        this.PhoneNumber  = PhoneNumber ;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getImageThumb () {
        return ImageThumb ;
    }

    public void setImageThumb (String ImageThumb ) {
        this.ImageThumb  = ImageThumb ;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getAddrLatitude() {
        return AddrLatitude;
    }

    public void setAddrLatitude(String AddrLatitude) {
        this.AddrLatitude = AddrLatitude;
    }

    public String getAddrLongitude() {
        return AddrLongitude;
    }

    public void setAddrLongitude(String AddrLongitude) {
        this.AddrLongitude = AddrLongitude;
    }

    // KEEP METHODS - put your custom methods here
    public String objectToJson(Owner doiTuong) {
        Gson gson = new Gson();
        String json = gson.toJson(doiTuong);
        return json;
    }

    public Owner jsonToObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,Owner.class);
    }

    public  Owner[] jsonToArrayObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,  Owner[].class);
    }
    // KEEP METHODS END

}
