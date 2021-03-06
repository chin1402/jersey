/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.jersey.examples.shortener.webapp.resource;

import java.util.Collections;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.glassfish.jersey.examples.shortener.webapp.domain.ShortenedLink;
import org.glassfish.jersey.examples.shortener.webapp.service.ShortenerService;
import org.glassfish.jersey.examples.shortener.webapp.validation.ShortenLink;
import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

/**
 * Resource responsible of providing a form to create shortened link (see {@link #form()}) and shortening posted link
 * (see {@link #createLink(String)}). Client is shown {@code error-404} page if the provided link is
 * not valid (see {@link ErrorTemplate} and {@link ShortenLink}).
 *
 * @author Michal Gajdos
 */
@Path("/")
public class ShortenerResource {

    @NotNull
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces("text/html")
    public Viewable form() {
        return new Viewable("/form",
                Collections.singletonMap("greeting", "Link Shortener"));
    }

    @POST
    @Produces("text/html")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Template(name = "/short-link")
    @ErrorTemplate(name = "/error-form")
    @Valid
    public ShortenedLink createLink(@ShortenLink @FormParam("link") final String link) {
        return ShortenerService.shortenLink(uriInfo.getBaseUri(), link);
    }
}
