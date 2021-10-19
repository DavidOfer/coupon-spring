package com.jb.couponProject.beans;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * a class for storing and handling a customer entity bean
 */

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 240)
    private String firstName;
    @Column(nullable = false, length = 240)
    private String lastName;
    @Column(nullable = false, length = 240, unique = true)
    private String username;
    private String password;
    private String role;
    @JsonIgnore
    private ArrayList<GrantedAuthority> authorities;
    @Singular
    @ManyToMany(cascade=CascadeType.ALL ,fetch = FetchType.LAZY)
    @JoinTable(name = "customers_vs_coupons",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="coupon_id", referencedColumnName = "id")
    )
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
