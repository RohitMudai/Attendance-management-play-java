package models;

import io.ebean.Model;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.Entity;
import javax.persistence.Id;

import static play.data.validation.Constraints.*;

@Entity
@Validate
public class Products_Info extends Model implements Constraints.Validatable<ValidationError> {
    @Id
   @Required
    private String name;
    private int emp_id;

    public int getEmp_id() {
            return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

 public String authenticate(String name) {
        if (name!=null) {
            return "valid name";
        }
            return null;

    }

    @Override
    public ValidationError validate() {
        if (authenticate(name) == null) {
            return new ValidationError("name","invalid");
        }
        return null;
    }
}
