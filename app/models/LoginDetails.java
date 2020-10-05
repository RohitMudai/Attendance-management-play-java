package models;


import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;
import play.data.validation.Constraints.*;
import play.data.validation.ValidationError;
import util.encryption.Encryption;
import util.encryption.EncryptionFactory;

import javax.persistence.*;

@Entity
@Table (name="login_details")
@Validate
public class LoginDetails extends Model implements Constraints.Validatable<ValidationError> {
    @Id @GeneratedValue

    @Column (name="first_name")
    private String fname;

    @Column (name="last_name")
    private String lname;

    @Column (name="Phone_no")
    private String phone;
    @Required
    @Column (name="email_id")
    private String email;

    @Column (name="password")
    private String password;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role.toLowerCase();
    }

    @Column (name="role")
    private String role;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname.toLowerCase();
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname.toLowerCase();;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Encryption encryption = EncryptionFactory.getInstance();
        String enryptedPassword = encryption.encrypt(password);
        this.password = enryptedPassword;
    }

    public static Finder<Integer, LoginDetails> find = new Finder(LoginDetails.class,"test");

    public String authenticate(String email) {
        if (email!=null) {
            return "valid name";
        }
        return null;

    }

    @Override
    public ValidationError validate() {
        if (authenticate(email) == null) {
            return new ValidationError("name","invalid");
        }
        return null;
    }


}
