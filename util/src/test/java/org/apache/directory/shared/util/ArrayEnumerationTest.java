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
package org.apache.directory.shared.util;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import com.mycila.junit.concurrent.Concurrency;
import com.mycila.junit.concurrent.ConcurrentJunitRunner;
import org.apache.directory.shared.util.ArrayEnumeration;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Tests the ArrayEnumeration class.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@RunWith(ConcurrentJunitRunner.class)
@Concurrency()
public class ArrayEnumerationTest
{
    @Test
    public void testAll()
    {
        // test with null array

        Object[] array = null;

        ArrayEnumeration list = new ArrayEnumeration( array );

        assertFalse( list.hasMoreElements() );

        try
        {
            list.nextElement();

            fail( "should never get here due to a NoSuchElementException" );
        }
        catch ( NoSuchElementException e )
        {
        }

        // test with empty array

        array = new Object[0];

        list = new ArrayEnumeration( array );

        assertFalse( list.hasMoreElements() );

        assertFalse( list.hasMoreElements() );

        try
        {
            list.nextElement();

            fail( "should never get here due to a NoSuchElementException" );
        }
        catch ( NoSuchElementException e )
        {
        }

        // test with one object

        array = new Object[]
            { new Object() };

        list = new ArrayEnumeration( array );

        assertTrue( list.hasMoreElements() );

        assertNotNull( list.nextElement() );

        assertFalse( list.hasMoreElements() );

        try
        {
            list.nextElement();

            fail( "should never get here due to a NoSuchElementException" );
        }
        catch ( NoSuchElementException e )
        {
        }

        // test with two objects

        array = new Object[]
            { new Object(), new Object() };

        list = new ArrayEnumeration( array );

        assertTrue( list.hasMoreElements() );

        assertNotNull( list.nextElement() );

        assertTrue( list.hasMoreElements() );

        assertNotNull( list.nextElement() );

        assertFalse( list.hasMoreElements() );

        try
        {
            list.nextElement();

            fail( "should never get here due to a NoSuchElementException" );
        }
        catch ( NoSuchElementException e )
        {
        }

        // test with three elements

        array = new Object[]
            { new Object(), new Object(), new Object() };

        list = new ArrayEnumeration( array );

        assertTrue( list.hasMoreElements() );

        assertNotNull( list.nextElement() );

        assertTrue( list.hasMoreElements() );

        assertNotNull( list.nextElement() );

        assertTrue( list.hasMoreElements() );

        assertNotNull( list.nextElement() );

        assertFalse( list.hasMoreElements() );

        try
        {
            list.nextElement();

            fail( "should never get here due to a NoSuchElementException" );
        }
        catch ( NoSuchElementException e )
        {
        }
    }
}
