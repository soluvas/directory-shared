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
package org.apache.directory.shared.ldap.entry;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.directory.shared.ldap.model.entry.DefaultEntry;
import org.apache.directory.shared.ldap.model.entry.Entry;
import org.apache.directory.shared.ldap.model.exception.LdapException;
import org.apache.directory.shared.ldap.model.schema.SchemaManager;
import org.apache.directory.shared.ldap.schemamanager.impl.DefaultSchemaManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycila.junit.concurrent.Concurrency;
import com.mycila.junit.concurrent.ConcurrentJunitRunner;


/**
 * Test the Entry Serialization
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@RunWith(ConcurrentJunitRunner.class)
@Concurrency()
public class SchemaAwareEntrySerializationTest
{
    private static SchemaManager schemaManager;


    /**
     * Initialize OIDs maps for normalization
     */
    @BeforeClass
    public static void setup() throws Exception
    {
        schemaManager = new DefaultSchemaManager();
    }


    @Test
    public void testEntryFullSerialization() throws IOException, LdapException, ClassNotFoundException
    {
        Entry entry1 = new DefaultEntry(
            schemaManager,
            "dc=example, dc=com",
            "ObjectClass: top",
            "ObjectClass: domain",
            "dc: example",
            "l: test" );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream( baos );

        entry1.writeExternal( out );

        ObjectInputStream in = null;

        byte[] data = baos.toByteArray();
        in = new ObjectInputStream( new ByteArrayInputStream( data ) );

        Entry entry2 = new DefaultEntry( schemaManager );
        entry2.readExternal( in );

        assertEquals( entry1, entry2 );
        assertTrue( entry2.contains( "2.5.4.0", "top", "domain" ) );
    }


    @Test
    public void testEntryNoDnSerialization() throws IOException, LdapException, ClassNotFoundException
    {
        Entry entry1 = new DefaultEntry(
            schemaManager,
            "",
            "ObjectClass: top",
            "ObjectClass: domain",
            "dc: example",
            "l: test" );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream( baos );

        entry1.writeExternal( out );

        ObjectInputStream in = null;

        byte[] data = baos.toByteArray();
        in = new ObjectInputStream( new ByteArrayInputStream( data ) );

        Entry entry2 = new DefaultEntry( schemaManager );
        entry2.readExternal( in );

        assertEquals( entry1, entry2 );
        assertTrue( entry2.contains( "ObjectClass", "top", "domain" ) );
        assertEquals( "", entry2.getDn().toString() );
    }


    @Test
    public void testEntryNoAttributesSerialization() throws IOException, LdapException, ClassNotFoundException
    {
        Entry entry1 = new DefaultEntry( schemaManager, "dc=example, dc=com" );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream( baos );

        entry1.writeExternal( out );

        ObjectInputStream in = null;

        byte[] data = baos.toByteArray();
        in = new ObjectInputStream( new ByteArrayInputStream( data ) );

        Entry entry2 = new DefaultEntry( schemaManager );
        entry2.readExternal( in );

        assertEquals( entry1, entry2 );
        assertEquals( 0, entry2.size() );
    }


    @Test
    public void testEntryNoAttributesNoDnSerialization() throws IOException, LdapException, ClassNotFoundException
    {
        Entry entry1 = new DefaultEntry( schemaManager, "" );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream( baos );

        entry1.writeExternal( out );

        ObjectInputStream in = null;

        byte[] data = baos.toByteArray();
        in = new ObjectInputStream( new ByteArrayInputStream( data ) );

        Entry entry2 = new DefaultEntry( schemaManager );
        entry2.readExternal( in );

        assertEquals( entry1, entry2 );
        assertEquals( 0, entry2.size() );
    }
}
