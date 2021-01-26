package com.alphasense.document.load.dto;


import java.util.ArrayList;
import java.util.List;

public class Errors {

    List<Error> errorList = new ArrayList<>();

    public List<Error> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<Error> errorList) {
        this.errorList = errorList;
    }
}
