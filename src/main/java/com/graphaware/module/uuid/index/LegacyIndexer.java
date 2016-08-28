/*
 * Copyright (c) 2013-2016 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.module.uuid.index;

import com.graphaware.module.uuid.UuidConfiguration;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

/**
 * Legacy Index implementation for indexing and finding nodes and relationships assigned a UUID.
 */
public class LegacyIndexer implements UuidIndexer {

    private final GraphDatabaseService database;
    private final UuidConfiguration configuration;

    public LegacyIndexer(GraphDatabaseService database, UuidConfiguration configuration) {
        this.database = database;
        this.configuration = configuration;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void indexNode(Node node) {
        database.index().forNodes(configuration.getUuidIndex()).add(node, configuration.getUuidProperty(), node.getProperty(configuration.getUuidProperty()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getNodeByUuid(String uuid) {
        return database.index().forNodes(configuration.getUuidIndex()).get(configuration.getUuidProperty(), uuid).getSingle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNodeFromIndex(Node node) {
        database.index().forNodes(configuration.getUuidIndex()).remove(node, configuration.getUuidProperty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void indexRelationship(Relationship relationship) {
        database.index().forRelationships(configuration.getUuidRelationshipIndex()).add(relationship, configuration.getUuidProperty(), relationship.getProperty(configuration.getUuidProperty()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteRelationshipFromIndex(Relationship relationship) {
        database.index().forRelationships(configuration.getUuidRelationshipIndex()).remove(relationship, configuration.getUuidProperty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Relationship getRelationshipByUuid(String uuid) {
        return database.index().forRelationships(configuration.getUuidRelationshipIndex()).get(configuration.getUuidProperty(), uuid).getSingle();
    }

}
