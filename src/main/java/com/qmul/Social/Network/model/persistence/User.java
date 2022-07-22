package com.qmul.Social.Network.model.persistence;


import com.qmul.Social.Network.model.persistence.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToMany(mappedBy = "users")
    private Set<Module> modules;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean accountNonLocked = true;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean enabled;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDummyUser;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Date createdTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = this.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
    }
}