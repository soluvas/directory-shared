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

package org.apache.directory.shared.ldap.client.api.messages;

import org.apache.directory.shared.ldap.util.LdapURL;


/**
 * Search reference protocol response message used to return referrals to the
 * client in response to a search request message.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Revision: 760984 $
 */
public class SearchResultReference extends AbstractMessage implements SearchResponse
{
    /** The list of LdapURL referrals */
    private Referral referral;
    
    /**
     * Creates a new instance of SearchResultReferenceImpl.
     */
    public SearchResultReference()
    {
        super();
    }

    
    /**
     * Gets the sequence of LdapUrls as a Referral instance.
     * 
     * @return the sequence of LdapUrls
     */
    public Referral getReferrals()
    {
        return referral;
    }


    /**
     * Sets the sequence of LdapUrls as a Referral instance.
     * 
     * @param referrals the sequence of LdapUrls
     */
    public void setReferral( Referral referral )
    {
        this.referral = referral;
    }


    /**
     * Sets the sequence of LdapUrls as a Referral instance.
     * 
     * @param urls the sequence of LdapUrls
     */
    public void addReferrals( LdapURL... urls )
    {
        if ( referral == null )
        {
            referral = new Referral();
        }
        
        referral.addLdapUrls( urls );
    }


    /**
     * Sets the sequence of LdapUrls as a Referral instance.
     * 
     * @param urls the sequence of LdapUrls
     */
    public void addReferrals( String... urls )
    {
        if ( referral == null )
        {
            referral = new Referral();
        }
        
        referral.addLdapUrls( urls );
    }


    /**
     * Removes the sequence of LdapUrls from the Referral instance.
     * 
     * @param urls the sequence of LdapUrls
     */
    public void removeReferrals( LdapURL... urls )
    {
        if ( referral == null )
        {
            referral = new Referral();
        }
        
        referral.removeLdapUrl( urls );
    }


    /**
     * Removes the sequence of LdapUrls from the Referral instance.
     * 
     * @param urls the sequence of LdapUrls
     */
    public void removeReferrals( String... urls )
    {
        if ( referral == null )
        {
            referral = new Referral();
        }
        
        referral.removeLdapUrl( urls );
    }
}