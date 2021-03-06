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
package org.apache.directory.shared.ldap.codec.actions.searchResultEntry;


import org.apache.directory.shared.asn1.DecoderException;
import org.apache.directory.shared.asn1.ber.grammar.GrammarAction;
import org.apache.directory.shared.asn1.ber.tlv.TLV;
import org.apache.directory.shared.i18n.I18n;
import org.apache.directory.shared.ldap.codec.api.LdapMessageContainer;
import org.apache.directory.shared.ldap.codec.decorators.SearchResultEntryDecorator;
import org.apache.directory.shared.ldap.model.exception.LdapException;
import org.apache.directory.shared.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The action used to store the SearchResultEntry attributes
 * <pre>
 * SearchResultEntry ::= [APPLICATION 4] SEQUENCE { ...
 *     ...
 *     attributes PartialAttributeList }
 *
 * PartialAttributeList ::= SEQUENCE OF SEQUENCE {
 *     type  AttributeDescription,
 *     ...
 * </pre>
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class AddAttributeType extends GrammarAction<LdapMessageContainer<SearchResultEntryDecorator>>
{
    /** The logger */
    private static final Logger LOG = LoggerFactory.getLogger( AddAttributeType.class );

    /** Speedup for logs */
    private static final boolean IS_DEBUG = LOG.isDebugEnabled();


    /**
     * Instantiates a new action.
     */
    public AddAttributeType()
    {
        super( "Store the AttributeType" );
    }


    /**
     * {@inheritDoc}
     */
    public void action( LdapMessageContainer<SearchResultEntryDecorator> container ) throws DecoderException
    {
        SearchResultEntryDecorator searchResultEntry = container.getMessage();

        TLV tlv = container.getCurrentTLV();

        String type = "";

        // Store the type
        if ( tlv.getLength() == 0 )
        {
            // The type can't be null
            String msg = I18n.err( I18n.ERR_04081 );
            LOG.error( msg );
            throw new DecoderException( msg );
        }
        else
        {
            type = Strings.utf8ToString( tlv.getValue().getData() );

            try
            {
                searchResultEntry.addAttribute( type );
            }
            catch ( LdapException ine )
            {
                // This is for the client side. We will never decode LdapResult on the server
                String msg = "The Attribute type " + type + "is invalid : " + ine.getMessage();
                LOG.error( "{} : {}", msg, ine.getMessage() );
                throw new DecoderException( msg, ine );
            }
        }

        if ( IS_DEBUG )
        {
            LOG.debug( "Attribute type : {}", type );
        }
    }
}
