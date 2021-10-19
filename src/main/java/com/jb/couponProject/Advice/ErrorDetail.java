package com.jb.couponProject.Advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * a model for exceptions
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private String error;
    private String description;
}