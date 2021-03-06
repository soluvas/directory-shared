/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.directory.shared.ldap.model.cursor;


import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import org.apache.directory.shared.i18n.I18n;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A simple implementation of a Cursor on a {@link Set}.  Optionally, the
 * Cursor may be limited to a specific range within the list.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @param <E> The element on which this cursor will iterate
 */
public class SetCursor<E> extends AbstractCursor<E>
{
    /** A dedicated log for cursors */
    private static final Logger LOG_CURSOR = LoggerFactory.getLogger( "CURSOR" );

    /** Speedup for logs */
    private static final boolean IS_DEBUG = LOG_CURSOR.isDebugEnabled();

    /** The inner Set */
    private final E[] set;

    /** The associated comparator */
    private final Comparator<E> comparator;

    /** The current position in the list */
    private int index = -1;


    /**
     * Creates a new SetCursor.
     *
     * As with all Cursors, this SetCursor requires a successful return from
     * advance operations (next() or previous()) to properly return values
     * using the get() operation.
     *
     * @param comparator an optional comparator to use for ordering
     * @param set the Set this StCursor operates on
     */
    public SetCursor( Comparator<E> comparator, Set<E> set )
    {
        if ( set == null )
        {
            set = Collections.EMPTY_SET;
        }

    	if ( IS_DEBUG )
    	{
    		LOG_CURSOR.debug( "Creating SetCursor {}", this );
    	}
    	
        this.comparator = comparator;
        this.set = ( E[] ) set.toArray();
    }


    /**
     * Creates a new SetCursor
     *
     * As with all Cursors, this SetCursor requires a successful return from
     * advance operations (next() or previous()) to properly return values
     * using the get() operation.
     *
     * @param set the Set this SetCursor operates on
     */
    public SetCursor( Set<E> set )
    {
        this( null, set );
    }


    /**
     * Creates a new SetCursor without any elements.
     */
    public SetCursor()
    {
        this( null, Collections.EMPTY_SET );
    }


    /**
     * Creates a new SetCursor without any elements. We also provide 
     * a comparator.
     * 
     * @param comparator The comparator to use for the <E> elements
     */
    @SuppressWarnings("unchecked")
    public SetCursor( Comparator<E> comparator )
    {
        this( comparator, Collections.EMPTY_SET );
    }


    /**
     * {@inheritDoc}
     */
    public boolean available()
    {
        return ( index >= 0 ) && ( index < set.length );
    }


    /**
     * {@inheritDoc}
     */
    public void before( E element ) throws Exception
    {
        checkNotClosed( "before()" );

        if ( comparator == null )
        {
            throw new IllegalStateException();
        }

        // handle some special cases
        if ( set.length == 0 )
        {
            return;
        }
        else if ( set.length == 1 )
        {
            if ( comparator.compare( element, set[0] ) <= 0 )
            {
                beforeFirst();
            }
            else
            {
                afterLast();
            }
        }

        throw new UnsupportedOperationException( I18n.err( I18n.ERR_02008_LIST_MAY_BE_SORTED ) );
    }


    /**
     * {@inheritDoc}
     */
    public void after( E element ) throws Exception
    {
        checkNotClosed( "after()" );

        if ( comparator == null )
        {
            throw new IllegalStateException();
        }

        // handle some special cases
        if ( set.length == 0 )
        {
            return;
        }
        else if ( set.length == 1 )
        {
            if ( comparator.compare( element, set[0] ) >= 0 )
            {
                afterLast();
            }
            else
            {
                beforeFirst();
            }
        }

        throw new UnsupportedOperationException( I18n.err( I18n.ERR_02008_LIST_MAY_BE_SORTED ) );
    }


    /**
     * {@inheritDoc}
     */
    public void beforeFirst() throws Exception
    {
        checkNotClosed( "beforeFirst()" );
        this.index = -1;
    }


    /**
     * {@inheritDoc}
     */
    public void afterLast() throws Exception
    {
        checkNotClosed( "afterLast()" );
        this.index = set.length;
    }


    /**
     * {@inheritDoc}
     */
    public boolean first() throws Exception
    {
        checkNotClosed( "first()" );

        if ( set.length > 0 )
        {
            index = 0;

            return true;
        }

        return false;
    }


    /**
     * {@inheritDoc}
     */
    public boolean last() throws Exception
    {
        checkNotClosed( "last()" );

        if ( set.length > 0 )
        {
            index = set.length - 1;

            return true;
        }

        return false;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFirst() throws Exception
    {
        checkNotClosed( "isFirst()" );

        return ( set.length > 0 ) && ( index == 0 );
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLast() throws Exception
    {
        checkNotClosed( "isLast()" );

        return ( set.length > 0 ) && ( index == set.length - 1 );
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAfterLast() throws Exception
    {
        checkNotClosed( "isAfterLast()" );
        return index == set.length;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBeforeFirst() throws Exception
    {
        checkNotClosed( "isBeforeFirst()" );
        return index == -1;
    }


    /**
     * {@inheritDoc}
     */
    public boolean previous() throws Exception
    {
        checkNotClosed( "previous()" );

        // if parked at -1 we cannot go backwards
        if ( index == -1 )
        {
            return false;
        }

        // if the index moved back is still greater than or eq to start then OK
        if ( index - 1 >= 0 )
        {
            index--;

            return true;
        }

        // if the index currently less than or equal to start we need to park it at -1 and return false
        if ( index <= 0 )
        {
            index = -1;

            return false;
        }

        if ( set.length <= 0 )
        {
            index = -1;
        }

        return false;
    }


    /**
     * {@inheritDoc}
     */
    public boolean next() throws Exception
    {
        checkNotClosed( "next()" );

        // if parked at -1 we advance to the start index and return true
        if ( ( set.length > 0 ) && ( index == -1 ) )
        {
            index = 0;

            return true;
        }

        // if the index plus one is less than the end then increment and return true
        if ( ( set.length > 0 ) && ( index + 1 < set.length ) )
        {
            index++;

            return true;
        }

        // if the index plus one is equal to the end then increment and return false
        if ( ( set.length > 0 ) && ( index + 1 == set.length ) )
        {
            index++;

            return false;
        }

        if ( set.length <= 0 )
        {
            index = set.length;
        }

        return false;
    }


    /**
     * {@inheritDoc}
     */
    public E get() throws Exception
    {
        checkNotClosed( "get()" );

        if ( ( index < 0 ) || ( index >= set.length ) )
        {
            throw new IOException( I18n.err( I18n.ERR_02009_CURSOR_NOT_POSITIONED ) );
        }

        return set[index];
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws Exception
    {
    	if ( IS_DEBUG )
    	{
    		LOG_CURSOR.debug( "Closing ListCursor {}", this );
    	}
    	
        super.close();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void close( Exception cause ) throws Exception
    {
    	if ( IS_DEBUG )
    	{
    		LOG_CURSOR.debug( "Closing ListCursor {}", this );
    	}
    	
        super.close( cause );
    }
}
