package in.sikkim_allied_health_care_council.server.models.announcements;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Events extends AnnouncementBase {

    @Column(nullable = false)
    private String name;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @ElementCollection
    @CollectionTable(name = "event_images", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "image_url")
    private List<String> images;

    @ElementCollection
    @CollectionTable(name = "event_documents", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "document_url")
    private List<String> documents;
}
