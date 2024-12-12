package com.hcc.entities;

import javax.persistence.*;

import com.hcc.enums.AuthorityEnum;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthorityEnum authority;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Authority() {}

    public Authority(AuthorityEnum authority, User user) {
        this.authority = authority;
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityEnum getAuthorityEnum() {
        return authority;
    }

    public void setAuthorityEnum(AuthorityEnum authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
