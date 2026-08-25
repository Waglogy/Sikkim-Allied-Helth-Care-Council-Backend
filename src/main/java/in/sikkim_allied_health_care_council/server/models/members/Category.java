package in.sikkim_allied_health_care_council.server.models.members;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * A grouping of council members — either the council executive or one of the
 * recognised professional categories. Categories are ordered by {@code priority}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member_categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    @UuidGenerator
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    /** Ascending display order among categories of the same type. */
    @Column(name = "priority")
    private int priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;
}
