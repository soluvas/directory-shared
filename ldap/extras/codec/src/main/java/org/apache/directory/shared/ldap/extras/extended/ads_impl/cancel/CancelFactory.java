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
package org.apache.directory.shared.ldap.extras.extended.ads_impl.cancel;


import org.apache.directory.shared.asn1.DecoderException;
import org.apache.directory.shared.ldap.codec.api.ExtendedRequestDecorator;
import org.apache.directory.shared.ldap.codec.api.ExtendedRequestFactory;
import org.apache.directory.shared.ldap.codec.api.ExtendedResponseDecorator;
import org.apache.directory.shared.ldap.codec.api.LdapApiService;
import org.apache.directory.shared.ldap.extras.extended.CancelRequestImpl;
import org.apache.directory.shared.ldap.extras.extended.CancelResponseImpl;
import org.apache.directory.shared.ldap.extras.extended.CancelRequest;
import org.apache.directory.shared.ldap.extras.extended.CancelResponse;
import org.apache.directory.shared.ldap.model.message.ExtendedRequest;
import org.apache.directory.shared.ldap.model.message.ExtendedResponse;


/**
 * An {@link ExtendedRequestFactory} for creating cancel extended request response 
 * pairs.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class CancelFactory implements ExtendedRequestFactory<CancelRequest, CancelResponse>
{
    private LdapApiService codec;


    public CancelFactory( LdapApiService codec )
    {
        this.codec = codec;
    }


    /**
     * {@inheritDoc}
     */
    public String getOid()
    {
        return CancelRequest.EXTENSION_OID;
    }


    /**
     * {@inheritDoc}
     */
    public CancelRequest newRequest()
    {
        return new CancelRequestDecorator( codec, new CancelRequestImpl() );
    }


    /**
     * {@inheritDoc}
     */
    public CancelResponse newResponse( byte[] encodedValue ) throws DecoderException
    {
        CancelResponseDecorator response = new CancelResponseDecorator( codec, new CancelResponseImpl() );
        response.setResponseValue( encodedValue );
        return response;
    }


    /**
     * {@inheritDoc}
     */
    public CancelRequest newRequest( byte[] value )
    {
        CancelRequestDecorator req = new CancelRequestDecorator( codec, new CancelRequestImpl() );
        req.setRequestValue( value );
        return req;
    }


    /**
     * {@inheritDoc}
     */
    public ExtendedRequestDecorator<CancelRequest, CancelResponse> decorate( ExtendedRequest<?> modelRequest )
    {
        if ( modelRequest instanceof CancelRequestDecorator )
        {
            return ( CancelRequestDecorator ) modelRequest;
        }

        return new CancelRequestDecorator( codec, ( CancelRequest ) modelRequest );
    }


    /**
     * {@inheritDoc}
     */
    public ExtendedResponseDecorator<CancelResponse> decorate( ExtendedResponse decoratedMessage )
    {
        if ( decoratedMessage instanceof CancelResponseDecorator )
        {
            return ( CancelResponseDecorator ) decoratedMessage;
        }

        return new CancelResponseDecorator( codec, ( CancelResponse ) decoratedMessage );
    }
}
