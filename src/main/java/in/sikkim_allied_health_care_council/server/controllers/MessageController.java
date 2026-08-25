package in.sikkim_allied_health_care_council.server.controllers;


import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.announcements.CreateMessage;
import in.sikkim_allied_health_care_council.server.dto.announcements.MessageResponse;
import in.sikkim_allied_health_care_council.server.services.announcements.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<MessageResponse>> createMessage(
            @Valid @RequestBody CreateMessage createMessageRequest
    ) {

        MessageResponse created = messageService.createMessage(createMessageRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Message created successfully", created));

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getAllMessages() {

        List<MessageResponse> messages = messageService.getAllMessages();

        return ResponseEntity
                .ok(ApiResponse.success("Messages fetched successfully", messages));

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<MessageResponse>> getMessage(
            @PathVariable UUID uuid
    ) {

        MessageResponse message = messageService.getMessageByUuid(uuid);

        return ResponseEntity
                .ok(ApiResponse.success("Message fetched successfully", message));

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<MessageResponse>> updateMessage(
            @PathVariable UUID uuid,
            @Valid @RequestBody CreateMessage updateMessageRequest
    ) {

        MessageResponse updated = messageService.updateMessage(uuid, updateMessageRequest);

        return ResponseEntity
                .ok(ApiResponse.success("Message updated successfully", updated));

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> deleteMessage(
            @PathVariable UUID uuid
    ) {

        messageService.deleteMessage(uuid);

        return ResponseEntity
                .ok(ApiResponse.success("Message deleted successfully"));

    }

}
