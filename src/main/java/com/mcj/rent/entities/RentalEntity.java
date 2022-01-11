
package com.mcj.rent.entities;

import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author maxco
 */
@Getter
@Setter
@Entity(name = "rental")
public class RentalEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String bookingName;
    @Basic
    private LocalDate checkIn;
    @Basic
    private LocalDate checkOut;
    private Boolean enable;
    @ManyToOne(cascade = {
    CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    private HouseEntity house;
        @ManyToOne(cascade = {
    CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    private GuestEntity guest;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RentalEntity{id=").append(id);
        sb.append(", bookingName=").append(bookingName);
        sb.append(", sinceDate=").append(checkIn);
        sb.append(", untilDate=").append(checkOut);
        sb.append(", enable=").append(enable);
        sb.append(", house=").append(house);
        sb.append(", guestAcount=").append(guest);
        sb.append('}');
        return sb.toString();
    }
    
}
