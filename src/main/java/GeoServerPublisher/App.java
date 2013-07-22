package GeoServerPublisher;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.encoder.GSLayerGroupEncoder23;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;

import java.io.File;

import static java.lang.System.out;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String workspace = "kiriwatsan";
        String path = "/home/sachin/Work/export/image/";
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher("http://localhost:8090/geoserver", "admin", "geoserver");
        GSLayerGroupEncoder23 groupWriter = new GSLayerGroupEncoder23();
        for (File f : new File(path).listFiles()) {
            String img = f.getAbsolutePath();
            if (img.toLowerCase().endsWith(".tif") || img.toLowerCase().endsWith(".tiff")) {
                String name = f.getName().substring(0, f.getName().indexOf("."));
                out.println(name);
                try {
                    publisher.publishExternalGeoTIFF(workspace, name, f, name, "EPSG:4326", GSResourceEncoder.ProjectionPolicy.REPROJECT_TO_DECLARED, "raster");
                    out.println("Published:" + f.getName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //creating layer group
                groupWriter.addLayer(workspace + ":" + name);
            }
        }
        publisher.createLayerGroup(workspace, "kiribati_imagery", groupWriter);
        out.println("End");
    }
}