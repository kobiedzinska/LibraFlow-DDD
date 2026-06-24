package Catalog.application.ports.in;

import Catalog.application.domain.model.CopyStatus;

public record CopyStatusDto(int copyId, CopyStatus status) {
}