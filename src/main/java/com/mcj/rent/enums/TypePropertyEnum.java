
package com.mcj.rent.enums;

/**
 *
 * @author maxco
 */
public enum TypePropertyEnum {
    HOUSE("HOUSE"),
    DEPARTMENT("DEPARTMENT");
    
    private final String displayValue;
    
    private TypePropertyEnum(String displayValue){
        this.displayValue = displayValue;
    }
    public String getDisplayValue(){
        return displayValue;
    }
    
}
