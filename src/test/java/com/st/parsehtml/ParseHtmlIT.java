package com.st.parsehtml;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * integration test
 */
public class ParseHtmlIT {

    ParseHtml parse;

    @Before
    public void setUp() throws Exception {
        parse = new ParseHtml();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCanOpenFile() throws Exception {
        final File f = parse.openFile("ggit.html");

        assertNotNull(f);
    }

}
