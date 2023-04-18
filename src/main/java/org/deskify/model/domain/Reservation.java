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
    private Long user_id;
    private Long floor_id;
    private Long table_id;
    private Timestamp reservationStart;
    private Timestamp reservationEnd;
}
