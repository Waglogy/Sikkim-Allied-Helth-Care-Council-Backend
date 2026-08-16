package in.sikkim_allied_health_care_council.server.controllers;


import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.announcements.CreateMessage;
import in.sikkim_allied_health_care_council.server.dto.announcements.MessageResponse;
import in.sikkim_allied_health_care_council.server.services.announcements.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
