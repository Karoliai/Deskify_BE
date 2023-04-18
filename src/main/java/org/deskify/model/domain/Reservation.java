package org.deskify.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Reservation {

    private Long reservationId;
    private Long userId;
    private Long floorId;
    private Long deskId;
    private Timestamp reservationStart;
    private Timestamp reservationEnd;
}
