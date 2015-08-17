/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.kaiserpfalzEdv.office.commons.client.mock;

import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.office.commons.data.Entity;
import de.kaiserpfalzEdv.office.commons.paging.PageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 09:24
 */
public class Store<V extends Entity, K> implements Serializable {
    private static final long serialVersionUID = 5753447456382462359L;

    private static final Logger LOG = LoggerFactory.getLogger(Store.class);

    private final HashMap<String, Object> store = new HashMap<>(100);

    public ArrayList<V> findAll() {
        ArrayList<V> result = new ArrayList<>(store.size());

        store.values().forEach(
                v -> {
                    result.add((V) v);
                }
        );

        return result;
    }

    public Page<V> findAll(Pageable pagerequest) {
        ArrayList<V> all = findAll();
        int listEnd = pagerequest.getOffset() + pagerequest.getPageSize();
        listEnd = Integer.min(all.size(), listEnd);

        List<V> data = all.subList(pagerequest.getOffset(), listEnd);

        return new PageImpl<>(pagerequest, all.size(), data);
    }


    public V save(final V entry) {
        store.put(entry.getId().toString(), entry);

        return entry;
    }


    public V findOne(final String id) {
        return (V) store.get(UUID.fromString(id));
    }


    public void delete(final String id) {
        store.remove(id);
    }

    public void delete(final V entry) {
        store.remove(entry.getId().toString());
    }
}
