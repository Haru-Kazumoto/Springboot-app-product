package dev.pack.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "tbl_supplier",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"email"}
        )
)
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
public class Supplier implements Serializable {

    public static Long serialiseVersionId = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 150,nullable = false)
    private String name;

    @Column(length = 150,nullable = false)
    private String address;

    @Column(unique = true,nullable =  false,length = 30)
    private String email;

    @ManyToMany(mappedBy = "suppliers")
    @ToString.Exclude
    @JsonBackReference
    private Set<Product> products;
    
}
