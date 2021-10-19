package com.jb.couponProject.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;



/**
 * a class for storing and handling a coupon entity bean
 */

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name="coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="company_id",updatable = false)
    private int companyId;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(length = 50, nullable = false)
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;
    @ManyToMany(mappedBy = "coupons", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<Customer> customers;
}
