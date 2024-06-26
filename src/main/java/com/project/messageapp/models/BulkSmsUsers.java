package com.project.messageapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BULK_SMS_USERS")
public class BulkSmsUsers implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
    @SequenceGenerator(name="user_seq_generator", sequenceName="SEQ_USER_ID", allocationSize = 1)
    @Column(name = "STAFF_ID", nullable = false)
    private Long staffId;

    @Column(name = "STAFF_NO", nullable = false, length = 25)
    private String staffNo;

    @Column(name = "FIRSTNAME", nullable = false, length = 25)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false, length = 25)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, length = 30)
    private String email;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private LocalDate createDate;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return staffNo;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}