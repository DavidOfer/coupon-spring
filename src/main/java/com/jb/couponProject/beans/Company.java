package com.jb.couponProject.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * a class for storing and handling a company entity bean
 */
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name="company")
public class Company implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 240, unique = true)
    private String name;
    @Column(nullable = false, length = 240, unique = true)
    private String username;
    private String password;
    private String role;
    @JsonIgnore
    private ArrayList<GrantedAuthority> authorities;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "company_id",referencedColumnName = "id")
    @Singular
    private List<Coupon> coupons;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
