package de.eat4speed.controllers;


import de.eat4speed.multipart.MultipartBody;
import de.eat4speed.services.interfaces.IGerichtService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/GerichtBilder")
@RegisterRestClient
public class GerichtBilderController {

    private final String path = "src\\main\\java\\de\\eat4speed\\gerichtBilder\\";

    private final String projectDirectory = System.getProperty("user.dir");

    private final String projectDirectoryNoTarget = projectDirectory.replace("target", "");

    @Inject
    IGerichtService gerichtService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/upload")
    public Response upload(@MultipartForm MultipartBody data) throws IOException {

        byte[] picture = IOUtils.toByteArray(data.file);

        String fileName = data.fileName;

        int id = Integer.parseInt(fileName.replace("Bild",""));

        gerichtService.updatePicturePath(path+fileName+".png", id);

        writeFile(picture,projectDirectoryNoTarget + path+fileName+".png");

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("image/png")
    @Path("/getBild/{id}")
    public byte[] getBildByID(@PathParam("id")int id) throws IOException {
        final String picturePath = projectDirectoryNoTarget+path+"Bild"+id+".png";

        byte[] content;

        File pictureFile = new File(picturePath);

        if(pictureFile.exists() && !pictureFile.isDirectory())
        {
            java.nio.file.Path path = Paths.get(picturePath);

            content = Files.readAllBytes(path);
        }
        else
        {
            content = null;
        }

        return content;
    }


    private void writeFile(byte[] content, String filename) throws IOException
    {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
    }



}
