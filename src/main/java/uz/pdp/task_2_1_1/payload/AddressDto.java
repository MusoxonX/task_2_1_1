package uz.pdp.task_2_1_1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @NotNull(message = "street not found")
    private String street;

    @NotNull(message = "homeNumber not found")
    private Integer homeNumber;
}
