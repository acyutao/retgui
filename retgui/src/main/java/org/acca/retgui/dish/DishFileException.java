/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

/**
 * DishFileException.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-7-5
 */
public class DishFileException extends RuntimeException {

    private static final long serialVersionUID = 4430879239995129281L;

    /**
     * Construct.
     */
    public DishFileException() {
        super();
    }

    /**
     * Construct.
     * @param message String
     * @param cause Throwable
     */
    public DishFileException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct.
     * @param message String
     */
    public DishFileException(String message) {
        super(message);
    }

    /**
     * Construct.
     * @param cause Throwable
     */
    public DishFileException(Throwable cause) {
        super(cause);
    }

}
