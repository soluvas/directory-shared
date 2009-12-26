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
package org.apache.directory.shared.ldap.message.control;

import org.apache.directory.shared.ldap.util.StringTools;


/**
 * The control for peforming a cascade of operations like delete and modify.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev: 437007 $
 */
public class CascadeControl extends InternalAbstractControl
{
    private static final long serialVersionUID = -2356861450876343999L;
    public static final String CONTROL_OID = "1.3.6.1.4.1.18060.0.0.1";


    public CascadeControl()
    {
        super();
        setID( CONTROL_OID );
    }


    public byte[] getEncodedValue()
    {
        return StringTools.EMPTY_BYTES;
    }
}