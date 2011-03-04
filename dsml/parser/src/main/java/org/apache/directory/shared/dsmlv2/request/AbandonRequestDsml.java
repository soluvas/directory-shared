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
package org.apache.directory.shared.dsmlv2.request;


import org.apache.directory.shared.ldap.codec.api.LdapCodecService;
import org.apache.directory.shared.ldap.model.message.AbandonRequest;
import org.apache.directory.shared.ldap.model.message.AbandonRequestImpl;
import org.apache.directory.shared.ldap.model.message.MessageTypeEnum;
import org.dom4j.Element;


/**
 * DSML Decorator for AbandonRequest
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class AbandonRequestDsml extends AbstractRequestDsml<AbandonRequest> 
    implements AbandonRequest
{
    /**
     * Creates a new instance of AbandonRequestDsml.
     */
    public AbandonRequestDsml( LdapCodecService codec )
    {
        super( codec, new AbandonRequestImpl() );
    }


    /**
     * Creates a new instance of AbandonRequestDsml.
     *
     * @param ldapMessage the message to decorate
     */
    public AbandonRequestDsml( LdapCodecService codec, AbandonRequest ldapMessage )
    {
        super( codec, ldapMessage );
    }


    /**
     * {@inheritDoc}
     */
    public MessageTypeEnum getType()
    {
        return getDecorated().getType();
    }


    /**
     * {@inheritDoc}
     */
    public Element toDsml( Element root )
    {
        Element element = super.toDsml( root );

        // AbandonID
        if ( getDecorated().getAbandoned() != 0 )
        {
            element.addAttribute( "abandonID", "" + getDecorated().getAbandoned() );
        }

        return element;
    }


    /**
     * Get the abandoned message ID
     * 
     * @return Returns the abandoned MessageId.
     */
    public int getAbandonedMessageId()
    {
        return getDecorated().getAbandoned();
    }


    /**
     * Set the abandoned message ID
     * 
     * @param abandonedMessageId The abandoned messageID to set.
     */
    public void setAbandonedMessageId( int abandonedMessageId )
    {
        getDecorated().setAbandoned( abandonedMessageId );
    }


    /**
     * {@inheritDoc}
     */
    public int getAbandoned()
    {
        return getDecorated().getAbandoned();
    }


    /**
     * {@inheritDoc}
     */
    public void setAbandoned( int requestId )
    {
        getDecorated().setAbandoned( requestId );
    }
}