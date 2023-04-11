package lk.ijse.palmoilfactory.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class Stock {
    private String stockId;
    private int ffbInput;
    private String date;
    private String time;
    private String supId;
}
