
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

@Setter @Getter
@Entity(name = "comment")
public class CommentEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String description;
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private HouseEntity house;
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private GuestEntity guestEntity;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CommentEntity{id=").append(id);
        sb.append(", description=").append(description);
        sb.append(", house=").append(house);
        sb.append(", guestEntity=").append(guestEntity);
        sb.append('}');
        return sb.toString();
    }




    
}
