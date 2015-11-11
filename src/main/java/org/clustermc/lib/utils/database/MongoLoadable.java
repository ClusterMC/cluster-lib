package org.clustermc.lib.utils.database;

import org.bson.Document;
import org.clustermc.lib.exceptions.InvalidMongoDocException;

public interface MongoLoadable {

    /**
     * Call super.toDocument() to get the superclasses
     * document, then .append() to append the extra info
     *
     * @return the document for this instance
     */
    Document toDocument();

    /**
     * Load the item
     *
     * @param doc the document to be loaded from
     */
    void load(Document doc) throws InvalidMongoDocException;

}
