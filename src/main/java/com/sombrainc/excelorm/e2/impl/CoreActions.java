package com.sombrainc.excelorm.e2.impl;

import lombok.Getter;

@Getter
public abstract class CoreActions<T> {
    protected EReaderContext eReaderContext;

    public CoreActions(EReaderContext eReaderContext) {
        this.eReaderContext = eReaderContext;
    }

    /**
     * The trigger the whole chain of user actions to read from the doc
     *
     * @return required user object
     */
    public T go() {
        return invokeExecutor().go();
    }

    /**
     * To return the executor object for the required structure
     *
     * @return appropriate executor object
     */
    protected abstract CoreExecutor<T> invokeExecutor();

}
