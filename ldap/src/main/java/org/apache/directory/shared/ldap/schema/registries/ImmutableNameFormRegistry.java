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
package org.apache.directory.shared.ldap.schema.registries;

import java.util.Iterator;

import javax.naming.NamingException;

import org.apache.directory.shared.ldap.exception.LdapOperationNotSupportedException;
import org.apache.directory.shared.ldap.message.ResultCodeEnum;
import org.apache.directory.shared.ldap.schema.NameForm;
import org.apache.directory.shared.ldap.schema.SchemaObjectType;




/**
 * An immutable wrapper of the NameForm registry.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev: 828111 $
 */
public class ImmutableNameFormRegistry implements NameFormRegistry
{
    /** The wrapped NameForm registry */
    NameFormRegistry immutableNameFormRegistry;
    
    /**
     * Creates a new instance of ImmutableNameFormRegistry.
     *
     * @param nameFormRegistry The wrapped NameForm registry
     */
    public ImmutableNameFormRegistry( NameFormRegistry nameFormRegistry )
    {
        immutableNameFormRegistry = nameFormRegistry;
    }
    
    
    /**
     * {@inheritDoc}
     */
    public ImmutableNameFormRegistry clone() throws CloneNotSupportedException
    {
        return (ImmutableNameFormRegistry)immutableNameFormRegistry.clone();
    }
    
    
    /**
     * {@inheritDoc}
     */
    public int size()
    {
        return immutableNameFormRegistry.size();
    }


    /**
     * {@inheritDoc}
     */
    public boolean contains( String oid )
    {
        return immutableNameFormRegistry.contains( oid );
    }


    /**
     * {@inheritDoc}
     */
    public boolean containsName( String name )
    {
        return immutableNameFormRegistry.containsName( name );
    }


    /**
     * {@inheritDoc}
     */
    public String getOidByName( String name ) throws NamingException
    {
        return immutableNameFormRegistry.getOidByName( name );
    }


    /**
     * {@inheritDoc}
     */
    public String getSchemaName( String oid ) throws NamingException
    {
        return immutableNameFormRegistry.getSchemaName( oid );
    }


    /**
     * {@inheritDoc}
     */
    public SchemaObjectType getType()
    {
        return immutableNameFormRegistry.getType();
    }


    /**
     * {@inheritDoc}
     */
    public Iterator<NameForm> iterator()
    {
        return immutableNameFormRegistry.iterator();
    }


    /**
     * {@inheritDoc}
     */
    public NameForm lookup( String oid ) throws NamingException
    {
        return immutableNameFormRegistry.lookup( oid );
    }


    /**
     * {@inheritDoc}
     */
    public Iterator<String> oidsIterator()
    {
        return immutableNameFormRegistry.oidsIterator();
    }


    /**
     * {@inheritDoc}
     */
    public void register( NameForm schemaObject ) throws NamingException
    {
        throw new LdapOperationNotSupportedException( "Cannot modify the NameFormRegistry copy", ResultCodeEnum.NO_SUCH_OPERATION );
    }


    /**
     * {@inheritDoc}
     */
    public void renameSchema( String originalSchemaName, String newSchemaName ) throws NamingException
    {
        throw new LdapOperationNotSupportedException( "Cannot modify the NameFormRegistry copy", ResultCodeEnum.NO_SUCH_OPERATION );
    }


    /**
     * {@inheritDoc}
     */
    public NameForm unregister( String numericOid ) throws NamingException
    {
        throw new LdapOperationNotSupportedException( "Cannot modify the NameFormRegistry copy", ResultCodeEnum.NO_SUCH_OPERATION );
    }


    /**
     * {@inheritDoc}
     */
    public void unregisterSchemaElements( String schemaName ) throws NamingException
    {
        throw new LdapOperationNotSupportedException( "Cannot modify the NameFormRegistry copy", ResultCodeEnum.NO_SUCH_OPERATION );
    }
}