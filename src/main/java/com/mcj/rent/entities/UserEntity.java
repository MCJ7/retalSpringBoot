
package com.mcj.rent.entities;


import com.mcj.rent.enums.RoleEnum;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author maxco
 */
@Entity(name = "user")
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
      @Transient
  public static final Sort SORT_BY_CREATED_AT_DESC = 
                        Sort.by(Sort.Direction.DESC, "signDate");

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false,unique = true)
    private String userName;
    private String email;
    private String pass;

    @Basic
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime signDate;
    @OneToOne
    private PicEntity avatar;
    @Basic
    private LocalDateTime dismissDate;
    private Boolean enable;
    @Enumerated(EnumType.STRING)
    @Column(unique = false)
    private RoleEnum role;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserEntity{id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", email=").append(email);
        sb.append(", pass=").append(pass);
        sb.append(", signDate=").append(signDate);
        sb.append(", dismissDate=").append(dismissDate);
        sb.append(", enable=").append(enable);
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
    
}
