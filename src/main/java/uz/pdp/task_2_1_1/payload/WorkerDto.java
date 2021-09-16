package uz.pdp.task_2_1_1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class WorkerDto {
    @NotNull(message = "name must be written")
    private String name;

    @NotNull(message = "phoneNumber must be written")
    private String phoneNumber;

    private Integer addressId;

    private Integer departmentId;
}
