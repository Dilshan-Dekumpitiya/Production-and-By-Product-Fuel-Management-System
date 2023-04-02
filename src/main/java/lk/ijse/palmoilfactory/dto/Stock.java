package lk.ijse.palmoilfactory.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Stock {
    String stockId;
    int ffbInput;
    String date;
    String time;
    String supId;
}
