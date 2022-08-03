package com.qmul.Social.Network.exception;

public class InstitutionNotFoundException extends RuntimeException{

    public InstitutionNotFoundException()
    {
        super();
    }

    public InstitutionNotFoundException(String message)
    {
        super(message);
    }
}
