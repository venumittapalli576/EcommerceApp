package ecommerceapp.ProductData;

import java.io.Serializable;

public class UserData implements Serializable
{
    static String name;
    static String email;
    static String phone;
    static String imageUri;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        UserData.imageUri = imageUri;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        UserData.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        UserData.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        UserData.phone = phone;
    }
}
