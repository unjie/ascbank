/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ascbank.solr.repository;

import org.springframework.data.solr.core.query.Field;

/**
 * @author Christoph Strobl
 */
public enum SolrSearchableFields implements Field {

	ID(SearchableArticle.ID_FIELD), MENUID(SearchableArticle.MENUID_FIELD), CONTEXT(SearchableArticle.CONTEXT_FIELD), CLICKS(SearchableArticle.CLICKS_FIELD), UPLOADTIME(SearchableArticle.UPLOADTIME_FIELD);

	private final String fieldName;

	private SolrSearchableFields(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String getName() {
		return fieldName;
	}

}
