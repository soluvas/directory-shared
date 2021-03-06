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
package org.apache.directory.shared.ldap.codec.controls.search.entryChange;


import java.nio.ByteBuffer;

import org.apache.directory.shared.asn1.Asn1Object;
import org.apache.directory.shared.asn1.DecoderException;
import org.apache.directory.shared.asn1.EncoderException;
import org.apache.directory.shared.asn1.ber.Asn1Decoder;
import org.apache.directory.shared.asn1.ber.tlv.TLV;
import org.apache.directory.shared.asn1.ber.tlv.UniversalTag;
import org.apache.directory.shared.asn1.ber.tlv.BerValue;
import org.apache.directory.shared.i18n.I18n;
import org.apache.directory.shared.ldap.codec.api.ControlDecorator;
import org.apache.directory.shared.ldap.codec.api.LdapApiService;
import org.apache.directory.shared.ldap.model.message.controls.ChangeType;
import org.apache.directory.shared.ldap.model.message.controls.EntryChange;
import org.apache.directory.shared.ldap.model.message.controls.EntryChangeImpl;
import org.apache.directory.shared.ldap.model.name.Dn;
import org.apache.directory.shared.util.Strings;


/**
 * An EntryChange implementation, that wraps and decorates the Control with codec
 * specific functionality.
 *
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class EntryChangeDecorator extends ControlDecorator<EntryChange> implements EntryChange
{

    public static final int UNDEFINED_CHANGE_NUMBER = -1;

    /** A temporary storage for the previous Dn */
    private byte[] previousDnBytes = null;

    /** The entry change global length */
    private int eccSeqLength;

    /** An instance of this decoder */
    private static final Asn1Decoder decoder = new Asn1Decoder();


    /**
     * Creates a new instance of EntryChangeDecoder wrapping a newly created
     * EntryChange Control object.
     */
    public EntryChangeDecorator( LdapApiService codec )
    {
        super( codec, new EntryChangeImpl() );
    }


    /**
     * Creates a new instance of EntryChangeDecorator wrapping the supplied
     * EntryChange Control.
     *
     * @param control The EntryChange Control to be decorated.
     */
    public EntryChangeDecorator( LdapApiService codec, EntryChange control )
    {
        super( codec, control );
    }


    /**
     * Internally used to not have to cast the decorated Control.
     *
     * @return the decorated Control.
     */
    private EntryChange getEntryChange()
    {
        return ( EntryChange ) getDecorated();
    }


    /**
     * Compute the EntryChangeControl length 
     * 
     * 0x30 L1 
     *   | 
     *   +--> 0x0A 0x0(1-4) [1|2|4|8] (changeType) 
     *  [+--> 0x04 L2 previousDN] 
     *  [+--> 0x02 0x0(1-4) [0..2^63-1] (changeNumber)]
     */
    public int computeLength()
    {
        int changeTypesLength = 1 + 1 + 1;

        int previousDnLength = 0;
        int changeNumberLength = 0;

        if ( getPreviousDn() != null )
        {
            previousDnBytes = Strings.getBytesUtf8( getPreviousDn().getName() );
            previousDnLength = 1 + TLV.getNbBytes( previousDnBytes.length ) + previousDnBytes.length;
        }

        if ( getChangeNumber() != UNDEFINED_CHANGE_NUMBER )
        {
            changeNumberLength = 1 + 1 + BerValue.getNbBytes( getChangeNumber() );
        }

        eccSeqLength = changeTypesLength + previousDnLength + changeNumberLength;
        valueLength = 1 + TLV.getNbBytes( eccSeqLength ) + eccSeqLength;

        return valueLength;
    }


    /**
     * Encodes the entry change control.
     * 
     * @param buffer The encoded sink
     * @return A ByteBuffer that contains the encoded PDU
     * @throws EncoderException If anything goes wrong.
     */
    public ByteBuffer encode( ByteBuffer buffer ) throws EncoderException
    {
        if ( buffer == null )
        {
            throw new EncoderException( I18n.err( I18n.ERR_04023 ) );
        }

        buffer.put( UniversalTag.SEQUENCE.getValue() );
        buffer.put( TLV.getBytes( eccSeqLength ) );

        buffer.put( UniversalTag.ENUMERATED.getValue() );
        buffer.put( ( byte ) 1 );
        buffer.put( BerValue.getBytes( getChangeType().getValue() ) );

        if ( getPreviousDn() != null )
        {
            BerValue.encode( buffer, previousDnBytes );
        }

        if ( getChangeNumber() != UNDEFINED_CHANGE_NUMBER )
        {
            BerValue.encode( buffer, getChangeNumber() );
        }

        return buffer;
    }


    /**
     * {@inheritDoc}
     */
    public byte[] getValue()
    {
        if ( value == null )
        {
            try
            {
                computeLength();
                ByteBuffer buffer = ByteBuffer.allocate( valueLength );

                buffer.put( UniversalTag.SEQUENCE.getValue() );
                buffer.put( TLV.getBytes( eccSeqLength ) );

                buffer.put( UniversalTag.ENUMERATED.getValue() );
                buffer.put( ( byte ) 1 );
                buffer.put( BerValue.getBytes( getChangeType().getValue() ) );

                if ( getPreviousDn() != null )
                {
                    BerValue.encode( buffer, previousDnBytes );
                }

                if ( getChangeNumber() != UNDEFINED_CHANGE_NUMBER )
                {
                    BerValue.encode( buffer, getChangeNumber() );
                }

                value = buffer.array();
            }
            catch ( Exception e )
            {
                return null;
            }
        }

        return value;
    }


    /**
     * {@inheritDoc}
     */
    public ChangeType getChangeType()
    {
        return getEntryChange().getChangeType();
    }


    /**
     * {@inheritDoc}
     */
    public void setChangeType( ChangeType changeType )
    {
        getEntryChange().setChangeType( changeType );
    }


    /**
     * {@inheritDoc}
     */
    public Dn getPreviousDn()
    {
        return getEntryChange().getPreviousDn();
    }


    /**
     * {@inheritDoc}
     */
    public void setPreviousDn( Dn previousDn )
    {
        getEntryChange().setPreviousDn( previousDn );
    }


    /**
     * {@inheritDoc}
     */
    public long getChangeNumber()
    {
        return getEntryChange().getChangeNumber();
    }


    /**
     * {@inheritDoc}
     */
    public void setChangeNumber( long changeNumber )
    {
        getEntryChange().setChangeNumber( changeNumber );
    }


    /**
     * {@inheritDoc}
     */
    public Asn1Object decode( byte[] controlBytes ) throws DecoderException
    {
        ByteBuffer bb = ByteBuffer.wrap( controlBytes );
        EntryChangeContainer container = new EntryChangeContainer( getCodecService(), this );
        decoder.decode( bb, container );
        return this;
    }
}
