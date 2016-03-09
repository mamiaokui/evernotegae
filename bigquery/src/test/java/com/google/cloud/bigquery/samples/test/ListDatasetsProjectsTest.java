/*
 * Copyright (c) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not  use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.bigquery.samples.test;

import static com.jcabi.matchers.RegexMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

import com.google.cloud.bigquery.samples.ListDatasetsProjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.Exception;

/** Unit tests for {@link ListDatasetsProjects}. */
public class ListDatasetsProjectsTest extends BigquerySampleTest {

  public ListDatasetsProjectsTest() throws FileNotFoundException {
    super();
  }

  private final ByteArrayOutputStream stdout = new ByteArrayOutputStream();
  private final ByteArrayOutputStream stderr = new ByteArrayOutputStream();
  private static final PrintStream REAL_OUT = System.out;
  private static final PrintStream REAL_ERR = System.err;

  @Before
  public void setUp() {
    System.setOut(new PrintStream(stdout));
    System.setErr(new PrintStream(stderr));
  }

  @After
  public void tearDown() {
    System.setOut(REAL_OUT);
    System.setErr(REAL_ERR);
  }

  @Test
  public void testUsage() throws Exception {
    ListDatasetsProjects.main(new String[] {});
    assertEquals("Usage: QuickStart <project-id>\n", stderr.toString());
  }

  @Test
  public void testMain() throws Exception {
    ListDatasetsProjects.main(new String[] { CONSTANTS.getProjectId() });
    String out = stdout.toString();
    assertThat(out, containsString("Running the asynchronous query"));
    assertThat(out, containsPattern("George W. Bush, [0-9]+"));
    assertThat(out, containsPattern("Wikipedia, [0-9]+"));

    assertThat(out, containsString("Listing all the Datasets"));
    assertThat(out, containsString("test_dataset"));

    assertThat(out, containsString("Listing all the Projects"));
    assertThat(out, containsString("Project list:"));
    assertThat(out, containsPattern("Bigquery Samples|cloud-samples-tests"));
  }
}
