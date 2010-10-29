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
package org.apache.directory.shared.ldap.util;


import java.util.Hashtable;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.ContextNotEmptyException;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.naming.OperationNotSupportedException;
import javax.naming.PartialResultException;
import javax.naming.ReferralException;
import javax.naming.ServiceUnavailableException;
import javax.naming.TimeLimitExceededException;
import javax.naming.directory.AttributeInUseException;
import javax.naming.directory.InvalidAttributeIdentifierException;
import javax.naming.directory.InvalidAttributeValueException;
import javax.naming.directory.InvalidSearchFilterException;
import javax.naming.directory.NoSuchAttributeException;
import javax.naming.directory.SchemaViolationException;
import javax.naming.ldap.BasicControl;
import javax.naming.ldap.ExtendedRequest;
import javax.naming.ldap.ExtendedResponse;
import javax.naming.ldap.LdapName;

import org.apache.directory.shared.ldap.codec.MessageTypeEnum;
import org.apache.directory.shared.ldap.codec.controls.ControlImpl;
import org.apache.directory.shared.ldap.exception.LdapAffectMultipleDsaException;
import org.apache.directory.shared.ldap.exception.LdapAliasDereferencingException;
import org.apache.directory.shared.ldap.exception.LdapAliasException;
import org.apache.directory.shared.ldap.exception.LdapAttributeInUseException;
import org.apache.directory.shared.ldap.exception.LdapAuthenticationException;
import org.apache.directory.shared.ldap.exception.LdapAuthenticationNotSupportedException;
import org.apache.directory.shared.ldap.exception.LdapContextNotEmptyException;
import org.apache.directory.shared.ldap.exception.LdapEntryAlreadyExistsException;
import org.apache.directory.shared.ldap.exception.LdapInvalidAttributeTypeException;
import org.apache.directory.shared.ldap.exception.LdapInvalidAttributeValueException;
import org.apache.directory.shared.ldap.exception.LdapInvalidDnException;
import org.apache.directory.shared.ldap.exception.LdapInvalidSearchFilterException;
import org.apache.directory.shared.ldap.exception.LdapLoopDetectedException;
import org.apache.directory.shared.ldap.exception.LdapNoPermissionException;
import org.apache.directory.shared.ldap.exception.LdapNoSuchAttributeException;
import org.apache.directory.shared.ldap.exception.LdapNoSuchObjectException;
import org.apache.directory.shared.ldap.exception.LdapOperationErrorException;
import org.apache.directory.shared.ldap.exception.LdapOtherException;
import org.apache.directory.shared.ldap.exception.LdapPartialResultException;
import org.apache.directory.shared.ldap.exception.LdapProtocolErrorException;
import org.apache.directory.shared.ldap.exception.LdapReferralException;
import org.apache.directory.shared.ldap.exception.LdapSchemaViolationException;
import org.apache.directory.shared.ldap.exception.LdapServiceUnavailableException;
import org.apache.directory.shared.ldap.exception.LdapTimeLimitExceededException;
import org.apache.directory.shared.ldap.exception.LdapUnwillingToPerformException;
import org.apache.directory.shared.ldap.message.ExtendedResponseImpl;
import org.apache.directory.shared.ldap.message.LdapResult;
import org.apache.directory.shared.ldap.message.MessageException;
import org.apache.directory.shared.ldap.message.ResultResponse;
import org.apache.directory.shared.ldap.message.control.Control;
import org.apache.directory.shared.ldap.name.DN;


/**
 * An utility class to convert back and forth JNDI classes to ADS classes.
 * 
 * TODO: We have a similar class in shared-ldap-jndi!!!
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public final class JndiUtils
{
    /**
     * Private constructor.
     */
    private JndiUtils()
    {
    }


    public static javax.naming.ldap.Control toJndiControl( Control control )
    {
        byte[] value = control.getValue();
        javax.naming.ldap.Control jndiControl = new BasicControl( control.getOid(), control.isCritical(), value );

        return jndiControl;
    }


    public static javax.naming.ldap.Control[] toJndiControls( Control... controls )
    {
        if ( controls != null )
        {
            javax.naming.ldap.Control[] jndiControls = new javax.naming.ldap.Control[controls.length];
            int i = 0;

            for ( Control control : controls )
            {
                jndiControls[i++] = toJndiControl( control );
            }

            return jndiControls;
        }
        else
        {
            return null;
        }
    }


    public static Control fromJndiControl( javax.naming.ldap.Control jndiControl )
    {
        Control control = new ControlImpl( jndiControl.getID() );

        control.setValue( jndiControl.getEncodedValue() );

        return control;
    }


    public static Control[] fromJndiControls( javax.naming.ldap.Control... jndiControls )
    {
        if ( jndiControls != null )
        {
            Control[] controls = new Control[jndiControls.length];
            int i = 0;

            for ( javax.naming.ldap.Control jndiControl : jndiControls )
            {
                controls[i++] = fromJndiControl( jndiControl );
            }

            return controls;
        }
        else
        {
            return null;
        }
    }


    /**
     * TODO toJndiExtendedResponse. This is NOT correct ATM
     *
     * @param request
     * @return
     */
    public static ExtendedResponse toJndiExtendedResponse(
        final org.apache.directory.shared.ldap.message.ExtendedResponse response )
    {
        class JndiExtendedResponse implements ExtendedResponse
        {
            public byte[] getEncodedValue()
            {
                return response.getEncodedValue();
            }


            public String getID()
            {
                return response.getResponseName();
            }
        }

        return new JndiExtendedResponse();
    }


    public static ExtendedRequest toJndiExtendedRequest(
        final org.apache.directory.shared.ldap.message.ExtendedRequest request )
    {
        class JndiExtendedRequest implements ExtendedRequest
        {
            private ExtendedResponse response;


            public ExtendedResponse createExtendedResponse( String id, byte[] berValue, int offset, int length )
                throws NamingException
            {
                org.apache.directory.shared.ldap.message.ExtendedResponse response = new ExtendedResponseImpl( request
                    .getMessageId(), request.getRequestName() );
                response.setResponseName( id );
                response.setResponseValue( berValue );

                this.response = JndiUtils.toJndiExtendedResponse( response );

                return this.response;
            }


            public byte[] getEncodedValue()
            {
                return request.getRequestValue();
            }


            public String getID()
            {
                return request.getRequestName();
            }

        }

        return new JndiExtendedRequest();
    }


    /**
     * TODO toJndiExtendedResponse. This is NOT correct ATM
     *
     * @param request
     * @return
     */
    public static org.apache.directory.shared.ldap.message.ExtendedResponse fromJndiExtendedResponse(
        final ExtendedResponse response )
    {
        class ServerExtendedResponse implements org.apache.directory.shared.ldap.message.ExtendedResponse
        {
            public String getResponseName()
            {
                return response.getID();
            }


            public byte[] getResponseValue()
            {
                return response.getEncodedValue();
            }


            public void setResponseName( String oid )
            {
            }


            public void setResponseValue( byte[] responseValue )
            {
            }


            public LdapResult getLdapResult()
            {
                return null;
            }


            public void addAllControls( Control[] controls ) throws MessageException
            {
            }


            public void addControl( Control control ) throws MessageException
            {
            }


            public Object get( Object key )
            {
                return null;
            }


            public Control getControl( String oid )
            {
                return null;
            }


            public Map<String, Control> getControls()
            {
                return null;
            }


            public int getControlsLength()
            {
                return 0;
            }


            public Control getCurrentControl()
            {
                return null;
            }


            public int getMessageId()
            {
                return 0;
            }


            public int getMessageLength()
            {
                return 0;
            }


            public MessageTypeEnum getType()
            {
                return null;
            }


            public boolean hasControl( String oid )
            {
                return false;
            }


            public Object put( Object key, Object value )
            {
                return null;
            }


            public void removeControl( Control control ) throws MessageException
            {
            }


            public void setControlsLength( int controlsLength )
            {
            }


            public void setMessageId( int messageId )
            {
            }


            public void setMessageLength( int messageLength )
            {
            }


            public byte[] getEncodedValue()
            {
                // TODO Auto-generated method stub
                return null;
            }


            public String getID()
            {
                // TODO Auto-generated method stub
                return null;
            }
        }

        return new ServerExtendedResponse();
    }


    public static org.apache.directory.shared.ldap.message.ExtendedRequest fromJndiExtendedRequest(
        final ExtendedRequest request )
    {
        class ServerExtendedRequest implements org.apache.directory.shared.ldap.message.ExtendedRequest
        {
            public String getRequestName()
            {
                return request.getID();
            }


            public byte[] getRequestValue()
            {
                return request.getEncodedValue();
            }


            public void setRequestName( String oid )
            {
            }


            public void setRequestValue( byte[] requestValue )
            {
            }


            public MessageTypeEnum getResponseType()
            {
                return null;
            }


            public ResultResponse getResultResponse()
            {
                return null;
            }


            public boolean hasResponse()
            {
                return false;
            }


            public void addAllControls( Control[] controls ) throws MessageException
            {
            }


            public void addControl( Control control ) throws MessageException
            {
            }


            public Object get( Object key )
            {
                return null;
            }


            public Control getControl( String oid )
            {
                return null;
            }


            public Map<String, Control> getControls()
            {
                return null;
            }


            public int getControlsLength()
            {
                return 0;
            }


            public Control getCurrentControl()
            {
                return null;
            }


            public int getMessageId()
            {
                return 0;
            }


            public int getMessageLength()
            {
                return 0;
            }


            public MessageTypeEnum getType()
            {
                return null;
            }


            public boolean hasControl( String oid )
            {
                return false;
            }


            public Object put( Object key, Object value )
            {
                return null;
            }


            public void removeControl( Control control ) throws MessageException
            {
            }


            public void setControlsLength( int controlsLength )
            {
            }


            public void setMessageId( int messageId )
            {
            }


            public void setMessageLength( int messageLength )
            {
            }

        }

        return new ServerExtendedRequest();
    }


    public static void wrap( Throwable t ) throws NamingException
    {
        if ( t instanceof NamingException )
        {
            throw ( NamingException ) t;
        }

        NamingException ne = null;

        if ( t instanceof LdapAffectMultipleDsaException )
        {
            ne = new NamingException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapAliasDereferencingException )
        {
            ne = new NamingException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapAliasException )
        {
            ne = new NamingException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapAttributeInUseException )
        {
            ne = new AttributeInUseException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapAuthenticationException )
        {
            ne = new AuthenticationException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapAuthenticationNotSupportedException )
        {
            ne = new AuthenticationNotSupportedException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapContextNotEmptyException )
        {
            ne = new ContextNotEmptyException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapEntryAlreadyExistsException )
        {
            ne = new NameAlreadyBoundException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapInvalidAttributeTypeException )
        {
            ne = new InvalidAttributeIdentifierException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapInvalidAttributeValueException )
        {
            ne = new InvalidAttributeValueException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapInvalidDnException )
        {
            ne = new InvalidNameException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapInvalidSearchFilterException )
        {
            ne = new InvalidSearchFilterException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapLoopDetectedException )
        {
            ne = new NamingException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapNoPermissionException )
        {
            ne = new NoPermissionException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapNoSuchAttributeException )
        {
            ne = new NoSuchAttributeException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapNoSuchObjectException )
        {
            ne = new NameNotFoundException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapOperationErrorException )
        {
            ne = new NamingException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapOtherException )
        {
            ne = new NamingException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapProtocolErrorException )
        {
            ne = new CommunicationException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapReferralException )
        {
            ne = new WrappedReferralException( ( LdapReferralException ) t );
        }
        else if ( t instanceof LdapPartialResultException )
        {
            ne = new WrappedPartialResultException( ( LdapPartialResultException ) t );
        }
        else if ( t instanceof LdapSchemaViolationException )
        {
            ne = new SchemaViolationException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapServiceUnavailableException )
        {
            ne = new ServiceUnavailableException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapTimeLimitExceededException )
        {
            ne = new TimeLimitExceededException( t.getLocalizedMessage() );
        }
        else if ( t instanceof LdapUnwillingToPerformException )
        {
            ne = new OperationNotSupportedException( t.getLocalizedMessage() );
        }
        else
        {
            ne = new NamingException( t.getLocalizedMessage() );
        }

        ne.setRootCause( t );

        throw ne;
    }


    /**
     * Convert a DN to a {@link javax.naming.Name}
     *
     * @param name The DN to convert
     * @return A Name
     */
    public static Name toName( DN dn )
    {
        try
        {
            Name name = new LdapName( dn.toString() );

            return name;
        }
        catch ( InvalidNameException ine )
        {
            // TODO : check if we must throw an exception.
            // Logically, the DN must be valid.
            return null;
        }
    }


    /**
     * Convert a {@link javax.naming.Name} to a DN
     *
     * @param name The Name to convert
     * @return A DN
     */
    public static DN fromName( Name name )
    {
        try
        {
            DN dn = new DN( name.toString() );

            return dn;
        }
        catch ( LdapInvalidDnException lide )
        {
            // TODO : check if we must throw an exception.
            // Logically, the Name must be valid.
            return null;
        }
    }
}

// a ReferralException around the LdapReferralException to be used in tests
class WrappedReferralException extends ReferralException
{
    private static final long serialVersionUID = 1L;

    private LdapReferralException lre;


    public WrappedReferralException( LdapReferralException lre )
    {
        this.lre = lre;
    }


    @Override
    public boolean skipReferral()
    {
        return lre.skipReferral();
    }


    @Override
    public void retryReferral()
    {
        lre.retryReferral();
    }


    @Override
    public Object getReferralInfo()
    {
        return lre.getReferralInfo();
    }


    @Override
    public Context getReferralContext( Hashtable<?, ?> env ) throws NamingException
    {
        return lre.getReferralContext( env );
    }


    @Override
    public Context getReferralContext() throws NamingException
    {
        return lre.getReferralContext();
    }


    @Override
    public Name getRemainingName()
    {
        return JndiUtils.toName( lre.getRemainingDn() );
    }


    @Override
    public Object getResolvedObj()
    {
        return lre.getResolvedObject();
    }


    @Override
    public Name getResolvedName()
    {
        return JndiUtils.toName( lre.getResolvedDn() );
    }
}

// a PartialResultException around the LdapPartialResultException to be used in tests
class WrappedPartialResultException extends PartialResultException
{
    private static final long serialVersionUID = 1L;

    private LdapPartialResultException lpre;


    public WrappedPartialResultException( LdapPartialResultException lpre )
    {
        this.lpre = lpre;
    }


    @Override
    public Name getRemainingName()
    {
        return JndiUtils.toName( lpre.getRemainingDn() );
    }


    @Override
    public Object getResolvedObj()
    {
        return lpre.getResolvedObject();
    }


    @Override
    public Name getResolvedName()
    {
        return JndiUtils.toName( lpre.getResolvedDn() );
    }
}