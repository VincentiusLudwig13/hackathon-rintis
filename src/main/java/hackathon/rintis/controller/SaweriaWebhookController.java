package hackathon.rintis.controller;

import hackathon.rintis.model.DTO.SaweriaWebhookPayload;
import hackathon.rintis.model.entity.SaweriaIntegration;
import hackathon.rintis.repository.SaweriaIntegrationRepo;
import hackathon.rintis.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/webhook/saweria")
@RequiredArgsConstructor
public class SaweriaWebhookController {

    private final SaweriaIntegrationRepo integrationRepo;
    private final TransactionService transactionService;

    @PostMapping("/{token}")
    public ResponseEntity<String> handle(
            @PathVariable String token,
            @RequestBody SaweriaWebhookPayload payload,
            @RequestHeader(value = "Saweria-Callback-Signature", required = false)
            String signatureHeader) {

        SaweriaIntegration integration = integrationRepo.findByWebhookToken(token)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unknown webhook token"));

        transactionService.inserTransaksi(payload, integration.getUserId());

        return ResponseEntity.ok("OK");
    }
}
