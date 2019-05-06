package Models.User;

import Authetication.UserDTO;
import Authetication.UserPrivilege;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "User")
public class User implements Serializable {
    @Id
    @Column(unique=true, nullable=false, length=128)
    private String email;

    @Column(nullable=false, length=128)
    private String firstName;

    @Column(nullable=false, length=128)
    private String lastName;

    @Column(nullable=false, length=128) // sha-512 + hex
    private String password;

    @Column(length=128) // sha-512 + hex
    private String randomPassword;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date registeredOn;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date received;

    @ElementCollection(targetClass = UserPrivilege.class)
    @CollectionTable(name = "User_UserPrivilege",
            joinColumns       = @JoinColumn(name = "email", nullable=false),
            uniqueConstraints = { @UniqueConstraint(columnNames={"email","privilegename"}) } )
    @Enumerated(EnumType.STRING)
    @Column(name="privilegename", length=64, nullable=false)
    private List<UserPrivilege> roles;

    public User(){

    }

    public User(UserDTO user){

        if (user.getPassword1() == null || user.getPassword1().length() == 0
                || !user.getPassword1().equals(user.getPassword2()) )
            throw new RuntimeException("Password 1 and Password 2 have to be equal (typo?)");

        this.email        = user.getEmail();
        this.firstName    = user.getFirstName();
        this.lastName     = user.getLastName();
        this.password     = DigestUtils.sha512Hex(user.getPassword1() );
        this.randomPassword = DigestUtils.sha512Hex(this.getRandomPassword());
        this.registeredOn = new Date();
        this.received = this.getReceived();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password in SHA512 HEX representation
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public List<UserPrivilege> getPrivileges() {
        return roles;
    }

    public void setPrivileges(List<UserPrivilege> roles) {
        this.roles = roles;
    }

    public boolean hasPrivilege(UserPrivilege role) {
        return (roles.contains(role));
    }

    public String getRandomPassword() {
        return randomPassword;
    }

    public void setRandomPassword(String randomPassword) {
        this.randomPassword = randomPassword;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }

    public JSONObject toJsonCustom(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("email", this.email);
        jsonObject.put("firstName", this.firstName);
        jsonObject.put("lastName", this.lastName);
        jsonObject.put("registeredOn", this.registeredOn);
        jsonObject.put("roles", this.roles);

        return  jsonObject;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", firstName=" + firstName
                + ", lastName=" + lastName + ", password=" + password
                + ", registeredOn=" + registeredOn + ", roles=" + roles + "]";
    }
}
