package uz.pdp.task_2_1_1.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {
    @NotNull(message = "corpName must be written")
    private String corpName;

    @NotNull(message = "directorName must be written")
    private String directorName;

    private Integer addressId;
}
