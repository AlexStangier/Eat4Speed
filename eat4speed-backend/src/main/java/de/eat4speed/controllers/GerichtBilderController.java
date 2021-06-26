package de.eat4speed.controllers;


import de.eat4speed.multipart.MultipartBody;
import de.eat4speed.services.interfaces.IGerichtService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Path("/GerichtBilder")
public class GerichtBilderController {

    private final String imageDatabasePath;

    @Inject
    IGerichtService gerichtService;

    public GerichtBilderController() {
        String mavenProjectDirectory = System.getProperty("user.dir").replace("target", "");
        if (!mavenProjectDirectory.endsWith("/")) {
            mavenProjectDirectory += "/";
        }
        this.imageDatabasePath = mavenProjectDirectory + "src/main/resources/Bilder/gerichtBilder/";
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/upload")
    @RolesAllowed("restaurant")
    public Response upload(@MultipartForm MultipartBody data) throws IOException {

        if (data == null || !data.fileName.matches("^Bild[0-9]+$")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String picturePath = this.imageDatabasePath + data.fileName + ".png";

        // IMPORTANT: Try to write the file BEFORE updating the entry in the database to prevent dangling
        // references in the case of an IOException.
        File pictureFile = new File(picturePath);
        FileUtils.copyInputStreamToFile(data.file, pictureFile);

        // Check if the uploaded file is actually an image
        BufferedImage image = ImageIO.read(pictureFile);
        if (image == null) {
            boolean deleteSuccess = pictureFile.delete();
            if (!deleteSuccess) {
                throw new IOException("Could not delete invalid image!");
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        int id = Integer.parseInt(data.fileName.replace("Bild",""));
        gerichtService.updatePicturePath(picturePath, id);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Produces("image/png")
    @Path("/getBild/{id}")
    @PermitAll
    public Response getBildByID(@PathParam("id") int id) throws IOException {

        File pictureFile = new File(this.imageDatabasePath + "Bild" + id + ".png");
        if (!pictureFile.exists()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        InputStream pictureStream = new FileInputStream(pictureFile);
        byte[] content = IOUtils.toByteArray(pictureStream);
        return Response.status(Response.Status.OK).entity(content).build();
    }
}
