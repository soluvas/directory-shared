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
package org.apache.directory.shared.ldap.registries;

import javax.naming.NamingException;

import org.apache.directory.shared.ldap.schema.AttributeType;
import org.apache.directory.shared.ldap.schema.registries.AttributeTypeRegistry;
import org.apache.directory.shared.ldap.schema.registries.OidRegistry;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Test the AttributeTypeRegistry
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
public class AttributeTypeRegistryTest
{
    AttributeTypeRegistry atRegistry;
    
    @Before
    public void setup()
    {
        atRegistry = new AttributeTypeRegistry( new OidRegistry() );
    }
    
    
    @Test
    public void testUnregister() throws NamingException
    {
        AttributeType at0 = new AttributeType( "1.1" );
        at0.addName( "t", "test", "Test", "T" );
        atRegistry.register( at0 );
        
        atRegistry.unregister( "1.1" );
        assertFalse( atRegistry.contains( "1.1" ) );
        assertFalse( atRegistry.contains( "t" ) );
        assertFalse( atRegistry.contains( "T" ) );
        assertFalse( atRegistry.contains( "tEsT" ) );

        try
        {
            atRegistry.getOidByName( "T" );
            fail();
        }
        catch ( NamingException ne )
        {
            assertTrue( true );
        }
    }
    
    
    @Test
    public void testRegister() throws NamingException
    {
        AttributeType at0 = new AttributeType( "1.1" );
        at0.addName( "t", "test", "Test", "T" );
        atRegistry.register( at0 );
        
        assertTrue( atRegistry.contains( "1.1" ) );
        assertTrue( atRegistry.contains( "t" ) );
        assertTrue( atRegistry.contains( "T" ) );
        assertTrue( atRegistry.contains( "tEsT" ) );
        assertEquals( "1.1", atRegistry.getOidByName( "T" ) );
        
        try
        {
            atRegistry.register( at0 );
            fail();
        }
        catch ( NamingException ne )
        {
            assertTrue( true );
        }
    }
}