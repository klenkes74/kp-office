/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.service;

import de.kaiserpfalzEdv.commons.paging.Page;
import de.kaiserpfalzEdv.office.address.PersonDTO;
import de.kaiserpfalzEdv.office.projects.ProjectBaseData;
import de.kaiserpfalzEdv.office.projects.ProjectDetailData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Path("/project")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class ProjectResource {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectResource.class);

    @Context
    private UriInfo uriInfo;

    @GET
    public Page<ProjectBaseData> listProjects() {
        Page<ProjectBaseData> result = new Page<>();

        List<PersonDTO> responsible = new ArrayList<>(1);
        responsible.add(new PersonDTO(UUID.randomUUID(), "1", "Roland T. Lichti"));
        List<PersonDTO> customer = new ArrayList<>(1);
        customer.add(new PersonDTO(UUID.randomUUID(), "9231", "ITConcepts Professional GmhH"));

        Map<String, List<PersonDTO>> contacts = new HashMap<>(2);
        contacts.put("customer", customer);
        contacts.put("responsible", responsible);


        result.add(new ProjectBaseData(uriInfo.getBaseUri().toString(), UUID.randomUUID(), "I'14-1", "KP-Central", contacts));
        result.setStart(0);
        result.setTotal(1);

        return result;
    }


    @GET
    @Path("/{number}")
    public ProjectDetailData getProjectOverview(@PathParam("number") final String number) {
        List<PersonDTO> responsible = new ArrayList<>(1);
        responsible.add(new PersonDTO(UUID.randomUUID(), "1", "Roland T. Lichti"));
        List<PersonDTO> customer = new ArrayList<>(1);
        customer.add(new PersonDTO(UUID.randomUUID(), "9231", "ITConcepts Professional GmhH"));

        Map<String, List<PersonDTO>> contacts = new HashMap<>(2);
        contacts.put("customer", customer);
        contacts.put("responsible", responsible);

        ProjectDetailData result = new ProjectDetailData(uriInfo.getBaseUri().toString(), UUID.randomUUID(), "I'14-1", "KP-Central", contacts);

        return result;
    }
}
