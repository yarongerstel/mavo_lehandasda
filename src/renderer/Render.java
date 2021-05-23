package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.MissingFormatArgumentException;
import java.util.MissingResourceException;

public class Render {
    private ImageWriter _imageWriter = null;
    private Camera _camera = null;
    private RayTracerBase _rayTracerBase = null;

    /**
     *
     * @param imageWriter
     * @return this render with the ImageWriter we get
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    /**
     *
     * @param camera
     * @return this render with the camera we get
     */
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    /**
     *
     * @param rayTracer
     * @return this render with the RayTracerBase we get
     */
    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracerBase = rayTracer;
        return this;
    }

    /**
     * right the pixel one by one
     */
    public void renderImage() {
        try {
            if (_imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (_camera == null) {
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
            }
            if (_rayTracerBase == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

            //rendering the image
            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
                    Color pixelColor = _rayTracerBase.traceRay(ray);
                    _imageWriter.writePixel(j, i, pixelColor);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }

    /**
     * make grid to the picture
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color) {
        if (_imageWriter == null) {
            throw new MissingFormatArgumentException("_imageWriter null!!");
        }
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (j % interval == 0 || i % interval == 0)
                    _imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * check if _imageWriter null else make the image
     */
    public void writeToImage() {
        if (_imageWriter == null) {
            throw new MissingFormatArgumentException("_imageWriter null!!");
        }
        _imageWriter.writeToImage();
    }
}
