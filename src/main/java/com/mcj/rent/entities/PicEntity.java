
package com.mcj.rent.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author maxco
 */
@Entity
@Setter
@Getter
public class PicEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name ="uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String mime;
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] conten;
    
    
}
