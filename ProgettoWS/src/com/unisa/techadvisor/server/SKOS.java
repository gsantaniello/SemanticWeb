package com.unisa.techadvisor.server;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Vocabulary definition for the 
 * <a href="http://www.w3.org/2009/08/skos-reference/skos.html">W3C SKOS Recommendation</a>.
 */
public class SKOS {
	/**
	 * The RDF model that holds the SKOS entities
	 */
	public static Model m = ModelFactory.createDefaultModel();
	/**
	 * The namespace of the SKOS vocabulary as a string
	 */
	public static final String NS = "http://www.w3.org/2004/02/skos/core#";
	/**
	 * Returns the namespace of the SKOS schema as a string
	 * @return the namespace of the SKOS schema
	 */
	public static String getURI() {
		return NS;
	}
	/**
	 * The namespace of the SKOS vocabulary
	 */
	public static final Resource NAMESPACE = m.createResource( NS );
	/* ##########################################################
	 * Defines SKOS Classes
	   ########################################################## */
	public static final Resource Concept = m.createResource( NS + "Concept");
	public static final Resource ConceptScheme = m.createResource( NS + "ConceptScheme");
	public static final Resource Collection = m.createResource(NS + "Collection");
	public static final Resource OrderedCollection = m.createResource( NS + "OrderedCollection");
	/* ##########################################################
	 * Defines SKOS Properties
	   ########################################################## */
	// SKOS lexical label properties
	public static final Property prefLabel = m.createProperty( NS + "prefLabel");
	public static final Property altLabel = m.createProperty( NS + "altLabel");
	public static final Property hiddenLabel = m.createProperty( NS + "hiddenLabel");
	// SKOS documentation properties
	public static final Property definition = m.createProperty( NS + "definition");
	public static final Property note = m.createProperty( NS + "note");
	public static final Property scopeNote = m.createProperty( NS + "scopeNote");
	public static final Property historyNote = m.createProperty( NS + "historyNote");
	public static final Property changeNote = m.createProperty( NS + "changeNote");
	public static final Property editorialNote = m.createProperty( NS + "editorialNote");
	public static final Property example = m.createProperty( NS + "example");
	// SKOS notation properties
	public static final Property notation = m.createProperty( NS + "notation");
	// SKOS semantic relations properties
	public static final Property semanticRelation = m.createProperty( NS + "semanticRelation");
	public static final Property broaderTransitive = m.createProperty( NS + "broaderTransitive");
	public static final Property broader = m.createProperty( NS + "broader");
	public static final Property narrowerTransitive = m.createProperty( NS + "narrowerTransitive");
	public static final Property narrower = m.createProperty( NS + "narrower");
	public static final Property related = m.createProperty( NS + "related");
	// SKOS mapping properties
	public static final Property mappingRelation = m.createProperty( NS + "mappingRelation");
	public static final Property exactMatch = m.createProperty( NS + "exactMatch");
	public static final Property closeMatch = m.createProperty( NS + "closeMatch");
	public static final Property broadMatch = m.createProperty( NS + "broadMatch");
	public static final Property narrowMatch = m.createProperty( NS + "narrowMatch");
	public static final Property relatedMatch = m.createProperty( NS + "relatedMatch");
	// SKOS concept scheme properties
	public static final Property inScheme = m.createProperty( NS + "inScheme");
	public static final Property hasTopConcept = m.createProperty( NS + "hasTopConcept");
	public static final Property topConceptOf = m.createProperty( NS + "topConceptOf");
	// SKOS collection properties
	public static final Property member = m.createProperty( NS + "member");
	public static final Property memberList = m.createProperty( NS + "memberList");
}