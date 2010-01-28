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



/**
 * Control implementation.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev: 762512 $
 */
public abstract class AbstractControl implements Control
{
    /** Unique object identifier for this control */
    private String oid;

    /** Flag for control criticality */
    private boolean isCritical;
    
    /** The encoded value */
    private byte[] value;


    /**
     * Creates a new instance of AbstractControl.
     *
     * @param oid The control's OID
     */
    public AbstractControl( String oid )
    {
        this.oid = oid;
        value = null;
        isCritical = false;
    }
    
    
    /**
     * Creates a new instance of AbstractControl.
     *
     * @param oid The control's OID
     * @param isCritical A flag telling if the control is critical or not
     * @param value The encoded value
     */
    public AbstractControl( String oid, boolean isCritical, byte[] value )
    {
        this.oid = oid;
        this.value = value;
        this.isCritical = isCritical;
    }
    
    
    // ------------------------------------------------------------------------
    // Control Interface Method Implementations
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean isCritical()
    {
        return this.isCritical;
    }


    /**
     * {@inheritDoc}
     */
    public void setCritical( boolean isCritical )
    {
        this.isCritical = isCritical;
    }


    /**
     * {@inheritDoc}
     */
    public void setOid( String oid )
    {
        this.oid = oid;
    }


    /**
     * {@inheritDoc}
     */
    public String getOid()
    {
        return this.oid;
    }
    
    
    /**
     * {@inheritDoc}
     */
    public byte[] getValue()
    {
        return value;
    }


    /**
     * {@inheritDoc}
     */
    public void setValue( byte[] value )
    {
        this.value = value;
    }
    
    
    public boolean hasValue()
    {
        return value != null;
    }
}