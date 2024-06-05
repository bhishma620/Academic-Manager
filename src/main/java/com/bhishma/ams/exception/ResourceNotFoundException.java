package com.bhishma.ams.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resouceName;
    String fieldName;
    String fieldValue;

    public ResourceNotFoundException(String resouceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s: %s",resouceName,fieldName,fieldValue));
        this.resouceName = resouceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
