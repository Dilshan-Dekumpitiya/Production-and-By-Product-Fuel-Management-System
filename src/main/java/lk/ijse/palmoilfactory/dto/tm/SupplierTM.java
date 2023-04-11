package lk.ijse.palmoilfactory.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierTM {
    private String supId;
    private String supName;
    private String supAddress;
    private String supContact;
    private Button btn;
}
