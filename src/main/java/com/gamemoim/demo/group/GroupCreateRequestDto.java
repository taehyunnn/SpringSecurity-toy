package com.gamemoim.demo.group;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GroupCreateRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private String description;
}
