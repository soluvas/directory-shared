/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.directory.shared.ldap.model.schema.syntaxes;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mycila.junit.concurrent.Concurrency;
import com.mycila.junit.concurrent.ConcurrentJunitRunner;
import org.apache.directory.shared.ldap.model.schema.syntaxCheckers.DsaQualitySyntaxSyntaxChecker;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test cases for DSAQualitySyntaxSyntaxChecker.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@RunWith(ConcurrentJunitRunner.class)
@Concurrency()
public class DsaQualitySyntaxSyntaxCheckerTest
{
    DsaQualitySyntaxSyntaxChecker checker = new DsaQualitySyntaxSyntaxChecker();


    @Test
    public void testNullString()
    {
        assertFalse( checker.isValidSyntax( null ) );
    }


    @Test
    public void testEmptyString()
    {
        assertFalse( checker.isValidSyntax( "" ) );
    }


    @Test
    public void testWrongCase()
    {
        assertFalse( checker.isValidSyntax( "Bad" ) );
        assertFalse( checker.isValidSyntax( "DEFuNCT" ) );
        assertFalse( checker.isValidSyntax( " DEFUNCT" ) );
        assertFalse( checker.isValidSyntax( "DEFUNCT$desc" ) );
        assertFalse( checker.isValidSyntax( "EXPERIMENTAL#test @ bad <desc>" ) );
    }


    @Test
    public void testCorrectCase()
    {
        assertTrue( checker.isValidSyntax( "DEFUNCT" ) );
        assertTrue( checker.isValidSyntax( "EXPERIMENTAL" ) );
        assertTrue( checker.isValidSyntax( "BEST-EFFORT" ) );
        assertTrue( checker.isValidSyntax( "PILOT-SERVICE" ) );
        assertTrue( checker.isValidSyntax( "FULL-SERVICE" ) );
        assertTrue( checker.isValidSyntax( "EXPERIMENTAL#test desc" ) );
        assertTrue( checker.isValidSyntax( "DEFUNCT#" ) );
    }
}
