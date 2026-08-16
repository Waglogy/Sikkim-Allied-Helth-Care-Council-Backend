package in.sikkim_allied_health_care_council.server.models.announcements;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "publications")
public class Publications extends AnnouncementBase {

    @Column(nullable = false)
    private String title;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @Column(name = "file_upload")
    private String fileUpload;

    @Column(nullable = false)
    private boolean publishStatus;
}
