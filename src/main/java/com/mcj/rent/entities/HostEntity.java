
package com.mcj.rent.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author maxco
 */
@Entity(name = "host")
@Setter @Getter
public class HostEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String fullName;
    private Boolean enable;
    private String personalIdentityCode;
    private Double accountBalance;
    private String city;
    private String country;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(referencedColumnName = "id")
    private UserEntity user;
    @OneToOne(cascade = {CascadeType.ALL})
    private HouseEntity house;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HostEntity{id=").append(id);
        sb.append(", enable=").append(enable);
        sb.append(", user=").append(user);
        sb.append(", house=").append(house);
        sb.append('}');
        return sb.toString();
    }
    
    
}
