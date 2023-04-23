
## Data structures and Algorithms assignment for semester 4
 
 The objective of this CA exercise is to create a JavaFX application that can analyse a given image of
deep space containing various celestial objects (stars, galaxies, nebulae, etc.). When given a deep
space image (and some user settings), the application will automatically locate the various celestial
objects on the deep space image and estimate how many of them are in the image overall. The
application should also mark the location of celestial objects on the deep space image with blue circles,
optionally number them sequentially from largest to smallest, and allow for gaseous analysis of
individual celestial bodies. 
 
 
The key aspect of this CA exercise is to use a union-find algorithm to locate the celestial
objects.


The input deep space image should be converted to black-and-white in the first instance pixelby-pixel using suitable luminance/hue/saturation/brightness/RGB calculations.



###  Overall, an interactive JavaFX GUI should be provided to allow users to:
  - Select a deep space image file to use.
  <img src="https://user-images.githubusercontent.com/91613044/233866895-b7e51492-75c6-4272-879f-5d4441685ed9.png" width="600" height="300">
  
  ![image](https://user-images.githubusercontent.com/91613044/233867080-762e5b0f-e5fc-4c06-90c0-aefabad419ed.png)


  - View the original deep space image.
  - Perform and view the black-and-white image conversion (using the user specified settings for noise management, luminance, etc.).
  ![image](https://user-images.githubusercontent.com/91613044/233867102-21fe6a3c-9a2f-4466-9cb4-853cb3ee4d66.png)

  - Perform the celestial object recognition/identification using union-find, and mark celestial objects on the original deep space image (or a copy of it) with blue circles.
  ![image](https://user-images.githubusercontent.com/91613044/233867205-670b369a-4237-4537-8382-9b07e9fe4b47.png)

  - See estimates of the number of celestial objects in the image.
  ![image](https://user-images.githubusercontent.com/91613044/233867291-eb304605-7196-4cf9-b5a6-3804a811ed02.png)

  - See the pixel unit/disjoint set sizes and perform simple gaseous analysis.
  - Ordered sequential numbering of celestial objects, including onscreen labelling.
  - Visualise/colour disjoint set pixels in the black-and-white image (individually as an average colour for a given set, or random colours for all sets; support both options).
  - Adjust settings to manage noise and outliers and to aid the black-and-white conversion.
  - Cleanly navigate and exit the application and have a good user experience overall.






## Marking Scheme

- Deep space image file selection and display = 5%
- Black-and-white image conversion and display = 10%
- Union-find implementation = 10%
- Onscreen identification of all selected celestial objects (using blue circles) = 10%
- Ordered sequential celestial objects numbering (onscreen labelling) = 10%
- Estimating/counting of celestial objects in overall image = 5%
- Reporting the size of individual disjoint sets (in pixel units) = 5%
- Gaseous analysis for sulphur, hydrogen and oxygen = 5%
- Colouring disjoint sets in black-and-white image (individual average / all random) = 10%
- Image noise reduction and outlier management = 10%
- JavaFX GUI = 5%
- JUnit Testing = 5%
- JMH Benchmarking of key methods = 5%
- General (overall completeness, structure, commenting, logic, etc.) = 5%




