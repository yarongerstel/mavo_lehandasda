package elements;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000);

        scene.geometries.add( //
                new Sphere(50, new Point3D(0, 0, -50)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25, new Point3D(0, 0, -50)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(2500, 2500).setDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(400, new Point3D(-950, -900, -1000)) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
                new Sphere(200, new Point3D(-950, -900, -1000)) //
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(30, new Point3D(60, 50, -50)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow1", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void myPicture() {
        Camera camera = new Camera(new Point3D(150, 400, 100), new Vector(0, -1,-0.2 ), new Vector(0, -0.2,1 )) //
                .setViewPlaneSize(200, 200).setDistance(150);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Plane(new Point3D(0, 1, 300), new Point3D(0, 0, 300),
                        new Point3D(1, 0, 300))
                        .setEmission(new Color(144,185,234))
                        .setMaterial(new Material().setKt(1)), // ritgh

                new Polygon(new Point3D(0, 0, 0), new Point3D(0, 0, 200),
                        new Point3D(0, 400, 200),new Point3D(0, 400, 0))
                        .setEmission(new Color(127,199,190))
                        .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(60)), // ritgh

                new Polygon(new Point3D(300, 0, 0), new Point3D(300, 0, 200),
                        new Point3D(300, 400, 200),new Point3D(300, 400, 0))
                        .setEmission(new Color(223,160,63))
                        .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(60)), // left

                new Polygon(new Point3D(0, 0, 200), new Point3D(300, 0, 200),
                        new Point3D(300, 400, 200),new Point3D(0, 400, 200))
                        .setEmission(new Color(219,93,56))//
                        .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(60)), // up

                new Polygon(new Point3D(0, 0, 0), new Point3D(300, 0, 0),
                        new Point3D(300, 400, 0),new Point3D(0, 400, 0))
                        .setEmission(new Color(0,0,0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //flor

                /////////////////////////////back woll/////////////////////////////////////////
                new Polygon(new Point3D(0, 0, 0), new Point3D(0, 0, 200),
                        new Point3D(100, 0, 200),new Point3D(100,0,0))
                        .setEmission(new Color(100,100,10)),//right

                new Polygon(new Point3D(300, 0, 0), new Point3D(300, 0, 200),
                        new Point3D(200, 0, 200),new Point3D(200,0,0))
                        .setEmission(new Color(100,100,10)),//right

                new Polygon(new Point3D(200, 0, 200), new Point3D(100, 0, 200),
                        new Point3D(100, 0, 170),new Point3D(200,0,170))
                        .setEmission(new Color(100,100,10)),//up

                new Polygon(new Point3D(200, 0, 140), new Point3D(100, 0, 140),
                        new Point3D(100, 0, 0),new Point3D(200,0,0))
                        .setEmission(new Color(100,100,10)),//down


                new Triangle(new Point3D(50, 50, 0), new Point3D(50, 100, 0), new Point3D(100, 75, 0)) //
                        .setEmission(new Color(40, 40, 90))//
                        .setMaterial(new Material().setKr(1)), //

                new Triangle(new Point3D(75, 75, 50), new Point3D(50, 100, 0), new Point3D(100, 75, 0)) //
                        .setEmission(new Color(40, 40, 90))//
                        .setMaterial(new Material().setKr(1)), //

                new Triangle(new Point3D(75, 75, 50), new Point3D(50, 100, 0), new Point3D(50, 50, 0)) //
                        .setEmission(new Color(40, 40, 90))//
                        .setMaterial(new Material().setKr(1)), //

                new Triangle(new Point3D(75, 75, 50), new Point3D(50, 50, 0), new Point3D(100, 75, 0)) //
                        .setEmission(new Color(40, 40, 90))//
                        .setMaterial(new Material().setKr(1)), //

                /////////////------------------table-------------------/////////
                new Polygon( new Point3D(100,200 , 40), new Point3D(100, 120, 40),
                        new Point3D(200, 120, 40), new Point3D(200, 200, 40)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.7)),//up- midel

                new Polygon( new Point3D(100,210 , 40), new Point3D(100, 200, 40),
                        new Point3D(200, 200, 40), new Point3D(200, 210, 40)) //
                        .setEmission(new Color(110,80,64)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-front

                new Polygon( new Point3D(210,210 , 40), new Point3D(210, 210, 35),
                        new Point3D(90, 210, 35), new Point3D(90, 210, 40)) //
                        .setEmission(new Color(110,80,64))  //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-front-down

                new Polygon( new Point3D(200,120 , 40), new Point3D(200, 110, 40),
                        new Point3D(100, 110, 40), new Point3D(100, 120, 40)) //
                        .setEmission(new Color(110,80,64)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-back

                new Polygon( new Point3D(210,110 , 40), new Point3D(210, 110, 35),
                        new Point3D(90, 110, 35), new Point3D(90, 110, 40)) //
                        .setEmission(new Color(110,80,64))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-back-down

                new Polygon( new Point3D(200,210 , 40), new Point3D(200, 110, 40)
                        , new Point3D(210, 110, 40), new Point3D(210, 210, 40)) //
                        .setEmission(new Color(110,80,64))  //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-left

                new Polygon( new Point3D(210,210 , 40), new Point3D(210, 210, 35)
                        , new Point3D(210, 110, 35), new Point3D(210, 110, 40)) //
                        .setEmission(new Color(110,80,64))  //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-left-down

                new Polygon( new Point3D(90,210 , 40), new Point3D(90, 110, 40)
                        , new Point3D(100, 110, 40), new Point3D(100, 210, 40)) //
                        .setEmission(new Color(110,80,64)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-right

                new Polygon( new Point3D(90,210 , 40), new Point3D(90, 110, 40)
                        , new Point3D(90, 110, 35), new Point3D(90, 210, 35)) //
                        .setEmission(new Color(110,80,64))  //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-right-down
                //------------------------------------------foots------------------------------
                //---------left_front----------
                new Polygon( new Point3D(210,210 , 40), new Point3D(200, 210, 40)
                        , new Point3D(200, 210, 0), new Point3D(210, 210, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//front

                new Polygon( new Point3D(200,210 , 40), new Point3D(200, 200, 40)
                        , new Point3D(200, 200, 0), new Point3D(200, 210, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30)),//right

                new Polygon( new Point3D(210,210 , 40), new Point3D(210, 200, 40)
                        , new Point3D(210, 200, 0), new Point3D(210, 210, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//left

                new Polygon( new Point3D(210,200 , 40), new Point3D(200, 200, 40)
                        , new Point3D(200, 200, 0), new Point3D(210, 200, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//beck
                //---------right_front----------
                new Polygon( new Point3D(100,210 , 40), new Point3D(90, 210, 40)
                        , new Point3D(90, 210, 0), new Point3D(100, 210, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//front

                new Polygon( new Point3D(90,210 , 40), new Point3D(90, 200, 40)
                        , new Point3D(90, 200, 0), new Point3D(90, 210, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30)),//right

                new Polygon( new Point3D(100,210 , 40), new Point3D(100, 200, 40)
                        , new Point3D(100, 200, 0), new Point3D(100, 210, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//left

                new Polygon( new Point3D(100,200 , 40), new Point3D(90, 200, 40)
                        , new Point3D(90, 200, 0), new Point3D(100, 200, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//beck
                //---------right_beck----------
                new Polygon( new Point3D(100,120 , 40), new Point3D(90, 120, 40)
                        , new Point3D(90, 120, 0), new Point3D(100, 120, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//front

                new Polygon( new Point3D(90,120 , 40), new Point3D(90, 110, 40)
                        , new Point3D(90, 110, 0), new Point3D(90, 120, 0)) //
                        .setEmission(new Color(75,69,55))//
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30)),//right

                new Polygon( new Point3D(100,120 , 40), new Point3D(100, 110, 40)
                        , new Point3D(100, 110, 0), new Point3D(100, 120, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//left

                new Polygon( new Point3D(100,110 , 40), new Point3D(90, 110, 40)
                        , new Point3D(90, 110, 0), new Point3D(100, 110, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//beck
                //---------left_beck----------
                new Polygon( new Point3D(210,120 , 40), new Point3D(200, 120, 40)
                        , new Point3D(200, 120, 0), new Point3D(210, 120, 0)) //
                        .setEmission(new Color(75,69,55))//
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//front

                new Polygon( new Point3D(200,120 , 40), new Point3D(200, 110, 40)
                        , new Point3D(200, 110, 0), new Point3D(200, 120, 0)) //
                        .setEmission(new Color(75,69,55))//
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30)),//right

                new Polygon( new Point3D(210,120 , 40), new Point3D(210, 110, 40)
                        , new Point3D(210, 110, 0), new Point3D(210, 120, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//left

                new Polygon( new Point3D(210,110 , 40), new Point3D(200, 110, 40)
                        , new Point3D(200, 110, 0), new Point3D(210, 110, 0)) //
                        .setEmission(new Color(75,69,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//beck



                new Sphere(15, new Point3D(new Point3D(150, 130, 205))) //
                        .setEmission(new Color(java.awt.Color.yellow)) //
                        .setMaterial(new Material().setKs(0.2).setShininess(30).setKt(0.7)),

                new Sphere(3, new Point3D(new Point3D(150, 130, 198))) //
                        .setEmission(new Color(java.awt.Color.WHITE)) //
                        .setMaterial(new Material().setKs(0.2).setShininess(30).setKt(1)),

                new Sphere(15, new Point3D(55, 190, 20)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(1)));
//

        scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point3D(70, 400, 100), new Vector(-70, -400, -100)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(244,244,214),new Point3D(150,130,198)).setKl(4E-5).setKq(2E-7));
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.YELLOW),new Vector(1,1,-1)));

        ImageWriter imageWriter = new ImageWriter("project-1-ss", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void myPicture2() {
        Camera camera = new Camera(new Point3D(150, 400, 100), new Vector(0, -1, -0.2), new Vector(0, -0.2, 1)) //
                .setViewPlaneSize(200, 200).setDistance(150);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                /////////////////////////////---The sky---/////////////////////////////
                new Plane(new Point3D(0, 1, 300), new Point3D(0, 0, 300), new Point3D(1, 0, 300))
                        .setEmission(new Color(144, 185, 234))
                        .setMaterial(new Material().setKt(1)),


                /////////////////////////////---The walls room---/////////////////////////////
                new Polygon(new Point3D(0, 0, 0), new Point3D(0, 0, 200),
                        new Point3D(0, 400, 200), new Point3D(0, 400, 0))
                        .setEmission(new Color(127, 199, 190))
                        .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(60)), // right

                new Polygon(new Point3D(300, 0, 0), new Point3D(300, 0, 200),
                        new Point3D(300, 400, 200), new Point3D(300, 400, 0))
                        .setEmission(new Color(223, 160, 63))
                        .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(60)), // left

                new Polygon(new Point3D(0, 0, 200), new Point3D(300, 0, 200),
                        new Point3D(300, 400, 200), new Point3D(0, 400, 200))
                        .setEmission(new Color(219, 93, 56))//
                        .setMaterial(new Material().setKd(0.1).setKs(0.5).setShininess(60)), // up

                new Polygon(new Point3D(0, 0, 0), new Point3D(300, 0, 0),
                        new Point3D(300, 400, 0), new Point3D(0, 400, 0))
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //flor

                /////////////////////////////---back wall---/////////////////////////////
                new Polygon(new Point3D(0, 0, 0), new Point3D(0, 0, 200),
                        new Point3D(100, 0, 200), new Point3D(100, 0, 0))
                        .setEmission(new Color(100, 100, 10)),//right

                new Polygon(new Point3D(300, 0, 0), new Point3D(300, 0, 200),
                        new Point3D(200, 0, 200), new Point3D(200, 0, 0))
                        .setEmission(new Color(100, 100, 10)),//left

                new Polygon(new Point3D(200, 0, 200), new Point3D(100, 0, 200),
                        new Point3D(100, 0, 170), new Point3D(200, 0, 170))
                        .setEmission(new Color(100, 100, 10)),//up

                new Polygon(new Point3D(200, 0, 120), new Point3D(100, 0, 120),
                        new Point3D(100, 0, 0), new Point3D(200, 0, 0))
                        .setEmission(new Color(100, 100, 10)),//down


                /////////////////////////////---The triangle of the pyramid---/////////////////////////////
                new Triangle(new Point3D(50, 50, 0), new Point3D(50, 100, 0), new Point3D(100, 75, 0)) //
                        .setEmission(new Color(40, 40, 90))//
                        .setMaterial(new Material().setKr(1)), // The base

                new Triangle(new Point3D(75, 75, 50), new Point3D(50, 100, 0), new Point3D(100, 75, 0)) //
                        .setEmission(new Color(40, 40, 90))//
                        .setMaterial(new Material().setKr(1)), //

                new Triangle(new Point3D(75, 75, 50), new Point3D(50, 100, 0), new Point3D(50, 50, 0)) //
                        .setEmission(new Color(40, 40, 90))//
                        .setMaterial(new Material().setKr(1)), //

                new Triangle(new Point3D(75, 75, 50), new Point3D(50, 50, 0), new Point3D(100, 75, 0)) //
                        .setEmission(new Color(40, 40, 90))//
                        .setMaterial(new Material().setKr(1)), //


                /////////////////////////////---The table---/////////////////////////////
                new Polygon(new Point3D(100, 200, 40), new Point3D(100, 120, 40),
                        new Point3D(200, 120, 40), new Point3D(200, 200, 40)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.7).setKr(0.2)),//up-middle

                new Polygon(new Point3D(100, 210, 40), new Point3D(100, 200, 40),
                        new Point3D(200, 200, 40), new Point3D(200, 210, 40)) //
                        .setEmission(new Color(110, 80, 64)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-front

                new Polygon(new Point3D(210, 210, 40), new Point3D(210, 210, 35),
                        new Point3D(90, 210, 35), new Point3D(90, 210, 40)) //
                        .setEmission(new Color(110, 80, 64))  //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-front-down

                new Polygon(new Point3D(200, 120, 40), new Point3D(200, 110, 40),
                        new Point3D(100, 110, 40), new Point3D(100, 120, 40)) //
                        .setEmission(new Color(110, 80, 64)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-back

                new Polygon(new Point3D(210, 110, 40), new Point3D(210, 110, 35),
                        new Point3D(90, 110, 35), new Point3D(90, 110, 40)) //
                        .setEmission(new Color(110, 80, 64))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-back-down

                new Polygon(new Point3D(200, 210, 40), new Point3D(200, 110, 40)
                        , new Point3D(210, 110, 40), new Point3D(210, 210, 40)) //
                        .setEmission(new Color(110, 80, 64))  //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-left

                new Polygon(new Point3D(210, 210, 40), new Point3D(210, 210, 35)
                        , new Point3D(210, 110, 35), new Point3D(210, 110, 40)) //
                        .setEmission(new Color(110, 80, 64))  //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-left-down

                new Polygon(new Point3D(90, 210, 40), new Point3D(90, 110, 40)
                        , new Point3D(100, 110, 40), new Point3D(100, 210, 40)) //
                        .setEmission(new Color(110, 80, 64)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-right

                new Polygon(new Point3D(90, 210, 40), new Point3D(90, 110, 40)
                        , new Point3D(90, 110, 35), new Point3D(90, 210, 35)) //
                        .setEmission(new Color(110, 80, 64))  //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//up-right-down

                /////////////////////////////---The feet---/////////////////////////////
                //---------left_front---------
                new Polygon(new Point3D(210, 210, 40), new Point3D(200, 210, 40)
                        , new Point3D(200, 210, 0), new Point3D(210, 210, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//front

                new Polygon(new Point3D(200, 210, 40), new Point3D(200, 200, 40)
                        , new Point3D(200, 200, 0), new Point3D(200, 210, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30)),//right

                new Polygon(new Point3D(210, 210, 40), new Point3D(210, 200, 40)
                        , new Point3D(210, 200, 0), new Point3D(210, 210, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//left

                new Polygon(new Point3D(210, 200, 40), new Point3D(200, 200, 40)
                        , new Point3D(200, 200, 0), new Point3D(210, 200, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//beck

                //---------right_front---------
                new Polygon(new Point3D(100, 210, 40), new Point3D(90, 210, 40)
                        , new Point3D(90, 210, 0), new Point3D(100, 210, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//front

                new Polygon(new Point3D(90, 210, 40), new Point3D(90, 200, 40)
                        , new Point3D(90, 200, 0), new Point3D(90, 210, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30)),//right

                new Polygon(new Point3D(100, 210, 40), new Point3D(100, 200, 40)
                        , new Point3D(100, 200, 0), new Point3D(100, 210, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//left

                new Polygon(new Point3D(100, 200, 40), new Point3D(90, 200, 40)
                        , new Point3D(90, 200, 0), new Point3D(100, 200, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//beck

                //---------right_beck---------
                new Polygon(new Point3D(100, 120, 40), new Point3D(90, 120, 40)
                        , new Point3D(90, 120, 0), new Point3D(100, 120, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//front

                new Polygon(new Point3D(90, 120, 40), new Point3D(90, 110, 40)
                        , new Point3D(90, 110, 0), new Point3D(90, 120, 0)) //
                        .setEmission(new Color(75, 69, 55))//
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30)),//right

                new Polygon(new Point3D(100, 120, 40), new Point3D(100, 110, 40)
                        , new Point3D(100, 110, 0), new Point3D(100, 120, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//left

                new Polygon(new Point3D(100, 110, 40), new Point3D(90, 110, 40)
                        , new Point3D(90, 110, 0), new Point3D(100, 110, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//beck

                //---------left_beck---------
                new Polygon(new Point3D(210, 120, 40), new Point3D(200, 120, 40)
                        , new Point3D(200, 120, 0), new Point3D(210, 120, 0)) //
                        .setEmission(new Color(75, 69, 55))//
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//front

                new Polygon(new Point3D(200, 120, 40), new Point3D(200, 110, 40)
                        , new Point3D(200, 110, 0), new Point3D(200, 120, 0)) //
                        .setEmission(new Color(75, 69, 55))//
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30)),//right

                new Polygon(new Point3D(210, 120, 40), new Point3D(210, 110, 40)
                        , new Point3D(210, 110, 0), new Point3D(210, 120, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//left

                new Polygon(new Point3D(210, 110, 40), new Point3D(200, 110, 40)
                        , new Point3D(200, 110, 0), new Point3D(210, 110, 0)) //
                        .setEmission(new Color(75, 69, 55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),//beck


                new Sphere(15, new Point3D(150, 130, 205)) // Light bulb socket
                        .setEmission(new Color(java.awt.Color.yellow)) //
                        .setMaterial(new Material().setKs(0.2).setShininess(30).setKt(0.7)),

                new Sphere(3, new Point3D(150, 130, 198)) // The lamp
                        .setEmission(new Color(java.awt.Color.WHITE)) //
                        .setMaterial(new Material().setKs(0.2).setShininess(30).setKt(1)),

                new Sphere(10, new Point3D(0.01, 180, 65)) // The spot
                        .setEmission(new Color(java.awt.Color.WHITE)) //
                        .setMaterial(new Material().setKs(0.2).setShininess(30).setKt(0.5)),

                //The balls on the table
                new Sphere(5, new Point3D(130, 170, 45.01)) //
                        .setEmission(new Color(java.awt.Color.ORANGE)) //
                        .setMaterial(new Material().setKs(0.2).setShininess(30)),
                new Sphere(5, new Point3D(125, 160, 45.01)) //
                        .setEmission(new Color(java.awt.Color.MAGENTA)) //
                        .setMaterial(new Material().setKs(0.2).setShininess(30)),
                new Sphere(5, new Point3D(145, 163, 45.01)) //
                        .setEmission(new Color(java.awt.Color.LIGHT_GRAY)) //
                        .setMaterial(new Material().setKs(0.2).setShininess(30)),

                new Sphere(15, new Point3D(55, 190, 20)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setKt(0.4).setShininess(30).setKr(0.3)));
//

        scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point3D(70, 400, 100), new Vector(-70, -400, -100)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(java.awt.Color.WHITE), new Point3D(0.01, 180, 65)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(244, 244, 214), new Point3D(150, 130, 198)).setKl(4E-5).setKq(2E-7));
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.YELLOW), new Vector(0.5, 1.5, -1)));

        ImageWriter imageWriter = new ImageWriter("bxhui", 800, 800);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene).setBVH())
                .setMultithreading(3);

        render.renderImage();
        render.writeToImage();
    }


}