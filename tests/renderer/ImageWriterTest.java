package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

class ImageWriterTest {
    @Test
    void createImage(){
        ImageWriter imageTest = new ImageWriter("hello image",800,500);
        //imageTest.writeToImage();
        for (int i = 0; i < 800; i++ ){
            for (int j = 0; j < 500; j++ ){
                if(i % 50 == 0 && i > 0 || j % 50 == 0 && j > 0) //
                {
                    imageTest.writePixel(i, j,new Color(0, 0,0));
                }
                else imageTest.writePixel(i, j,new Color(255, 255,255));
            }
            imageTest.writeToImage();
        }
    }

}