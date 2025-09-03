package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.security.PrivateKey;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private LocalDateTime createdAt;
    private String email;
    private String name;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //@JoinColumn(name = "course_id")
    private Course course;

}
