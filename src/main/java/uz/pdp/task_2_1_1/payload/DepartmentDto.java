package uz.pdp.task_2_1_1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {

    @NotNull(message = "name must be written")
    private String name;

    private Integer companyId;
}
