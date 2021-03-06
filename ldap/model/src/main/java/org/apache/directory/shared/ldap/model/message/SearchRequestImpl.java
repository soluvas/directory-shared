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


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.directory.shared.ldap.model.exception.LdapException;
import org.apache.directory.shared.ldap.model.exception.LdapProtocolErrorException;
import org.apache.directory.shared.ldap.model.exception.MessageException;
import org.apache.directory.shared.ldap.model.filter.BranchNormalizedVisitor;
import org.apache.directory.shared.ldap.model.filter.ExprNode;
import org.apache.directory.shared.ldap.model.filter.FilterParser;
import org.apache.directory.shared.ldap.model.name.Dn;
import org.apache.directory.shared.util.Strings;


/**
 * SearchRequest implementation.
 * 
 * @author <a href="mailto:dev@directory.apache.org"> Apache Directory Project</a>
 */
public class SearchRequestImpl extends AbstractAbandonableRequest implements SearchRequest
{
    static final long serialVersionUID = -5655881944020886218L;

    /** Search base distinguished name */
    private Dn baseDn;

    /** Search filter expression tree's root node */
    private ExprNode filterNode;

    /** Search scope enumeration value */
    private SearchScope scope;

    /** Types only return flag */
    private boolean typesOnly;

    /** Max size in entries to return */
    private long sizeLimit;

    /** Max seconds to wait for search to complete */
    private int timeLimit;

    /** Alias dereferencing mode enumeration value (default to DEREF_ALWAYS) */
    private AliasDerefMode aliasDerefMode = AliasDerefMode.DEREF_ALWAYS;

    /** Attributes to return */
    private List<String> attributes = new ArrayList<String>();

    /** The final result containing SearchResponseDone response */
    private SearchResultDone response;


    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------
    /**
     * Creates a SearcRequest implementing object used to search the
     * DIT.
     */
    public SearchRequestImpl()
    {
        super( -1, MessageTypeEnum.SEARCH_REQUEST );
    }


    // ------------------------------------------------------------------------
    // SearchRequest Interface Method Implementations
    // ------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public List<String> getAttributes()
    {
        return Collections.unmodifiableList( attributes );
    }


    /**
     * Gets the search base as a distinguished name.
     * 
     * @return the search base
     */
    public Dn getBase()
    {
        return baseDn;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest setBase( Dn base )
    {
        baseDn = base;

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public AliasDerefMode getDerefAliases()
    {
        return aliasDerefMode;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest setDerefAliases( AliasDerefMode aliasDerefAliases )
    {
        this.aliasDerefMode = aliasDerefAliases;

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public ExprNode getFilter()
    {
        return filterNode;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest setFilter( ExprNode filter )
    {
        this.filterNode = filter;
        return this;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest setFilter( String filter ) throws LdapException
    {
        try
        {
            filterNode = FilterParser.parse( Strings.getBytesUtf8( filter ) );
        }
        catch ( ParseException pe )
        {
            String msg = "The filter" + filter + " is invalid.";
            throw new LdapProtocolErrorException( msg, pe );
        }

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public MessageTypeEnum[] getResponseTypes()
    {
        return RESPONSE_TYPES.clone();
    }


    /**
     * {@inheritDoc}
     */
    public SearchScope getScope()
    {
        return scope;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest setScope( SearchScope scope )
    {
        this.scope = scope;

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public long getSizeLimit()
    {
        return sizeLimit;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest setSizeLimit( long entriesMax )
    {
        sizeLimit = entriesMax;

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public int getTimeLimit()
    {
        return timeLimit;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest setTimeLimit( int secondsMax )
    {
        timeLimit = secondsMax;

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public boolean getTypesOnly()
    {
        return typesOnly;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest setTypesOnly( boolean typesOnly )
    {
        this.typesOnly = typesOnly;

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest addAttributes( String... attributesToAdd )
    {
        this.attributes.addAll( Arrays.asList( attributesToAdd ) );

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest removeAttribute( String attribute )
    {
        attributes.remove( attribute );

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public SearchResultDone getResultResponse()
    {
        if ( response == null )
        {
            response = new SearchResultDoneImpl( getMessageId() );
        }

        return response;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest setMessageId( int messageId )
    {
        super.setMessageId( messageId );

        return this;
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest addControl( Control control ) throws MessageException
    {
        return ( SearchRequest ) super.addControl( control );
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest addAllControls( Control[] controls ) throws MessageException
    {
        return ( SearchRequest ) super.addAllControls( controls );
    }


    /**
     * {@inheritDoc}
     */
    public SearchRequest removeControl( Control control ) throws MessageException
    {
        return ( SearchRequest ) super.removeControl( control );
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        int hash = 37;

        if ( baseDn != null )
        {
            hash = hash * 17 + baseDn.hashCode();
        }

        hash = hash * 17 + aliasDerefMode.hashCode();
        hash = hash * 17 + scope.hashCode();
        hash = hash * 17 + Long.valueOf( sizeLimit ).hashCode();
        hash = hash * 17 + timeLimit;
        hash = hash * 17 + ( typesOnly ? 0 : 1 );

        if ( attributes != null )
        {
            hash = hash * 17 + attributes.size();

            // Order doesn't matter, thus just add hashCode
            for ( String attr : attributes )
            {
                hash = hash + attr.hashCode();
            }
        }

        BranchNormalizedVisitor visitor = new BranchNormalizedVisitor();
        filterNode.accept( visitor );
        hash = hash * 17 + filterNode.toString().hashCode();
        hash = hash * 17 + super.hashCode();

        return hash;
    }


    /**
     * Checks to see if two search requests are equal. The Lockable properties
     * and the get/set context specific parameters are not consulted to
     * determine equality. The filter expression tree comparison will normalize
     * the child order of filter branch nodes then generate a string
     * representation which is comparable. For the time being this is a very
     * costly operation.
     * 
     * @param obj the object to check for equality to this SearchRequest
     * @return true if the obj is a SearchRequest and equals this SearchRequest,
     *         false otherwise
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

        SearchRequest req = ( SearchRequest ) obj;

        if ( !req.getBase().equals( baseDn ) )
        {
            return false;
        }

        if ( req.getDerefAliases() != aliasDerefMode )
        {
            return false;
        }

        if ( req.getScope() != scope )
        {
            return false;
        }

        if ( req.getSizeLimit() != sizeLimit )
        {
            return false;
        }

        if ( req.getTimeLimit() != timeLimit )
        {
            return false;
        }

        if ( req.getTypesOnly() != typesOnly )
        {
            return false;
        }

        if ( req.getAttributes() == null && attributes != null && attributes.size() > 0 )
        {
            return false;
        }

        if ( req.getAttributes() != null && attributes == null && req.getAttributes().size() > 0 )
        {
            return false;
        }

        if ( req.getAttributes() != null && attributes != null )
        {
            if ( req.getAttributes().size() != attributes.size() )
            {
                return false;
            }

            for ( String attribute : attributes )
            {
                if ( !req.getAttributes().contains( attribute ) )
                {
                    return false;
                }
            }
        }

        BranchNormalizedVisitor visitor = new BranchNormalizedVisitor();
        req.getFilter().accept( visitor );
        filterNode.accept( visitor );

        String myFilterString = filterNode.toString();
        String reqFilterString = req.getFilter().toString();

        return myFilterString.equals( reqFilterString );
    }


    /**
     * Return a string the represent a SearchRequest
     * {@inheritDoc}
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append( "    SearchRequest\n" );
        sb.append( "        baseDn : '" ).append( baseDn ).append( "'\n" );

        if ( filterNode != null )
        {
            sb.append( "        filter : '" );
            sb.append( filterNode.toString() );
            sb.append( "'\n" );
        }

        sb.append( "        scope : " );

        switch ( scope )
        {
            case OBJECT:
                sb.append( "base object" );
                break;

            case ONELEVEL:
                sb.append( "single level" );
                break;

            case SUBTREE:
                sb.append( "whole subtree" );
                break;
        }

        sb.append( '\n' );

        sb.append( "        typesOnly : " ).append( typesOnly ).append( '\n' );

        sb.append( "        Size Limit : " );

        if ( sizeLimit == 0L )
        {
            sb.append( "no limit" );
        }
        else
        {
            sb.append( sizeLimit );
        }

        sb.append( '\n' );

        sb.append( "        Time Limit : " );

        if ( timeLimit == 0 )
        {
            sb.append( "no limit" );
        }
        else
        {
            sb.append( timeLimit );
        }

        sb.append( '\n' );

        sb.append( "        Deref Aliases : " );

        switch ( aliasDerefMode )
        {
            case NEVER_DEREF_ALIASES:
                sb.append( "never Deref Aliases" );
                break;

            case DEREF_IN_SEARCHING:
                sb.append( "deref In Searching" );
                break;

            case DEREF_FINDING_BASE_OBJ:
                sb.append( "deref Finding Base Obj" );
                break;

            case DEREF_ALWAYS:
                sb.append( "deref Always" );
                break;
        }

        sb.append( '\n' );
        sb.append( "        attributes : " );

        boolean isFirst = true;

        if ( attributes != null )
        {
            for ( String attribute : attributes )
            {
                if ( isFirst )
                {
                    isFirst = false;
                }
                else
                {
                    sb.append( ", " );
                }

                sb.append( '\'' ).append( attribute ).append( '\'' );
            }
        }

        sb.append( '\n' );

        // The controls
        sb.append( super.toString() );

        return super.toString( sb.toString() );
    }
}