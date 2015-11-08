package org.theclustermc.lib.utils.database;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of GuildsCore.
 * 
 * GuildsCore can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import com.mongodb.client.model.UpdateOptions;
import org.theclustermc.lib.exceptions.FailedSaveException;

import static com.mongodb.client.model.Filters.eq;

public interface MongoObject extends MongoLoadable {

    /**
     *
     * save toDocument() to the database
     */
    default void save(final MongoDB database) throws FailedSaveException {
        database.getPlayerdata().getCollection(getCollection()).updateOne(
                eq(getIndex(), getID()),
                toDocument(),
                new UpdateOptions().upsert(true)
        );
    }

    /**
     * Returns the key (getIndex) that is paired with the value (getID) to save/load
     * from the collection (getCollection)
     *
     * @return the key
     */
    String getIndex();

    /**
     * Returns the string representation of the name
     * of the collection to store
     * this in
     *
     * @return the collection
     */
    String getCollection();

    /**
     * Get the key to use for field in db
     *
     * @return return the key to use in save
     */
    String getID();
}
