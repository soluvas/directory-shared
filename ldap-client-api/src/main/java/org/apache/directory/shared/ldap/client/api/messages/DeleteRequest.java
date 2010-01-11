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

package org.apache.directory.shared.ldap.client.api.messages;


import org.apache.directory.shared.ldap.name.LdapDN;


/**
 * Class for representing the client's delete operation request.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
public class DeleteRequest extends AbstractRequest implements RequestWithResponse, AbandonableRequest
{
    /** the DN to be deleted */
    private LdapDN targetDn;

    /** flag to tell whether to delete all the children under the target DN */
    private boolean deleteChildren;


    public DeleteRequest( LdapDN targetDn )
    {
        super();
        this.targetDn = targetDn;
    }


    public boolean isDeleteChildren()
    {
        return deleteChildren;
    }


    public void setDeleteChildren( boolean deleteChildren )
    {
        this.deleteChildren = deleteChildren;
    }


    public LdapDN getTargetDn()
    {
        return targetDn;
    }
}