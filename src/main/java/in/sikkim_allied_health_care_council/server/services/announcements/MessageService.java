package in.sikkim_allied_health_care_council.server.services.announcements;

import in.sikkim_allied_health_care_council.server.dto.announcements.CreateMessage;
import in.sikkim_allied_health_care_council.server.dto.announcements.MessageResponse;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.models.announcements.Messages;
import in.sikkim_allied_health_care_council.server.repositories.AdminUserRepository;
import in.sikkim_allied_health_care_council.server.repositories.MessageRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AdminUserRepository adminUserRepository;

    public MessageService(MessageRepository messageRepository, AdminUserRepository adminUserRepository) {
        this.messageRepository = messageRepository;
        this.adminUserRepository = adminUserRepository;
    }

    public MessageResponse createMessage(@Valid CreateMessage createMessage) {

        AdminUser createdBy = adminUserRepository.findByUuid(createMessage.createdBy())
                .orElseThrow(() -> new ResourceNotFoundException("Admin user not found."));

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
