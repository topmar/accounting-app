package com.topolski.accountingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auth_users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_user_id")
    private Long authUserId;

    @Column(name = "user_name")
    private String username;

    private String password;

    private String role;

    @Column(name = "enabled")
    private Boolean isEnabled;

    @Column(name = "account_non_expired")
    private Boolean isAccountNonExpired;

    @Column(name = "account_non_locked")
    private Boolean isAccountNonLocked;

    @Column(name = "credentials_non_expired")
    private Boolean isCredentialsNonExpired;
}
