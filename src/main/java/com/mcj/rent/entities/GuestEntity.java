
package com.mcj.rent.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author maxco
 */
@Entity(name = "guest")
@Getter @Setter
public class GuestEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;
    private String fullName;
    private String street;
    private Integer number;
    private String zipCode;
    private String city;
    private String country;
    private Boolean enable;
    @OneToOne(cascade = {CascadeType.ALL})
    private UserEntity user;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GuestEntity{Id=").append(Id);
        sb.append(", fullName=").append(fullName);
        sb.append(", street=").append(street);
        sb.append(", number=").append(number);
        sb.append(", zipCode=").append(zipCode);
        sb.append(", city=").append(city);
        sb.append(", country=").append(country);
        sb.append(", enable=").append(enable);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
    
    
}
