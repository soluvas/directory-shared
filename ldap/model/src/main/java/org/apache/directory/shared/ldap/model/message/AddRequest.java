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

package org.apache.directory.shared.ldap.model.message;


import org.apache.directory.shared.ldap.model.entry.Entry;
import org.apache.directory.shared.ldap.model.exception.MessageException;
import org.apache.directory.shared.ldap.model.name.Dn;


/**
 * Add protocol operation request used to add a new entry to the DIT.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public interface AddRequest extends SingleReplyRequest<AddResponse>, AbandonableRequest
{
    /** LDAPv3 add request type enum code */
    MessageTypeEnum TYPE = MessageTypeEnum.ADD_REQUEST;

    /** LDAPv3 add response type enum code */
    MessageTypeEnum RESP_TYPE = AddResponse.TYPE;


    /**
     * Gets the distinguished name of the entry to add.
     * 
     * @return the Dn of the added entry.
     */
    Dn getEntryDn();


    /**
     * Sets the distinguished name of the entry to add.
     * 
     * @param entry the Dn of the added entry.
     * @return The AddRequest instance
     */
    AddRequest setEntryDn( Dn entry );


    /**
     * Gets the entry to add.
     * 
     * @return the added Entry
     */
    Entry getEntry();


    /**
     * Sets the Entry to add.
     * 
     * @param entry the added Entry
     * @return The AddRequest instance
     */
    AddRequest setEntry( Entry entry );


    /**
     * {@inheritDoc}
     */
    AddRequest setMessageId( int messageId );


    /**
     * {@inheritDoc}
     */
    AddRequest addControl( Control control ) throws MessageException;


    /**
     * {@inheritDoc}
     */
    AddRequest addAllControls( Control[] controls ) throws MessageException;


    /**
     * {@inheritDoc}
     */
    AddRequest removeControl( Control control ) throws MessageException;
}
