package lk.ijse.palmoilfactory.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class Stock {
    String stockId;
    int ffbInput;
    String date;
    String time;
    String supId;
}
