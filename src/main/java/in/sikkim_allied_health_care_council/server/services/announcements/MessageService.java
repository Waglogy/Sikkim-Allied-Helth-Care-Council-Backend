package in.sikkim_allied_health_care_council.server.services.announcements;

import in.sikkim_allied_health_care_council.server.dto.announcements.CreateMessage;
import in.sikkim_allied_health_care_council.server.dto.announcements.MessageResponse;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.models.announcements.Messages;
import in.sikkim_allied_health_care_council.server.repositories.MessageRepository;
import in.sikkim_allied_health_care_council.server.security.CurrentAdminProvider;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final CurrentAdminProvider currentAdminProvider;

    public MessageService(MessageRepository messageRepository, CurrentAdminProvider currentAdminProvider) {
        this.messageRepository = messageRepository;
        this.currentAdminProvider = currentAdminProvider;
    }

    public MessageResponse createMessage(@Valid CreateMessage createMessage) {

        AdminUser createdBy = currentAdminProvider.getCurrentAdmin();

        Messages message = Messages.builder()
                .title(createMessage.title())
                .description(createMessage.description())
                .publishDate(createMessage.publishDate())
                .fileUpload(createMessage.fileUpload())
                .publishStatus(createMessage.publishStatus())
                .createdBy(createdBy)
                .build();

        return MessageResponse.from(messageRepository.save(message));
    }
}
