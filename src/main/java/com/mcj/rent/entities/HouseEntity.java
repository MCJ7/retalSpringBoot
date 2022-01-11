
package com.mcj.rent.entities;

import com.mcj.rent.enums.TypePropertyEnum;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Entity(name = "house")
@Getter @Setter
public class HouseEntity implements Serializable {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String street;
    private int number;
    private String zipCode;
    private String city;
    private String country;
    @Basic
    private LocalDate sinceDate;
    @Basic
    private LocalDate toDate;
    private int timeMin;
    private int timeMax;
    private double price;
    private int numberOfBaths;
    private int numberOfRooms;
    @Enumerated(EnumType.STRING)
    private TypePropertyEnum typeHouse;
    private Boolean enable;
    @OneToOne
    private PicEntity pic;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HouseEntity{id=").append(id);
        sb.append(", street=").append(street);
        sb.append(", number=").append(number);
        sb.append(", zipCode=").append(zipCode);
        sb.append(", city=").append(city);
        sb.append(", country=").append(country);
        sb.append(", sinceDate=").append(sinceDate);
        sb.append(", toDate=").append(toDate);
        sb.append(", timeMin=").append(timeMin);
        sb.append(", timeMax=").append(timeMax);
        sb.append(", roomPrice=").append(price);
        sb.append(", typeHouse=").append(typeHouse);
        sb.append(", enable=").append(enable);
        sb.append('}');
        return sb.toString();
    }
     
}
