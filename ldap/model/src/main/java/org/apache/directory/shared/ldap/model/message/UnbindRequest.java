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
 * Unbind protocol request message used to end a client session.
 * 
 * @author <a href="mailto:dev@directory.apache.org"> Apache Directory Project</a>
 */
public interface UnbindRequest extends Request
{
    /** Unbind request protocol message type */
    MessageTypeEnum TYPE = MessageTypeEnum.UNBIND_REQUEST;


    /**
     * {@inheritDoc}
     */
    UnbindRequest setMessageId( int messageId );


    /**
     * {@inheritDoc}
     */
    UnbindRequest addControl( Control control ) throws MessageException;


    /**
     * {@inheritDoc}
     */
    UnbindRequest addAllControls( Control[] controls ) throws MessageException;


    /**
     * {@inheritDoc}
     */
    UnbindRequest removeControl( Control control ) throws MessageException;
}
