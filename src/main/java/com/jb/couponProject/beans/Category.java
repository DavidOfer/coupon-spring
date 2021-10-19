package com.jb.couponProject.beans;


/**
 * Enum class for the category types of the coupons.
 */
public enum Category {
//    FOOD("CODE_1"), ELECTRICITY("CODE_2"), RESTAURANT("CODE_3"), VACATION("CODE_4");
    FOOD, ELECTRICITY, RESTAURANT, VACATION;

//    private String code;

//    private Category(String code) {
//        this.code=code;
//    }
//
//    @JsonCreator
//    public static Category decode(final String code) {
//        return Stream.of(Category.values()).filter(targetEnum -> targetEnum.code.equals(code)).findFirst().orElse(null);
//    }
//
//    @JsonValue
//    public String getCode() {
//        return code;
//    }
}