/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.xerces.util.XMLChar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class XMLFixingReader.
 */
public class XMLFixingReader extends Reader {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLFixingReader.class);

    private final Reader target;


    /**
     * Instantiates a new XML fixing reader.
     *
     * @param target the target
     */
    public XMLFixingReader(Reader target) {
        this.target = target;
    }


    /* (non-Javadoc)
     * @see java.io.Reader#read(char[], int, int)
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int ret = target.read(cbuf, off, len);
        if (ret > 0) {
            int n = off + ret;
            for (int i = off; i < n; ++i) {
                if (!XMLChar.isValid(cbuf[i])) {
                    LOGGER.warn("XML input contains invalid character 0x" + Integer.toString(cbuf[i], 16));
                    cbuf[i] = '?';
                }
            }
        }
        return ret;
    }


    /* (non-Javadoc)
     * @see java.io.Reader#close()
     */
    @Override
    public void close() throws IOException {
        target.close();
    }
}
