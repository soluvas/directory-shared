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
package org.apache.directory.shared.ldap.model.constants;


/**
 * An enum defining a list of dedicated loggers, used for debugging
 * purpose :
 * - ACI_LOG
 * - (more to come)
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public enum Loggers
{
    /** The dedicated logger for ACIs */
    ACI_LOG("aci-logger");

    /** The associated name */
    private String name;


    /**
     * Creates a new instance of LdapSecurityConstants.
     * @param name the associated name
     */
    private Loggers( String name )
    {
        this.name = name;
    }


    /**
     * @return the name associated with the constant.
     */
    public String getName()
    {
        return name;
    }
}
