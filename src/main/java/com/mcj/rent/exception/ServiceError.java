/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.rent.exception;

/**
 *
 * @author maxco
 */
public class ServiceError extends Exception{

    /**
     * Creates a new instance of <code>ServiceError</code> without detail
     * message.
     */
    public ServiceError() {
    }

    /**
     * Constructs an instance of <code>ServiceError</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ServiceError(String msg) {
        super(msg);
    }
}
