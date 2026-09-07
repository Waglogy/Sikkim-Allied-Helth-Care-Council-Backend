package in.sikkim_allied_health_care_council.server.services.announcements;

import in.sikkim_allied_health_care_council.server.dto.announcements.CreateMessage;
import in.sikkim_allied_health_care_council.server.dto.announcements.MessageResponse;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.models.announcements.Messages;
import in.sikkim_allied_health_care_council.server.repositories.MessageRepository;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.security.CurrentAdminProvider;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
                .photo(createMessage.photo())
                .fileUpload(createMessage.fileUpload())
                .publishStatus(createMessage.publishStatus())
                .orderPriority(createMessage.priority())
                .createdBy(createdBy)
                .build();

        return MessageResponse.from(messageRepository.save(message));
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> getAllMessages() {
        return messageRepository.findAllByOrderByOrderPriorityAscCreatedAtDesc()
                .stream()
                .map(MessageResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MessageResponse getMessageByUuid(UUID uuid) {
        Messages message = messageRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found."));
        return MessageResponse.from(message);
    }

    @Transactional
    public MessageResponse updateMessage(UUID uuid, @Valid CreateMessage updateMessage) {
        Messages message = messageRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found."));

        message.setTitle(updateMessage.title());
        message.setDescription(updateMessage.description());
        message.setPublishDate(updateMessage.publishDate());
        message.setPhoto(updateMessage.photo());
        message.setFileUpload(updateMessage.fileUpload());
        message.setPublishStatus(updateMessage.publishStatus());
        message.setOrderPriority(updateMessage.priority());

        return MessageResponse.from(messageRepository.save(message));
    }

    @Transactional
    public void deleteMessage(UUID uuid) {
        Messages message = messageRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found."));
        messageRepository.delete(message);
    }
}
