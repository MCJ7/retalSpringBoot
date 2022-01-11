
package com.mcj.rent.enums;

/**
 *
 * @author maxco
 */
public enum RoleEnum {
    
    ADMIN("ADMIN"),
    GUEST("GUEST"),
    HOST("HOST");
    
    private final String displayValue;
    
    private RoleEnum(String displayValue){
        this.displayValue = displayValue;
    }
    public String getDisplayValue(){
        return displayValue;
    }
    
}
