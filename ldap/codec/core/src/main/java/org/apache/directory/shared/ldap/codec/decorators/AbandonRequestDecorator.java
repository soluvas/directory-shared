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
package org.apache.directory.shared.ldap.codec.decorators;


import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import org.apache.directory.shared.asn1.EncoderException;
import org.apache.directory.shared.asn1.ber.tlv.BerValue;
import org.apache.directory.shared.i18n.I18n;
import org.apache.directory.shared.ldap.codec.api.LdapApiService;
import org.apache.directory.shared.ldap.codec.api.LdapConstants;
import org.apache.directory.shared.ldap.model.exception.MessageException;
import org.apache.directory.shared.ldap.model.message.AbandonRequest;
import org.apache.directory.shared.ldap.model.message.Control;


/**
 * A decorator for the AddRequest message
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public final class AbandonRequestDecorator extends RequestDecorator<AbandonRequest>
    implements AbandonRequest
{
    /**
     * Makes a AddRequest a MessageDecorator.
     *
     * @param decoratedMessage the decorated AddRequest
     */
    public AbandonRequestDecorator( LdapApiService codec, AbandonRequest decoratedMessage )
    {
        super( codec, decoratedMessage );
    }


    //-------------------------------------------------------------------------
    // The AbandonRequest methods
    //-------------------------------------------------------------------------

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
    public AbandonRequest setAbandoned( int requestId )
    {
        getDecorated().setAbandoned( requestId );

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public AbandonRequest setMessageId( int messageId )
    {
        super.setMessageId( messageId );

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public AbandonRequest addControl( Control control ) throws MessageException
    {
        return ( AbandonRequest ) super.addControl( control );
    }


    /**
     * {@inheritDoc}
     */
    public AbandonRequest addAllControls( Control[] controls ) throws MessageException
    {
        return ( AbandonRequest ) super.addAllControls( controls );
    }


    /**
     * {@inheritDoc}
     */
    public AbandonRequest removeControl( Control control ) throws MessageException
    {
        return ( AbandonRequest ) super.removeControl( control );
    }


    //-------------------------------------------------------------------------
    // The Decorator methods
    //-------------------------------------------------------------------------

    /**
     * Encode the Abandon protocolOp part
     */
    public ByteBuffer encode( ByteBuffer buffer ) throws EncoderException
    {
        try
        {
            // The tag
            buffer.put( LdapConstants.ABANDON_REQUEST_TAG );

            // The length. It has to be evaluated depending on
            // the abandoned messageId value.
            buffer.put( ( byte ) BerValue.getNbBytes( getAbandoned() ) );

            // The abandoned messageId
            buffer.put( BerValue.getBytes( getAbandoned() ) );
        }
        catch ( BufferOverflowException boe )
        {
            String msg = I18n.err( I18n.ERR_04005 );
            throw new EncoderException( msg );
        }

        return buffer;
    }


    /**
     * Compute the AbandonRequest length 
     * 
     * AbandonRequest : 
     * 0x50 0x0(1..4) abandoned MessageId 
     * 
     * Length(AbandonRequest) = Length(0x50) + 1 + Length(abandoned MessageId)
     */
    public int computeLength()
    {
        int length = 1 + 1 + BerValue.getNbBytes( getAbandoned() );

        return length;
    }
}
