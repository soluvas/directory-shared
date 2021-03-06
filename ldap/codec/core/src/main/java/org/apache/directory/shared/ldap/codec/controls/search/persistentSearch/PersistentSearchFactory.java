/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package org.apache.directory.shared.ldap.codec.controls.search.persistentSearch;


import org.apache.directory.shared.ldap.codec.api.ControlFactory;
import org.apache.directory.shared.ldap.codec.api.LdapApiService;
import org.apache.directory.shared.ldap.model.message.controls.PersistentSearch;


/**
 * 
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
public class PersistentSearchFactory implements ControlFactory<PersistentSearch, PersistentSearchDecorator>
{
    private LdapApiService codec;


    public PersistentSearchFactory( LdapApiService codec )
    {
        this.codec = codec;
    }


    /**
     * {@inheritDoc}
     */
    public String getOid()
    {
        return PersistentSearch.OID;
    }


    /**
     * {@inheritDoc}
     */
    public PersistentSearchDecorator newCodecControl()
    {
        return new PersistentSearchDecorator( codec );
    }


    /**
     * {@inheritDoc}
     */
    public PersistentSearchDecorator newCodecControl( PersistentSearch control )
    {
        return new PersistentSearchDecorator( codec, control );
    }
}
