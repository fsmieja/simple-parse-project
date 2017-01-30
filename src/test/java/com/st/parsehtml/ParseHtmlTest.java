package com.st.parsehtml;

import static org.junit.Assert.*;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParseHtmlTest {

	ParseHtml parse=null;
	
	@Before
	public void setUp() throws Exception {
		parse = new ParseHtml();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testProcessRepoFile() {
		String res = parse.processRepoFile("src/test/resources/testfile.html");
		assertNotNull(res);
		System.out.println (res);
		assertTrue(res.trim().equals("Repo repo: 70 days since last change"));
	}

	@Test
	public final void testProcessHtml1() {
		String html = "<div class='list2'><div class='age4'/><div class='age4'/></div>";
		String res = parse.processHtml(html);
		
		assertNull(res);
		//assertTrue(res.equals(""));
	}
	
	@Test
	public final void testProcessHtml2() {
		String html = "<div class='list'><div class='age4'/><div class='age4'/></div>";
		String res = parse.processHtml(html);
		
		assertNotNull(res);
		//assertTrue(!res.equals(""));
	}




}
