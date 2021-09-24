package com.topolski.accountingapp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String firstName;
    private String lastName;
    private ContactDTO contact;
    private AddressDTO address;
    private Long companyId;
    private String creationdate;
}
