package com.st.parsehtml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParseHtmlTest {

	ParseHtml parse;

	@Before
	public void setUp() throws Exception {
		parse = new ParseHtml();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testProcessRepoFile() {
		final String res = parse
				.processRepoFile("src/test/resources/testfile.html");
		assertNotNull(res);
		System.out.println(res);
		assertTrue(res.trim().equals("Repo repo: 70 days since last change"));
	}

	@Test
	public final void testProcessHtml13() {
		final String html = "<div class='list2'><div class='age4'/><div class='age4'/></div>";
		final String res = parse.processHtml(html);

		assertNull(res);
		// assertTrue(res.equals(""));
	}

	@Test
	public final void testZeroResult() {

		final long result = parse.performCalc(5, 6);
		assertEquals(result, 0);
	}

	@Test
	public final void testProcessHtml2() {
		final String html = "<div class='list'><div class='age4'/><div class='age4'/></div>";
		final String res = parse.processHtml(html);

		assertNotNull(res);
		// assertTrue(!res.equals(""));
		//
	}

}
