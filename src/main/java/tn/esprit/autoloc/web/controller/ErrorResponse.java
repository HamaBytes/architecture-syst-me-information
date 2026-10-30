package tn.esprit.autoloc.web.controller;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        LocalDateTime horodatage
) {}