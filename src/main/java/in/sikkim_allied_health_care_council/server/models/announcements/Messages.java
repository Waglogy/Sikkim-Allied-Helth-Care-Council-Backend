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
@Table(name = "messages")
public class Messages extends AnnouncementBase {

    @Column(nullable = false)
    private String title;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    private String photo;

    @Column(name = "file_upload")
    private String fileUpload;

    @Column(nullable = false)
    private boolean publishStatus;

    /** Display order — lower value is shown first. */
    @Column(name = "order_priority", nullable = false, columnDefinition = "int default 0")
    private int orderPriority;
}
