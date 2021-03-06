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


import org.apache.directory.shared.ldap.model.exception.MessageException;


/**
 * ExtendedRequest implementation.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public abstract class AbstractExtendedRequest<R extends ExtendedResponse> extends AbstractRequest implements
    ExtendedRequest<R>
{
    static final long serialVersionUID = 7916990159044177480L;

    /** Extended request's Object Identifier or <b>requestName</b> */
    private String oid;

    /** The associated response */
    protected R response;


    /**
     * Creates an ExtendedRequest implementing object used to perform
     * extended protocol operation on the server.
     */
    public AbstractExtendedRequest()
    {
        super( -1, TYPE, true );
    }


    /**
     * Creates an ExtendedRequest implementing object used to perform
     * extended protocol operation on the server.
     * 
     * @param id the sequential message identifier
     */
    public AbstractExtendedRequest( final int id )
    {
        super( id, TYPE, true );
    }


    // -----------------------------------------------------------------------
    // ExtendedRequest Interface Method Implementations
    // -----------------------------------------------------------------------

    /**
     * Gets the Object Identifier corresponding to the extended request type.
     * This is the <b>requestName</b> portion of the ext. req. PDU.
     * 
     * @return the dotted-decimal representation as a String of the OID
     */
    public String getRequestName()
    {
        return oid;
    }


    /**
     * Sets the Object Identifier corresponding to the extended request type.
     * 
     * @param newOid the dotted-decimal representation as a String of the OID
     */
    public ExtendedRequest<R> setRequestName( String newOid )
    {
        this.oid = newOid;

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public ExtendedRequest<R> setMessageId( int messageId )
    {
        super.setMessageId( messageId );

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public ExtendedRequest<R> addControl( Control control ) throws MessageException
    {
        return ( ExtendedRequest<R> ) super.addControl( control );
    }


    /**
     * {@inheritDoc}
     */
    public ExtendedRequest<R> addAllControls( Control[] controls ) throws MessageException
    {
        return ( ExtendedRequest<R> ) super.addAllControls( controls );
    }


    /**
     * {@inheritDoc}
     */
    public ExtendedRequest<R> removeControl( Control control ) throws MessageException
    {
        return ( ExtendedRequest<R> ) super.removeControl( control );
    }


    // ------------------------------------------------------------------------
    // SingleReplyRequest Interface Method Implementations
    // ------------------------------------------------------------------------

    /**
     * Gets the protocol response message type for this request which produces
     * at least one response.
     * 
     * @return the message type of the response.
     */
    public MessageTypeEnum getResponseType()
    {
        return RESP_TYPE;
    }


    /**
     * The result containing response for this request.
     * 
     * @return the result containing response for this request
     */
    public abstract R getResultResponse();


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        int hash = 37;
        if ( oid != null )
        {
            hash = hash * 17 + oid.hashCode();
        }
        hash = hash * 17 + super.hashCode();

        return hash;
    }


    /**
     * Checks to see if an object equals this ExtendedRequest.
     * 
     * @param obj the object to be checked for equality
     * @return true if the obj equals this ExtendedRequest, false otherwise
     */
    public boolean equals( Object obj )
    {
        if ( obj == this )
        {
            return true;
        }

        if ( !super.equals( obj ) )
        {
            return false;
        }

        if ( !( obj instanceof ExtendedRequest ) )
        {
            return false;
        }

        ExtendedRequest<?> req = ( ExtendedRequest<?> ) obj;

        if ( ( oid != null ) && ( req.getRequestName() == null ) )
        {
            return false;
        }

        if ( ( oid == null ) && ( req.getRequestName() != null ) )
        {
            return false;
        }

        if ( ( oid != null ) && ( req.getRequestName() != null ) && !oid.equals( req.getRequestName() ) )
        {
            return false;
        }

        return true;
    }


    /**
     * Get a String representation of an Extended Request
     * 
     * @return an Extended Request String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append( "    Extended request\n" );
        sb.append( "        Request name : '" ).append( oid ).append( "'\n" );

        // The controls
        sb.append( super.toString() );

        return super.toString( sb.toString() );
    }
}
