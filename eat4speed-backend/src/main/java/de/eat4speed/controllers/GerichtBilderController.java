package de.eat4speed.controllers;



import de.eat4speed.multipart.MultipartBody;
import de.eat4speed.services.interfaces.IGerichtService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Path("/GerichtBilder")
@RegisterRestClient
public class GerichtBilderController {

    private final String path = "src/main/resources/Bilder/gerichtBilder/";

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

    //    @Produces(MediaType.APPLICATION_OCTET_STREAM)

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("image/png")
    @Path("/getBild/{id}")
    public byte[] getBildByID(@PathParam("id")int id) throws IOException {
        final String picturePath = projectDirectoryNoTarget+path+"Bild"+id+".png";

        byte[] content;

        File pictureFile = new File(picturePath);

        System.out.println(picturePath);

        if(pictureFile.exists() && !pictureFile.isDirectory())
        {
            java.nio.file.Path path = Paths.get(picturePath);

            content = Files.readAllBytes(path);
        }
        else
        {
            content = null;
        }

        //return savedPicture.file;
        return content;
    }


    private void writeFile(byte[] content, String filename) throws IOException
    {
        System.out.println(filename);

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
