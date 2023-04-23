package com.example.sem2ca1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.effect.ColorAdjust;
import java.lang.reflect.Array;
import java.io.File;

public class Controller extends Driver implements Initializable  {

    @FXML
    private ImageView imageFrame, processedImage;

    @FXML
    private Button importButton;
//    @FXML
//    ComboBox channelDropdown;
    @FXML
    Slider threshSlider, minStarSizeSlider;
    @FXML
    TextArea consoleOut;

    @FXML
    Label inputImgLabel;
    FileChooser fileChooser = new FileChooser();

    Image image;
    private Map<Integer, Color> colorMap;

    private HashMap<Integer,List<Integer>> allStarPixels;
    int[] pixelArray;

    public int[] selectedCircles;
    public void resetAll() throws IOException{
        reset();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.setInitialDirectory(new File("C:\\Users\\"));
//        channelDropdown.getItems().addAll("Red", "Green", "Blue");
        /*threshSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                colorToBlackAndWhite();
            }
        });*/
        minStarSizeSlider.setOnMouseReleased( event -> {
//            scanAndCategorise();
            //clear circles in imageFrame imageView
//            ((Pane)imageFrame.getParent()).getChildren().add(c);
//            ((Pane)imageFrame.getParent()).getChildren().removeIf(c -> c instanceof Circle);
//            ((Pane)imageFrame.getParent()).getChildren().removeIf(c -> c instanceof Label);
            displayCircles();

//            imageFrame.getChildren().clear();
        });
        threshSlider.setOnMouseReleased( event -> {
            colorToBlackAndWhite();
        });
//        setConsoleOut("Console Output: \r");
    }

    //set consoleOut to display console output



    public void loadPresetImage(ActionEvent event) throws IOException {
        image = new Image("file:src/main/resources/com/example/sem2ca1/stars.jpg");
        imageFrame.setImage(image);
        inputImgLabel.setText("Stars.jpg");
    }

    public void getImage(ActionEvent event) throws IOException {
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Window mainStage = null;
        File file = fileChooser.showOpenDialog(mainStage);
        System.out.println(file.getAbsolutePath());
//        image =  new Image(file.toURI().toString(), 535,633, false, true );
        image =  new Image(file.toURI().toString());
        inputImgLabel.setText(file.getName());
//        imageFrame.setEffect(colorAdjust);
        imageFrame.setImage(image);
    }


    public void colorToBlackAndWhite() {
    if(image != null){
        WritableImage wi = new WritableImage((int)image.getWidth(), (int)image.getHeight());
        PixelReader pr = image.getPixelReader();
        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();
//        int[] pixelArray = new int[imageWidth * imageHeight];
        pixelArray = new int[imageWidth * imageHeight];
        for(int y = 0; y<(int)image.getHeight(); y++) {
            for(int x = 0; x<(int)image.getWidth(); x++) {
                int index = y * (int)image.getWidth() + x;
                Color c = pr.getColor(x,y);
                double threshold = threshSlider.getValue();
                if(c.getRed() < threshold && c.getBlue() < threshold && c.getGreen() < threshold){
                    wi.getPixelWriter().setColor(x,y, Color.BLACK );
                    pixelArray[index] = -1;
                }
                else {
                    wi.getPixelWriter().setColor(x, y, Color.WHITE);
                    pixelArray[index] = index;
                    }
                }
            }
            processedImage.setImage(wi);
    //        System.out.println(Arrays.toString(pixelArray));
        }else System.out.println("please import an image"); //Error avoidance if-statement
    }


    public void scanAndCategorise() {
    if(image != null){
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        for (int y = 0; y < (int) image.getHeight(); y++) {
            for (int x = 0; x < (int) image.getWidth(); x++) {
                int index = y * (int) image.getWidth() + x;
                if (pixelArray[index] != -1) {
                    // check right neighbor
                    if (x + 1 < width && pixelArray[index+1] != -1) {
                        union(pixelArray, index, index+1);
                    }
                    // check bottom neighbor
                    if (index + width < pixelArray.length && pixelArray[index+width] != -1) {
                        union(pixelArray, index, index+width);
                    }
                }
            }
        }
        //union find debug loop
    //          for(int i = 0; i < pixelArray.length;i++){
    //            if(i%((int)image.getWidth())==0){
    //                    System.out.println(pixelArray[i] + " ");}
    //                else System.out.print(pixelArray[i]+ " ");
    //        }

            System.out.println("\n  Width is: " + width + ", Height is: " + height);
        }else System.out.println("no black and white image provided");
    }

    public void assignRandomColorToStar() {
        scanAndCategorise();
        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();
        WritableImage wi = new WritableImage(imageWidth, imageHeight);

        // Create a map that maps set root values to random colors
        this.colorMap = new HashMap<>();
        for (int i = 0; i < pixelArray.length; i++) {
            int root = find(pixelArray, i);
            if (root != -1 && !colorMap.containsKey(root)) {
                colorMap.put(root, Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            }
        }
        // Loop over the pixels and set their colors based on the root value
        // This loop parses the one dimensional array and pixelWriter reconstructs it back to an image
        for (int i = 0; i < pixelArray.length; i++) {
            int row = i / imageWidth;
            int col = i % imageWidth;
            int root = find(pixelArray, i);
            if (root != -1) {
                Color color = colorMap.get(root);
                wi.getPixelWriter().setColor(col, row, color);
            } else {
                wi.getPixelWriter().setColor(col, row, Color.BLACK);
            }
        }
        int numberOfStars =  colorMap.size();
        System.out.println("There are " + numberOfStars +  " stars in this image");
        processedImage.setImage(wi);
        //save processedImage to file
    }

    public void saveProcessedImage(ActionEvent event) throws IOException {
        if(processedImage != null){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Processed Image");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            Window mainStage = null;
            File file = fileChooser.showSaveDialog(mainStage);
            System.out.println(file.getAbsolutePath());
            ImageIO.write(SwingFXUtils.fromFXImage(processedImage.getImage(), null), "png", file);
        }else System.out.println("no processed image to save");
    }



    public void printSizeOfEachStar( ) {
    //this method is wrong
        int setSize = 0;
        int firstPixel = 0;
        int lastPixel = 0;
        for (int i = 0; i < pixelArray.length; i++) {

            int root = find(pixelArray, i);

            if (root != -1 && colorMap.containsKey(root)) {
                setSize++;
                if(firstPixel == 0){
                    firstPixel += i;
                    }
                }
            if (!colorMap.containsKey(root)) {
                setSize = 0;
                firstPixel = 0;}
            else{
                int firstPixelX = firstPixel/(int)image.getWidth();
                int firstPixelY = firstPixel%(int)image.getHeight();
//                System.out.println("set size=" + setSize +"px, "  + "first pixel:  x:" + firstPixelX + ", y:"+ firstPixelY);
                System.out.println(setSize + "px");
            }
        }
        System.out.println("Hashmap size: " + colorMap.size());
    }


    public void displayCircles(){
        if(colorMap != null){
            ((Pane)imageFrame.getParent()).getChildren().removeIf(c -> c instanceof Circle);
            ((Pane)imageFrame.getParent()).getChildren().removeIf(c -> c instanceof Label);
            double minSize = minStarSizeSlider.getValue();
            double top =Integer.MAX_VALUE; double bottom=0; double left=Integer.MAX_VALUE; double right=0;
            int imageWidth = (int) image.getWidth();
            int imageHeight = (int) image.getHeight();
            WritableImage wi = new WritableImage(imageWidth, imageHeight);
            this.allStarPixels = new HashMap<>();

            for (int i = 0; i < pixelArray.length; i++) {
                int root = find(pixelArray, i);
                if (root != -1 ) {
                    if(!allStarPixels.containsKey(root)) {
                        allStarPixels.put(root,new ArrayList<>());
                    }
                    allStarPixels.get(root).add(i);}
            }
            Bounds bounds = imageFrame.getLayoutBounds();
            double scaleX = bounds.getWidth()/imageFrame.getImage().getWidth();
            double scaleY = bounds.getHeight()/imageFrame.getImage().getHeight();

            for(int root : allStarPixels.keySet()) {
                top=Collections.min(allStarPixels.get(root))/(int)image.getWidth();
                bottom =Collections.max(allStarPixels.get(root))/(int)image.getWidth();
                left=Collections.min(allStarPixels.get(root),(a,b)->a%imageWidth-b%imageWidth)%imageWidth;//negative if a should come before b and pos if a comes after, zero if theyre the same
                right = Collections.max(allStarPixels.get(root), (a,b)->a%imageWidth-b%imageWidth)%imageWidth;
                int starPixNum = allStarPixels.get(root).size();
                if(root != -1) {
                    top*=scaleX;
                    right *= scaleY;
                    bottom*= scaleX;
                    left *= scaleY;
                }
                double centerX = (left+right)/2;
                double centerY = (top+bottom)/2;
                double xradius = centerX - left;
                double yRadius = centerY - top;
                double radius;
                if(xradius > yRadius) {radius = xradius;}
                else {radius = yRadius;}
//            System.out.println(radius);
                int starSizeRank = 1;
                //sort start sizes by starPixNum (0 being smallest, 1 being second smallest, etc)
                for(int size : allStarPixels.keySet()){
                    if(allStarPixels.get(size).size() > starPixNum){
                        starSizeRank++;
                    }
                }
                if(starPixNum > minSize){
//              ((Pane)imageFrame.getParent()).getChildren().clear();
                    Circle c = new Circle(centerX, centerY, radius);
                    Label label = new Label("" + starSizeRank);
                    Tooltip t = new Tooltip("the size of this star is : " + starPixNum +"\n"+ analyzeGasses(c));
                    c.setFill(Color.TRANSPARENT);
                    c.setStroke(Color.RED);
                    label.setTextFill(Color.WHITE);
                    label.setStyle("-fx-font-size: 18px; -fx-font-style: bold;");
                    if(radius < minSize/6){
                        label.setStyle("-fx-font-size: 12px;");
                        c.setStrokeWidth(1);
                    }else {
                        c.setStrokeWidth(3);

                    }
                    c.setTranslateX(imageFrame.getLayoutX());
                    c.setTranslateY(imageFrame.getLayoutY());
                    //place a label above every circle with its size


                    label.setTranslateX(centerX + radius);
                    label.setTranslateY(centerY - radius);
//                    label.setTranslateX(centerX + imageWidth/(5*radius));
//                    label.setTranslateY(centerY - imageHeight/(5*radius));



                    ((Pane)imageFrame.getParent()).getChildren().add(c);
                    ((Pane)imageFrame.getParent()).getChildren().add(label);
                    c.onMouseClickedProperty().set(e->{
                        selectSingleStar(c);
                    });
                    Tooltip.install(c,t);
                }
            }
        }else System.out.println("Please run union-find first!");
    }


    public void selectSingleStar(Circle c) {
        int rad = (int)c.getRadius();
        int x = (int)c.getCenterX()*3 + rad;
        int y = (int)c.getCenterY()*3 ;
        int index = y*(int)image.getWidth() + x;
        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();
        WritableImage wi = new WritableImage(imageWidth, imageHeight);

        // find the root of the circle's center pixel
        for (int i = index; i < pixelArray.length; i++) {

            int root = find(pixelArray, index);
            int row = i / imageWidth;
                int col = i % imageWidth;
                if (root != -1 && i==find(pixelArray, index)) {
                    Color color = colorMap.get(root);
                    wi.getPixelWriter().setColor(col, row, color);
                } else {
                    index++;
//                    wi.getPixelWriter().setColor(col, row, Color.BLACK);
                }
            }

        // loop over all the pixels within the circle
//        for (int i = (int)c.getCenterX() - rad; i <= (int)c.getCenterX() + rad; i++) {
//            int row = i / imageWidth;
//            int col = i % imageWidth;
//            for (int j = (int)c.getCenterY() - rad; j <= (int)c.getCenterY() + rad; j++) {
//                int idx = j*(int)image.getWidth() + i;
//                wi.getPixelWriter().setColor(col, row, Color.BLACK);
//                // check if the pixel belongs to the same component as the root pixel
//                if (find(pixelArray, idx) == root) {
//                    // update the pixel color to indicate that it's selected
////                    int red = (int)(255 * Math.random());
////                    int green = (int)(255 * Math.random());
////                    int blue = (int)(255 * Math.random());
////                    int color = (red << 16) | (green << 8) | blue;
//                    wi.getPixelWriter().setColor(i, j, Color.WHITE);
//                }
//            }
//        }
        processedImage.setImage(wi);
    }


    public void selectSingleStarOLD(Circle c){
        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();
        WritableImage wi = new WritableImage(imageWidth, imageHeight);

        HashMap<Integer, Color> singleStarPixels = new HashMap<>();

        int rad = (int)c.getRadius();
        int circleArea = (int)(Math.PI * rad * rad);
        //Im fully not sure why I have to multiply X and Y by 3, center X and Y were way too small initially
        // so I decided to take the image into photoshop and measure X and Y distances from the left and top
        // border for a few circles and I noticed the values intellij was printing out were three times smaller
        //than the actual values in photoshop.
        int x = (int)c.getCenterX()*3 + rad;
        int y = (int)c.getCenterY()*3 + rad;
        System.out.println("circle center: x"+x + ", y" + y);
        //convert x and y to an index in the pixel array
        int index = y*(int)image.getWidth() + x;
//        System.out.println(index);
//
        //go to the index in the pixel array and find the root of the star in colorMap

        int root = find(pixelArray,index);
        if(root != -1) {
            System.out.println("root is:"+root);
        }//theres a good chance that the index will land on a black pixel
        // therefore we move it to the left until it hits a non-black root
        else{
        //this method is not flawless because the iterator could keep moving left, escape the circle
        // and hit the root of a different star
            for(int i =index; i < pixelArray.length; i++){
                if(root != -1){
                    System.out.println("root is:"+root);
                }
            }

//            for (int i = 0; i < pixelArray.length; i++) {
//                int row = i / imageWidth;
//                int col = i % imageWidth;
//                int root = find(pixelArray, i);
//                if (root != -1) {
//                    Color color = colorMap.get(root);
//                    wi.getPixelWriter().setColor(col, row, color);
//                } else {
//                    wi.getPixelWriter().setColor(col, row, Color.BLACK);
//                }
//            }


//            for(int i =0; i < pixelArray.length; i++){
//            allStarPixels.get(root).get(i);
//            System.out.println(i + ",");
//
//                if (i + 1 < imageWidth && pixelArray[index+1] == root) {
//                    Color color = colorMap.get(root);
//                    wi.getPixelWriter().setColor(col, row, color);
//                }
//                // check bottom neighbor
//                if (index + imageWidth < pixelArray.length && pixelArray[index+imageWidth] != -1) {
//
//                }
//            }
        }
        int pixelCount = allStarPixels.get(root).size();
        System.out.println("pixel count is: " + pixelCount);
    }


//        allStarPixels.get()
        //when circle is clicked select the star
        //clear all previous circles
        //when a circle is clicked, return the root of the star it represents




    public String analyzeGasses(Circle c){
        String hydrogen = "";
        String oxygen = "";
        String sulfur = "";
        int rad = (int)c.getRadius();
        int x = (int)c.getCenterX()*3 + rad;
        int y = (int)c.getCenterY()*3 + rad;

        //convert x and y to an index in the pixel array
        int index = y*(int)image.getWidth() + x;
//        imageFrame.getImage().getPixelReader()

        int root = find(pixelArray,index);
        //use the root to scan every pixel of the star in ImageFrame


    return "Hydrogen: " + hydrogen + " Oxygen: " + oxygen + " Sulfur: " + sulfur + " ";

    }


    public int indexToX(int i){
        int x = i%(int)image.getWidth();
        return x;
    }

    public int indexToY(int i){
        int y = i/(int)image.getWidth();
        return y;
    }

    public static int find(int[] a, int id) {
       // while(a[id]!=id) id=a[id];
       if(a[id]<0 ) return a[id];
       if(a[id] == id) return id;
       else return find(a, a[id]);
       // return id;
    }

    //Quick union of disjoint sets containing elements p and q (Version 1)
    public static void union(int[] a, int p, int q)
     {
        a[find(a,q)]=find(a, p); //The root of q is made reference p
    }

}