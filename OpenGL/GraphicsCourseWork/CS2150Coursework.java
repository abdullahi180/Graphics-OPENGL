/* CS2150Coursework.java
 * TODO: put your university username and full name here
 *180104174
 *Abdullahi Shidane
 *Computer graphics CS2150
 *year2
 * Scene Graph:
 *  Scene origin
 *  |
 *  +-- [S(20,1,20) T(0,-1,-10)] Ground plane
 *  |
 *  +-- [S(20,1,10) Rx(90) T(0,4,-20)] Sky plane
 *  |
 *  +-- [T(8.0f, 7, -21.1f)] Moon
 *  |
 *  +-- [T(1,-1,-18)] Tree
 *  |   |
 *  |   +-- [Rx(-90)] Trunk
 *  |   |
 *  |   +-- [T(0,2,0)] triangular head
    |
 *  +-- [T(-1,-1,-18)] Tree
 *  |   |
 *  |   +-- [Rx(-90)] Trunk
 *  |   |
 *  |   +-- [T(0,2,0)] triangular head
 *  |
 *  | +-- [T(-3,-1,-18)] Tree
 *  |   |
 *  |   +-- [Rx(-90)] Trunk
 *  |   |
 *  |   +-- [T(0,2,0)] triangular head
 *  |
 *  +-- [T(-5,-1,-18)] Tree
 *  |   |
 *  |   +-- [Rx(-90)] Trunk
 *  |   |
 *  |   +-- [T(0,2,0)] triangular head
 *  |
 *  +-- [T(-7,-1,-18)] Tree
 *  |   |
 *  |   +-- [Rx(-90)] Trunk
 *  |   |
 *  |   +-- [T(0,2,0)] triangular head
 *  |
 *  +-- [S(1.0, 1.0, 1.0) T(currentTractorX,-1.4,-1.8)] Tractor Body
 *  |   |
 *  |   +-- [S(1.0, 1.0, 1.0) T(currentTractorWindowX, -1.0, -1.8)] Tractor Window
 *  |   |
 *  |   +-- [S(0.12,0.13,0.1) T(currentTractorWindowX,0.32,-1.8)] Tractor Roof
 *  |   |
 *  |   +-- [ T(1, -0.3, 0.2)] Tractor wheel1
 *  |   |
 *  |   +-- [ T(-0.2, 0.0, -0.5)] Tractor wheel2
 *  |   |
 *  |   +-- [ T(-1.8, -0.3 ,0.6)] Tractor wheel3
 *  |   |
 *  |   +-- [ T(0.0, 0.0 ,1.3)] Tractor wheel4
 *  |
 *  +-- [S(0.15, 0.15, 0.15) T(-0.7, 0.0, -2.0)] Windmill Body 
 *  |   |
 *  |   +-- [S(1.7, 1.7, 1.5) T(0.12, 0.6, -0.20) R(bladesRotationAngle, 0.0, 0.0, 1.0) ] Windmill Blades   
 *  |  
 *  +-- [S(0.15, 0.15, 0.15) T(currentAeroplaneX, 0.65f, -2.5f)] aeroplanes body
 *  |   |
 *  |   +-- [S(0.7f, 0.7f, -1.5f) T(currentAeroplaneWingsX, -0.2f, -2.0f)  ] aeroplanes wings
 *  |
 */
package GraphicsCourseWork;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import GraphicsLab.*;

/**
 * TODO: Briefly describe your submission here
 * 
 * My scene is farm, which includes a tractor which the user can 
 * move around with. There is also a windmill spinning and a plane which 
 * flies past. In this submission there are various animations, some which the
 * user could have input and some automated. 
 * 
 * <p>
 * Controls:
 * <ul>Press 'L' to move the tractor left and press R to move the Tractor Right.
 * <li>Press the escape key to exit the application.
 * <li>Hold the x, y and z keys to view the scene along the x, y and z axis,
 * respectively
 * <li>While viewing the scene along the x, y or z axis, use the up and down
 * cursor keys to increase or decrease the viewpoint's distance from the scene
 * origin
 * </ul>
 * 
 *
 */

public class CS2150Coursework extends GraphicsLab {
	
	
	
	/** display list id for the unit plane */
	private final int planeList = 1;

	/** display list id for the tractors roof */
	private final int tractorRoofList = 2;
	
	/** display list id for the tractors window */
	private final int tractorWindowsList = 3;
	
	/** display list id for the tractor */
	private final int tractorBodyList = 4;
	
	/** display list id for the Windmill */
	private final int windmillBodyList = 5;
	
	/** display list id for the windmill's blades */
	private final int windmillBladesList = 6;
	
	/** display list id for the aeroplane */
	private final int aeroplaneList = 7;
	
	/** display list id for the Aeroplanes wings */
    private final int aeroplaneWingList = 8;
    
  
	
    /** the tractors current X offset from the scene origin */
	private float currentTractorX = 0.1f;
	
	/** the tractors highest possible X offset */
	private final float highestTractorX = currentTractorX;
	
	/** the tractors lowest possible X offset */
	private final float lowestTractorX = -0.5f;
	
	/** is the tractor moving (false = tractor moving left/ true = tractor moving right) */
	private boolean isStill = true;

	/** the tractors roof and window current X offset from the scene origin */
	 private float currentTractorWindowX = 0.66f;
	 /** is the tractor window/ roof moving (false = tractor wheels moving left / true = tractor wheels moving right) */
	 private boolean isStillWindow = true;
	
	 /** the blades rotational angle */
	 private float bladesRotationAngle = 0.0f;
	   
	 /** the aeroplanes current X offset from the scene origin */
	 private float currentAeroplaneX = 1.86f;
	 /** the aeroplanes wings current X offset from the scene origin */
	 private float currentAeroplaneWingsX = 0.1f;
	
	/** ids for nearest, linear and mipmapped textures for the ground plane */
	private Texture grassTextures;
	/**
	 * ids for nearest, linear and mipmapped textures for the night time back (sky)
	 * plane
	 */
	
	/**
	 * ids for nearest, linear and mipmapped textures for the night time back (sky)
	 * plane
	 */
	private Texture nightTextures;

	// TODO: Feel free to change the window title and default animation scale here
	public static void main(String args[]) {
		new CS2150Coursework().run(WINDOWED, "CS2150 Coursework Submission", 0.02f);
	}

	protected void initScene() throws Exception {// TODO: Initialise your resources here - might well call other methods
													// you write.
													// load the textures
													// load the textures
		grassTextures = loadTexture("GraphicsCourseWork/textures/grass.bmp");
		// skyDayTextures = loadTexture("Lab6/textures/daySky.bmp");
		nightTextures = loadTexture("GraphicsCourseWork/textures/night.bmp");

		// global ambient light level
		float globalAmbient[] = { 0.5f, 0.5f, 0.5f, 5.0f };
		// set the global ambient lighting
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(globalAmbient));

		// the first light for the scene is soft blue...
		float diffuse0[] = { 0.5f, 0.5f, 0.5f, 1.0f };
		// ...with a very dim ambient contribution...
		float ambient0[] = { 0.5f, 0.5f, 0.5f, 1.0f };
		// ...and is positioned above the viewpoint
		float position0[] = { 0.0f, 10.0f, 0.0f, 1.0f };

		// supply OpenGL with the properties for the first light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, FloatBuffer.wrap(ambient0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, FloatBuffer.wrap(position0));
		// enable the first light
		GL11.glEnable(GL11.GL_LIGHT0);

		// enable lighting calculations
		GL11.glEnable(GL11.GL_LIGHTING);
		// ensure that all normals are re-normalised after transformations automatically
		GL11.glEnable(GL11.GL_NORMALIZE);

		// prepare the display lists for later use.
		

		GL11.glNewList(planeList, GL11.GL_COMPILE);
		{
			drawUnitPlane();
		}
		GL11.glEndList();

		GL11.glNewList(tractorRoofList, GL11.GL_COMPILE);
		{
			drawTractorRoof();
		}
		GL11.glEndList();
		GL11.glNewList(tractorWindowsList, GL11.GL_COMPILE);
		{
			drawTractorWindows();
		}
		GL11.glEndList();
		GL11.glNewList(tractorBodyList, GL11.GL_COMPILE);
		{
			drawTractorBody();
		}
		GL11.glEndList();

		GL11.glEndList();
		GL11.glNewList(windmillBladesList, GL11.GL_COMPILE);
		{
			drawWindmillBlades();
		}
		GL11.glEndList();

		GL11.glNewList(windmillBodyList, GL11.GL_COMPILE);

		{
			drawWindmillBody();
		}
		GL11.glEndList();
		
		GL11.glNewList(aeroplaneList, GL11.GL_COMPILE);

		{
			drawAeroplane();
		}
		GL11.glEndList();

		GL11.glNewList(aeroplaneWingList, GL11.GL_COMPILE);

		{
			drawAeroplaneWing();
		}
		GL11.glEndList();
		
	
		

	}

	private void drawAeroplaneWing() {
		// TODO Auto-generated method stub
		//wing2 front
		Vertex v1 = new Vertex(-0.2f, 0.5f, 0.5f);
		Vertex v2 = new Vertex(0.5f, 0.5f, -2.5f);
		Vertex v3 = new Vertex(1.0f, 0.5f, -2.5f);
		Vertex v4 = new Vertex(0.2f, 0.5f, 0.5f);
	
		Vertex v5 = new Vertex(-0.2f, 0.8f, 0.5f);
		Vertex v6 = new Vertex(0.5f, 0.8f, -2.5f);
		Vertex v7 = new Vertex(1.0f, 0.8f, -2.5f);
		Vertex v8 = new Vertex(0.2f, 0.8f, 0.5f);
				
				GL11.glBegin(GL11.GL_POLYGON);
				{
					
					//submitNextColour();

					v4.submit();
					v3.submit();
					v2.submit();
					v1.submit();
					
					//near
					
					
				}
				GL11.glEnd();
				
				GL11.glBegin(GL11.GL_POLYGON);
				{
					
					//submitNextColour();

					v1.submit();
					v2.submit();
					v6.submit();
					v5.submit();
					
					//left
					
					
				}
				GL11.glEnd();		

				GL11.glBegin(GL11.GL_POLYGON);
				{
					
					//submitNextColour();

					v8.submit();
					v7.submit();
					v3.submit();
					v4.submit();
					
					//right
					
					
				}
				GL11.glEnd();

				GL11.glBegin(GL11.GL_POLYGON);
				{
					
					//submitNextColour();

					v3.submit();
					v7.submit();
					v6.submit();
					v2.submit();
					
					//top
					
					
				}
				GL11.glEnd();

				GL11.glBegin(GL11.GL_POLYGON);
				{
					
					//submitNextColour();

					v8.submit();
					v4.submit();
					v1.submit();
					v5.submit();
					
					//bottom
					
					
				}
				GL11.glEnd();

				GL11.glBegin(GL11.GL_POLYGON);
				{
					
					//submitNextColour();

					v5.submit();
					v6.submit();
					v7.submit();
					v8.submit();
					
					//
					
					
				}
				GL11.glEnd();	
	}

	private void drawAeroplane() {
		// TODO Auto-generated method stub
		Vertex v1 = new Vertex(-1.5f, 0.0f, 0.5f);
		Vertex v2 = new Vertex(-1.25f, 0.5f, 0.5f);
		Vertex v3 = new Vertex(1.5f, 0.5f, 0.5f);
		Vertex v4 = new Vertex(1.8f, 0.8f, 0.5f);
		Vertex v5 = new Vertex(1.8f, 0.8f, 0.5f);
		Vertex v6 = new Vertex(1.5f, 0.0f, 0.5f);
		Vertex v7 = new Vertex(-1.5f, 0.0f,0.0f);
		Vertex v8 = new Vertex(-1.25f, 0.5f, 0.0f);
		Vertex v9 = new Vertex(1.5f, 0.5f, 0.0f);
		Vertex v10 = new Vertex(1.8f, 0.8f, 0.0f);
		Vertex v11= new Vertex(1.8f, 0.8f, 0.0f);
		Vertex v12 = new Vertex(1.5f, 0.0f, 0.0f);
	
		GL11.glBegin(GL11.GL_POLYGON);
		{
			//near
			//submitNextColour();

			v1.submit();
			v6.submit();
			v5.submit();
			v4.submit();
			v3.submit();
			v2.submit();
			
			//near
			
			
		}
		GL11.glEnd();
	
	
		
		
		GL11.glBegin(GL11.GL_POLYGON);
		{
			//back
			//submitNextColour();

			v7.submit();
			v8.submit();
			v9.submit();
			v10.submit();
			v11.submit();
			v12.submit();
			
			
			
		}
		GL11.glEnd();
		
		
		
		GL11.glBegin(GL11.GL_POLYGON);
		{
			//bottom
			//submitNextColour();

			v7.submit();
			v12.submit();
			v6.submit();
			v1.submit();
			
			
			
		}
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_POLYGON);
		{
			//top
			//submitNextColour();

			
			v2.submit();
			v3.submit();
			v9.submit();
			v8.submit();
		
			
			
		}
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_POLYGON);
		{
			//topr
			//submitNextColour();

			
			v3.submit();
			v4.submit();
			v10.submit();
			v9.submit();
		
			
			
		}
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_POLYGON);
		{
			//left
			//submitNextColour();

			
			v1.submit();
			v2.submit();
			v8.submit();
			v7.submit();
		
			
			
		}
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_POLYGON);
		{
			//right
			//submitNextColour();

			
			v6.submit();
			v12.submit();
			v11.submit();
			v10.submit();
			v4.submit();
			v5.submit();
			
			
			
		}
		GL11.glEnd();
		
		
		
	}
	

	private void drawWindmillBlades() {
		// TODO Auto-generated method stub
		
		
		// i have documented the faces on the design document, and explained there how i designed it.
		Vertex v1 = new Vertex(-0.2f, -0.2f, 0.5f);
		Vertex v2 = new Vertex(-0.2f, 0.2f, 0.5f);
		Vertex v3 = new Vertex(0.2f, 0.2f, 0.5f);
		Vertex v4 = new Vertex(0.2f, -0.2f, 0.5f);
		Vertex v5 = new Vertex(-0.2f, -0.2f, 0.3f);
		Vertex v6 = new Vertex(-0.2f, 0.2f, 0.3f);
		Vertex v7 = new Vertex(0.2f, 0.2f, 0.3f);
		Vertex v8 = new Vertex(0.2f, -0.2f, 0.3f);

		Vertex v9 = new Vertex(-0.2f, 1.2f, 0.5f);
		Vertex v10 = new Vertex(0.2f, 1.2f, 0.5f);
		Vertex v11 = new Vertex(-0.2f, 1.2f, 0.4f);
		Vertex v12 = new Vertex(0.2f, 1.2f, 0.4f);

		Vertex v13 = new Vertex(-0.2f, -1.2f, 0.5f);
		Vertex v14 = new Vertex(0.2f, -1.2f, 0.5f);
		Vertex v15 = new Vertex(-0.2f, -1.2f, 0.4f);
		Vertex v16 = new Vertex(0.2f, -1.2f, 0.4f);

		Vertex v17 = new Vertex(-1.2f, -0.2f, 0.5f);
		Vertex v18 = new Vertex(-1.2f, 0.2f, 0.5f);
		Vertex v19 = new Vertex(-1.2f, -0.2f, 0.4f);
		Vertex v20 = new Vertex(-1.2f, 0.2f, 0.4f);

		Vertex v21 = new Vertex(1.2f, 0.2f, 0.5f);
		Vertex v22 = new Vertex(1.2f, -0.2f, 0.5f);
		Vertex v23 = new Vertex(1.2f, 0.2f, 0.4f);
		Vertex v24 = new Vertex(1.2f, -0.2f, 0.4f);

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v21.submit();
			v22.submit();
			v24.submit();
			v23.submit();

			// top

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v4.submit();
			v8.submit();
			v24.submit();
			v22.submit();

			// right

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v7.submit();
			v3.submit();
			v21.submit();
			v23.submit();

			// left

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v7.submit();
			v23.submit();
			v24.submit();
			v8.submit();

			// far

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v3.submit();
			v4.submit();
			v22.submit();
			v21.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v5.submit();
			v1.submit();
			v17.submit();
			v19.submit();

			// left

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v2.submit();
			v6.submit();
			v20.submit();
			v18.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v17.submit();
			v18.submit();
			v20.submit();
			v19.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v5.submit();
			v19.submit();
			v20.submit();
			v6.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v1.submit();
			v2.submit();
			v18.submit();
			v17.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v1.submit();
			v13.submit();
			v14.submit();
			v4.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v14.submit();
			v16.submit();
			v8.submit();
			v4.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v15.submit();
			v13.submit();
			v14.submit();
			v16.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v15.submit();
			v13.submit();
			v1.submit();
			v5.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v15.submit();
			v5.submit();
			v8.submit();
			v16.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v2.submit();
			v3.submit();
			v10.submit();
			v9.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v11.submit();
			v6.submit();
			v2.submit();
			v9.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v3.submit();
			v7.submit();
			v12.submit();
			v10.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v9.submit();
			v10.submit();
			v12.submit();
			v11.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v6.submit();
			v11.submit();
			v12.submit();
			v7.submit();

			// far

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v4.submit();
			v3.submit();
			v2.submit();
			v1.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v1.submit();
			v2.submit();
			v6.submit();
			v5.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v8.submit();
			v7.submit();
			v3.submit();
			v4.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v3.submit();
			v7.submit();
			v6.submit();
			v2.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v8.submit();
			v4.submit();
			v1.submit();
			v5.submit();

			// near

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// submitNextColour();

			v5.submit();
			v6.submit();
			v7.submit();
			v8.submit();

			// near

		}
		GL11.glEnd();

	}

	private void drawWindmillBody() {
		// TODO Auto-generated method stub
		{
			Vertex v1 = new Vertex(-0.8f, -2.0f, 0.5f);
			Vertex v2 = new Vertex(-0.5f, 1.0f, 0.5f);
			Vertex v3 = new Vertex(0.5f, 1.0f, 0.5f);
			Vertex v4 = new Vertex(0.8f, -2.0f, 0.5f);
			Vertex v5 = new Vertex(-0.8f, -2.0f, -0.5f);
			Vertex v6 = new Vertex(-0.5f, 1.0f, -0.5f);
			Vertex v7 = new Vertex(0.5f, 1.0f, -0.5f);
			Vertex v8 = new Vertex(0.8f, -2.0f, -0.5f);

			GL11.glBegin(GL11.GL_POLYGON);
			{

				// submitNextColour();

				v4.submit();
				v3.submit();
				v2.submit();
				v1.submit();

				

			}
			GL11.glEnd();

			GL11.glBegin(GL11.GL_POLYGON);
			{

				// submitNextColour();

				v1.submit();
				v2.submit();
				v6.submit();
				v5.submit();

				

			}
			GL11.glEnd();

			GL11.glBegin(GL11.GL_POLYGON);
			{

				// submitNextColour();

				v8.submit();
				v7.submit();
				v3.submit();
				v4.submit();

				

			}
			GL11.glEnd();

			GL11.glBegin(GL11.GL_POLYGON);
			{

				// submitNextColour();

				v3.submit();
				v7.submit();
				v6.submit();
				v2.submit();

			
			}
			GL11.glEnd();

			GL11.glBegin(GL11.GL_POLYGON);
			{

				// submitNextColour();

				v8.submit();
				v4.submit();
				v1.submit();
				v5.submit();

				

			}
			GL11.glEnd();

			GL11.glBegin(GL11.GL_POLYGON);
			{

				// submitNextColour();

				v5.submit();
				v6.submit();
				v7.submit();
				v8.submit();

				

			}
			GL11.glEnd();
		}

	}

	private void drawTractorWindows() {
		// TODO Auto-generated method stub
		// tractorsuPPERbODY
		// tractorsuPPERbODY
		Vertex v1 = new Vertex(-0.8f, -1.0f, 0.5f);
		Vertex v2 = new Vertex(-0.5f, 1.0f, 0.5f);
		Vertex v3 = new Vertex(0.5f, 1.0f, 0.5f);
		Vertex v4 = new Vertex(0.8f, -1.0f, 0.5f);
		Vertex v5 = new Vertex(-0.8f, -1.0f, -0.5f);
		Vertex v6 = new Vertex(-0.5f, 1.0f, -0.5f);
		Vertex v7 = new Vertex(0.5f, 1.0f, -0.5f);
		Vertex v8 = new Vertex(0.8f, -1.0f, -0.5f);

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// near

			v4.submit();
			v3.submit();
			v2.submit();
			v1.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

		    //left
			
			v1.submit();
			v2.submit();
			v6.submit();
			v5.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// right

			v8.submit();
			v7.submit();
			v3.submit();
			v4.submit();

		

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// Top

			v3.submit();
			v7.submit();
			v6.submit();
			v2.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			//bottom

			v8.submit();
			v4.submit();
			v1.submit();
			v5.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// far

			v5.submit();
			v6.submit();
			v7.submit();
			v8.submit();

		

		}
		GL11.glEnd();
	}

	private void drawTractorBody() {
		// TODO Auto-generated method stub
		Vertex v1 = new Vertex(-1.6f, -0.5f, 0.5f);
		Vertex v2 = new Vertex(-1.6f, 0.5f, 0.5f);
		Vertex v3 = new Vertex(1.5f, 0.5f, 0.5f);
		Vertex v4 = new Vertex(1.5f, -0.5f, 0.5f);
		Vertex v5 = new Vertex(-1.6f, -0.5f, -0.5f);
		Vertex v6 = new Vertex(-1.6f, 0.5f, -0.5f);
		Vertex v7 = new Vertex(1.5f, 0.5f, -0.5f);
		Vertex v8 = new Vertex(1.5f, -0.5f, -0.5f);

		GL11.glBegin(GL11.GL_POLYGON);
		{

		
			// near
			v4.submit();
			v3.submit();
			v2.submit();
			v1.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// left

			v1.submit();
			v2.submit();
			v6.submit();
			v5.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// right

			v8.submit();
			v7.submit();
			v3.submit();
			v4.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			//top

			v3.submit();
			v7.submit();
			v6.submit();
			v2.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// bottom

			v8.submit();
			v4.submit();
			v1.submit();
			v5.submit();

		
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			// far

			v5.submit();
			v6.submit();
			v7.submit();
			v8.submit();

		}
		GL11.glEnd();
	}

	protected void checkSceneInput() {
		// TODO: Check for keyboard and mouse input here
/* if the 'r' or 'l' key's are clicked on the keyboard, it will trigger the following code,
 * which will therefore enable the vehicle to move left and right. Once it is is true or false it will
 * go to updateScene() method, which would then trigger the mocement of the tractor in the direction clicked,
 * L means left and r means right.
*/
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
//			isStillWheel1 = true;
//			isStillWheel2 = true;
//			isStillWheel3 = true;
//			isStillWheel4 = true;
			isStill = true;
			isStillWindow = true;
			
		} else if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
//			isStillWheel1 = false;
//			isStillWheel2 = false;
//			isStillWheel3 = false;
//			isStillWheel4 = false;
			isStill = false;
			isStillWindow = false;
		}

	}

	protected void renderScene() {// TODO: Render your scene here - remember that a scene graph will help you
									// write this method!
									// It will probably call a number of other methods you will write.
									// draw the ground plane

		// draw the ground plane
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, grassTextures.getTextureID());

			// position, scale and draw the ground plane using its display list
			GL11.glTranslatef(0.0f, -1.0f, -10.0f);
			GL11.glScalef(25.0f, 1.0f, 20.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the back plane
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, nightTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(0.0f, 4.0f, -20.0f);
			GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the moon
		GL11.glPushMatrix();
		{
			// how shiny are the front faces of the moon (specular exponent)
			float moonFrontShininess = 2.0f;
			// specular reflection of the front faces of the moon
			float moonFrontSpecular[] = { 0.6f, 0.6f, 0.6f, 1.0f };
			// diffuse reflection of the front faces of the moon
			float moonFrontDiffuse[] = { 0.6f, 0.6f, 0.6f, 1.0f };

			// set the material properties for the moon using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, moonFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(moonFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(moonFrontDiffuse));

			// position and draw the moon using a sphere quadric object
			GL11.glTranslatef(8.0f, 7, -21.1f);
			new Sphere().draw(1.4f, 100, 10);
		}
		GL11.glPopMatrix();

		/* i have drawn 5 trees, the first tree below is the far right tree,
		 * the next tree is the one to the left of it, i have drawn them from
		 * the right handside to left.
		*/
		GL11.glPushMatrix();
		{
			// how shiny are the front faces of the trunk (specular exponent)
			float trunkFrontShininess = 20.0f;
			// specular reflection of the front faces of the trunk
			float trunkFrontSpecular[] = { 0.2f, 0.2f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the trunk
			float trunkFrontDiffuse[] = { 0.38f, 0.29f, 0.07f, 1.0f };

			// set the material properties for the trunk using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, trunkFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(trunkFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(trunkFrontDiffuse));

			// position the tree
			GL11.glTranslatef(1.0f, -1.0f, -18.0f);

			// draw the trunk using a cylinder quadric object. Surround the draw call with a
			// push/pop matrix pair, as the cylinder will originally be aligned with the Z
			// axis
			// and will have to be rotated to align it along the Y axis
			GL11.glPushMatrix();
			{
				GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				new Cylinder().draw(0.25f, 0.25f, 1.5f, 10, 10);
			}
			GL11.glPopMatrix();

			// how shiny are the front faces of the triangular head of the tree (specular
			// exponent)
			float headFrontShininess = 20.0f;
			// specular reflection of the front faces of the head
			float headFrontSpecular[] = { 0.1f, 0.2f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the head
			float headFrontDiffuse[] = { 0.0f, 0.5f, 0.0f, 1.0f };

			// set the material properties for the head using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, headFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(headFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(headFrontDiffuse));

			// position and draw the triangular head using a sphere quadric object
			GL11.glTranslatef(0.0f, 2.0f, 0.0f);
			new Sphere().draw(1.3f, 3, 10);
		}
		GL11.glPopMatrix();
		


		// draw the second tree
		GL11.glPushMatrix();
		{
			// how shiny are the front faces of the trunk (specular exponent)
			float trunkFrontShininess = 20.0f;
			// specular reflection of the front faces of the trunk
			float trunkFrontSpecular[] = { 0.2f, 0.2f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the trunk
			float trunkFrontDiffuse[] = { 0.38f, 0.29f, 0.07f, 1.0f };

			// set the material properties for the trunk using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, trunkFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(trunkFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(trunkFrontDiffuse));

			// position the tree
			GL11.glTranslatef(-1.0f, -1.0f, -18.0f);

			// draw the trunk using a cylinder quadric object. Surround the draw call with a
			// push/pop matrix pair, as the cylinder will originally be aligned with the Z
			// axis
			// and will have to be rotated to align it along the Y axis
			GL11.glPushMatrix();
			{
				GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				new Cylinder().draw(0.25f, 0.25f, 1.5f, 10, 10);
			}
			GL11.glPopMatrix();

			// how shiny are the front faces of the triangular head of the tree (specular
			// exponent)
			float headFrontShininess = 20.0f;
			// specular reflection of the front faces of the head
			float headFrontSpecular[] = { 0.1f, 0.2f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the head
			float headFrontDiffuse[] = { 0.0f, 0.5f, 0.0f, 1.0f };

			// set the material properties for the head using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, headFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(headFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(headFrontDiffuse));

			// position and draw the triangular head using a sphere quadric object
			GL11.glTranslatef(0.0f, 2.0f, 0.0f);
			new Sphere().draw(1.3f, 3, 10);
		}
		GL11.glPopMatrix();
		
		
		

		// draw the third tree
		GL11.glPushMatrix();
		{
			// how shiny are the front faces of the trunk (specular exponent)
			float trunkFrontShininess = 20.0f;
			// specular reflection of the front faces of the trunk
			float trunkFrontSpecular[] = { 0.2f, 0.2f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the trunk
			float trunkFrontDiffuse[] = { 0.38f, 0.29f, 0.07f, 1.0f };

			// set the material properties for the trunk using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, trunkFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(trunkFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(trunkFrontDiffuse));

			// position the tree
			GL11.glTranslatef(-3.0f, -1.0f, -18.0f);

			// draw the trunk using a cylinder quadric object. Surround the draw call with a
			// push/pop matrix pair, as the cylinder will originally be aligned with the Z
			// axis
			// and will have to be rotated to align it along the Y axis
			GL11.glPushMatrix();
			{
				GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				new Cylinder().draw(0.25f, 0.25f, 1.5f, 10, 10);
			}
			GL11.glPopMatrix();

			// how shiny are the front faces of the triangular head of the tree (specular
			// exponent)
			float headFrontShininess = 20.0f;
			// specular reflection of the front faces of the head
			float headFrontSpecular[] = { 0.1f, 0.2f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the head
			float headFrontDiffuse[] = { 0.0f, 0.5f, 0.0f, 1.0f };

			// set the material properties for the head using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, headFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(headFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(headFrontDiffuse));

			// position and draw the triangular head using a sphere quadric object
			GL11.glTranslatef(0.0f, 2.0f, 0.0f);
			new Sphere().draw(1.3f, 3, 10);
		}
		GL11.glPopMatrix();

		// draw the fourth tree
		GL11.glPushMatrix();
		{
			// how shiny are the front faces of the trunk (specular exponent)
			float trunkFrontShininess = 20.0f;
			// specular reflection of the front faces of the trunk
			float trunkFrontSpecular[] = { 0.2f, 0.2f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the trunk
			float trunkFrontDiffuse[] = { 0.38f, 0.29f, 0.07f, 1.0f };

			// set the material properties for the trunk using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, trunkFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(trunkFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(trunkFrontDiffuse));

			// position the tree
			GL11.glTranslatef(-5.0f, -1.0f, -18.0f);

			// draw the trunk using a cylinder quadric object. Surround the draw call with a
			// push/pop matrix pair, as the cylinder will originally be aligned with the Z
			// axis
			// and will have to be rotated to align it along the Y axis
			GL11.glPushMatrix();
			{
				GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				new Cylinder().draw(0.25f, 0.25f, 1.5f, 10, 10);
			}
			GL11.glPopMatrix();

			// how shiny are the front faces of the triangular head of the tree (specular
			// exponent)
			float headFrontShininess = 2.0f;
			// specular reflection of the front faces of the head
			float headFrontSpecular[] = { 0.1f, 0.2f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the head
			float headFrontDiffuse[] = { 0.0f, 0.5f, 0.0f, 1.0f };

			// set the material properties for the head using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, headFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(headFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(headFrontDiffuse));

			// position and draw the triangular head using a sphere quadric object
			GL11.glTranslatef(0.0f, 2.0f, 0.0f);
			new Sphere().draw(1.3f, 3, 10);
		}
		GL11.glPopMatrix();

		// draw the fifth tree
		GL11.glPushMatrix();
		{
			// how shiny are the front faces of the trunk (specular exponent)
			float trunkFrontShininess = 2.0f;
			// specular reflection of the front faces of the trunk
			float trunkFrontSpecular[] = { 0.2f, 0.2f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the trunk
			float trunkFrontDiffuse[] = { 0.38f, 0.29f, 0.07f, 1.0f };

			// set the material properties for the trunk using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, trunkFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(trunkFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(trunkFrontDiffuse));

			// position the tree
			GL11.glTranslatef(-7.f, -1.0f, -18.0f);

			// draw the trunk using a cylinder quadric object. Surround the draw call with a
			// push/pop matrix pair, as the cylinder will originally be aligned with the Z
			// axis
			// and will have to be rotated to align it along the Y axis
			GL11.glPushMatrix();
			{
				GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				new Cylinder().draw(0.25f, 0.25f, 1.5f, 10, 10);
			}
			GL11.glPopMatrix();

			// how shiny are the front faces of the triangular head of the tree (specular
			// exponent)
			float headFrontShininess = 2.0f;
			// specular reflection of the front faces of the head
			float headFrontSpecular[] = { 0.1f, 0.2f, 0.1f, 1.0f };
			// diffuse reflection of the front faces of the head
			float headFrontDiffuse[] = { 0.0f, 0.5f, 0.0f, 1.0f };

			// set the material properties for the head using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, headFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(headFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(headFrontDiffuse));

			// position and draw the triangular head using a sphere quadric object
			GL11.glTranslatef(0.0f, 2.0f, 0.0f);
			new Sphere().draw(1.3f, 3, 10);
		}
		GL11.glPopMatrix();

		// draw the tractor: tractors roof, window, body and 4 wheels. 
		//drawing the tractors roof
		GL11.glPushMatrix();
		{
			// position and scale the tractors roof
			// the x value is the tractors window, however its the same for the roof aswell. 
            GL11.glTranslatef( currentTractorWindowX, 0.32f, -1.8f);
			GL11.glScalef(0.12f, 0.13f, 0.1f);
			
		    // how shiny are the front faces of the tractors roof (specular exponent)
			float tractorRoofFrontShininess = 2.0f;
			// specular reflection of the front faces of the tractors roof
			float tractorRoofFrontSpecular[] = { 5.0f, 0.0f, 0.0f, 1.0f };
			// diffuse reflection of the front faces of the tractors roof
			float tractorRoofFrontDiffuse[] = { 5.0f, 0.0f, 0.0f, 1.0f };

			// set the material properties for the tractors roof using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, tractorRoofFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(tractorRoofFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(tractorRoofFrontDiffuse));

			// draw the base of the tractors roof using its display list
			GL11.glCallList(tractorRoofList);


		//drawing the tractors upper body / window.
	
			// position and scale the tractor window
			GL11.glTranslatef(currentTractorWindowX, -1.0f, -1.8f);
			GL11.glScalef(1.0f, 1.0f, 1.0f);
			

			// how shiny are the front faces of the tractors window (specular exponent)
			float  tractorWindowFrontShininess = 50.0f;
			// specular reflection of the front faces of the tractors window
			float  tractorWindowFrontSpecular[] = { 11.0f, 1.0f, 1.0f, 1.0f };
			// diffuse reflection of the front faces of the tractors window
			float  tractorWindowFrontDiffuse[] = { 6.0f, 5.5f, 7.9f, 70.0f };

			// set the material properties for the tractor Window using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS,  tractorWindowFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap( tractorWindowFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap( tractorWindowFrontDiffuse));

			// draw the base of the tractors window using its display list
			GL11.glCallList(tractorWindowsList);


		    // drawing the tractors body
	
			// position and scale the tractors body
			GL11.glTranslatef( currentTractorX, -1.4f, -1.8f);
			GL11.glScalef(1.0f, 1.0f, 1.0f);
			

			// how shiny are the front faces of the tractors body (specular exponent)
			float  tractorBodyFrontShininess = 10.0f;
			// specular reflection of the front faces of the tractors body
			float  tractorBodyFrontSpecular[] = { 0.6f, 0.6f, 0.6f, 1.0f };
			// diffuse reflection of the front faces of the tractors body
			float  tractorBodyFrontDiffuse[] = { 0.1f, 0.1f, 0.1f, 1.0f };

			// set the material properties for the tractors body using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, tractorBodyFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap( tractorBodyFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap( tractorBodyFrontDiffuse));

	
			// draw the base of the tractors body using its display list
			GL11.glCallList(tractorBodyList);

			// back wheel1 
			// how shiny are the front faces of the wheel (specular exponent)
						float wheel1FrontShininess = 2.0f;
						// specular reflection of the front faces of the wheel
						float wheel1FrontSpecular[] = { 0.0f, 0.0f, 0.0f, 1.0f };
						// diffuse reflection of the front faces of the wheel
						float wheel1FrontDiffuse[] = { 0.0f, 0.0f, 0.5f, 1.0f };

						// set the material properties for the wheel using OpenGL
						GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, wheel1FrontShininess);
						GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(wheel1FrontSpecular));
						GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(wheel1FrontDiffuse));

						// position and draw the wheel using a sphere quadric object
						GL11.glTranslatef(1.0f, -0.3f, 0.2f);
						new Sphere().draw(0.83f, 100, 10);
						
						// far back wheel2 
						// how shiny are the front faces of the wheel (specular exponent)
									float wheel2FrontShininess = 2.0f;
									// specular reflection of the front faces of the wheel
									float wheel2FrontSpecular[] = { 0.0f, 0.9f, 0.0f, 1.0f };
									// diffuse reflection of the front faces of the wheel
									float wheel2FrontDiffuse[] = { 0.0f, 0.9f, 0.5f, 1.0f };

									// set the material properties for the wheel using OpenGL
									GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, wheel2FrontShininess);
									GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(wheel2FrontSpecular));
									GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(wheel2FrontDiffuse));

									// position and draw the wheel using a sphere quadric object
									GL11.glTranslatef(-0.2f, -0.0f, -0.5f);
									new Sphere().draw(0.83f, 100, 10);
									
								
		// front wheel3 
		// how shiny are the front faces of the wheel (specular exponent)
		float wheel3FrontShininess = 2.0f;
		// specular reflection of the front faces of the wheel
		float wheel3FrontSpecular[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		// diffuse reflection of the front faces of the wheel
		float wheel3FrontDiffuse[] = { 0.0f, 0.0f, 0.5f, 1.0f };

		// set the material properties for the wheel using OpenGL
		GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, wheel3FrontShininess);
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(wheel3FrontSpecular));
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(wheel3FrontDiffuse));

		// position and draw the wheel using a sphere quadric object
		GL11.glTranslatef(-1.8f, -0.3f, -0.6f);
		new Sphere().draw(0.6f, 100, 10);							
		
		
		// front far wheel4
				// how shiny are the front faces of the wheel (specular exponent)
				float wheel4FrontShininess = 2.0f;
				// specular reflection of the front faces of the wheel
				float wheel4FrontSpecular[] = { 0.0f, 0.0f, 0.0f, 1.0f };
				// diffuse reflection of the front faces of the wheel
				float wheel4FrontDiffuse[] = { 0.0f, 0.0f, 0.5f, 1.0f };

				// set the material properties for the wheel using OpenGL
				GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, wheel4FrontShininess);
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(wheel4FrontSpecular));
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(wheel4FrontDiffuse));

				// position and draw the wheel using a sphere quadric object
				GL11.glTranslatef(-0.0f, -0.0f, 1.3f);
				new Sphere().draw(0.6f, 100, 10);	
		
		}
		GL11.glPopMatrix();

		
		
		
		//drawing the windmill's body and blades
		GL11.glPushMatrix();
		{
			// position and scale the windmill's body
			GL11.glTranslatef(-0.7f, 0.0f, -2.0f);
			GL11.glScalef(0.15f, 0.15f, 0.15f);
	
			// how shiny are the front faces of the windmill's body (specular exponent)
			float windmillBodyFrontShininess = 10.0f;
			// specular reflection of the front faces of the windmill's body
			float windmillBodyFrontSpecular[] = { 1.0f, 7.0f, 2.0f, 1.0f };
			// diffuse reflection of the front faces of the windmill's body
			float windmillBodyFrontDiffuse[] = { 2.0f, 0.0f, 0.0f, 1.0f };
			
		

			// set the material properties for the windmill's body using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS,windmillBodyFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(windmillBodyFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(windmillBodyFrontDiffuse));

			// draw the base of the windmill's body using its display list
			GL11.glCallList(windmillBodyList);

	

		// drawing the windmill's blades in relation to the windmills body
		

			// position and scale the windmill's blades
			GL11.glTranslatef(0.12f, 0.6f, -0.20f);
			GL11.glScalef(1.7f, 1.7f, 1.5f);
			
			//  used bladesRotationAngle is set to a number at the top of the code, here we are just calling it.
			GL11.glRotatef(bladesRotationAngle, 0.0f, 0.0f, 1.0f);
			// how shiny are the front faces of the windmill's blades (specular exponent)
			float windmillBladesFrontShininess = 10.0f;
			// specular reflection of the front faces of the windmill's blades
			float windmillBladesFrontSpecular[] = { 1.0f, 7.0f, 2.0f, 1.0f };
			// diffuse reflection of the front faces of the windmill's blades
			float windmillBladesFrontDiffuse[] =  { 0.0f, 0.0f, 0.0f, 1.0f };

			// set the material properties for the windmill's blades using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, windmillBladesFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(windmillBladesFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(windmillBladesFrontDiffuse));

			// draw the base of the Windmill's Blades using its display list
			GL11.glCallList(windmillBladesList);

		}
		GL11.glPopMatrix();

		//drawing the aeroplane's body, wings 
		GL11.glPushMatrix();
		{

			// position and scale the aeroplane's body
			// currentAirPlaneX is defined at the top of the code.
			GL11.glTranslatef(currentAeroplaneX, 0.65f, -2.5f);
			GL11.glScalef(0.15f, 0.15f, 0.15f);
		
			// how shiny are the front faces of the aeroplane's body (specular exponent)
			float aeroplanesBodyFrontShininess = 10.0f;
			// specular reflection of the front faces of the aeroplane's body
			float aeroplanesBodyFrontSpecular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
			// diffuse reflection of the front faces of the aeroplane's body
			float aeroplanesBodyFrontDiffuse[] =  { 9.0f, 0.5f, 1.0f, 1.0f };

			// set the material properties for the aeroplane's body using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, aeroplanesBodyFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(aeroplanesBodyFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(aeroplanesBodyFrontDiffuse));

			// draw the base of the aeroplane's Body using its display list
			GL11.glCallList(aeroplaneList);

		

		    //drawing the aeroplane's wings
            // position and scale the aeroplane's wings
			GL11.glTranslatef(currentAeroplaneWingsX, -0.2f, -2.0f);
			GL11.glScalef(0.7f, 0.7f, -1.5f);
			
			// how shiny are the front faces of the aeroplane's wings (specular exponent)
			float aeroplaneWingsFrontShininess = 10.0f;
			// specular reflection of the front faces of the aeroplane's wings
			float aeroplaneWingsFrontSpecular[] = { 1.0f, 0.0f, 0.0f, 1.0f };
			// diffuse reflection of the front faces of the aeroplane's wings
			float aeroplaneWingsFrontDiffuse[] =  { 0.5f, 0.0f, 0.0f, 1.0f };
			// set the material properties for the aeroplane's wings using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, aeroplaneWingsFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(aeroplaneWingsFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(aeroplaneWingsFrontDiffuse));

			// draw the base of the aeroplane's wings using its display list
			GL11.glCallList(aeroplaneWingList);
		
			
		}
		GL11.glPopMatrix();
		
	}

	

	private void drawTractorRoof() {

		//tractorsRoof
		Vertex v1 = new Vertex(-0.5f, -0.25f, 0.5f);
		Vertex v2 = new Vertex(-0.8f, 0.25f, 0.5f);
		Vertex v3 = new Vertex(0.8f, 0.25f, 0.5f);
		Vertex v4 = new Vertex(0.5f, -0.25f, 0.5f);
		Vertex v5 = new Vertex(-0.5f, -0.25f, -0.5f);
		Vertex v6 = new Vertex(-0.8f, 0.25f, -0.5f);
		Vertex v7 = new Vertex(0.8f, 0.25f, -0.5f);
		Vertex v8 = new Vertex(0.5f, -0.25f, -0.5f);

		GL11.glBegin(GL11.GL_POLYGON);
		{

		
			// near
			
			v4.submit();
			v3.submit();
			v2.submit();
			v1.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

		   //left

			v1.submit();
			v2.submit();
			v6.submit();
			v5.submit();

			
		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			//right

			v8.submit();
			v7.submit();
			v3.submit();
			v4.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

		     //top 

			v3.submit();
			v7.submit();
			v6.submit();
			v2.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{
            //bottom    
       
			v8.submit();
			v4.submit();
			v1.submit();
			v5.submit();

			

		}
		GL11.glEnd();

		GL11.glBegin(GL11.GL_POLYGON);
		{

			//far

			v5.submit();
			v6.submit();
			v7.submit();
			v8.submit();

			

		}
		GL11.glEnd();
	}

	protected void setSceneCamera() {
		// call the default behaviour defined in GraphicsLab. This will set a default
		// perspective projection
		// and default camera settings ready for some custom camera positioning below...
		super.setSceneCamera();

		// TODO: If it is appropriate for your scene, modify the camera's position and
		// orientation here
		// using a call to GL11.gluLookAt(...)

	}

	protected void cleanupScene() {// TODO: Clean up your resources here
	}

	private void drawUnitPlane() {
		Vertex v1 = new Vertex(-0.5f, 0.0f, -0.5f); // left, back
		Vertex v2 = new Vertex(0.5f, 0.0f, -0.5f); // right, back
		Vertex v3 = new Vertex(0.5f, 0.0f, 0.5f); // right, front
		Vertex v4 = new Vertex(-0.5f, 0.0f, 0.5f); // left, front

		// draw the plane geometry. order the vertices so that the plane faces up
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v2.toVector(), v1.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v4.submit();

			GL11.glTexCoord2f(1.0f, 0.0f);
			v3.submit();

			GL11.glTexCoord2f(1.0f, 1.0f);
			v2.submit();

			GL11.glTexCoord2f(0.0f, 1.0f);
			v1.submit();
		}
		GL11.glEnd();

		// if the user is viewing an axis, then also draw this plane
		// using lines so that axis aligned planes can still be seen
		if (isViewingAxis()) {
			// also disable textures when drawing as lines
			// so that the lines can be seen more clearly
			GL11.glPushAttrib(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_LINE_LOOP);
			{
				v4.submit();
				v3.submit();
				v2.submit();
				v1.submit();
			}
			GL11.glEnd();
			GL11.glPopAttrib();
		}
	}



	@Override
	protected void updateScene() {
		// the bladesRotationAngle rotates the blade, to the speed 15.0f
		bladesRotationAngle += 20.0f * getAnimationScale();

		/*the currentAeroplaneX, currentAeroplaneWindowX, currentAeroplaneWingsX are 
		 * the planes and it parts current x positions, the plane will slowly
		 * appear from the right handside, to the left with the spped of 0.1f,
		 * the plane will not require any user input. 
		 * */
		currentAeroplaneX -= 0.1f * getAnimationScale();
		
		
		
		
		
		
		
		
		// TODO Auto-generated method stub
		
	/* if the tractor is still and the current position of the tractor is 
	 * in the limits i have set then the tractor will be able to move left and right, 
	 * between the highestX and the lowestX. I have set the highest and lowest X at
	 * top of the code. the speed the Tractor will move at is 0.2f. i have done the same with 
	 * all the parts of the tractor so they would move in the same direction with the same speed
	 * Within the boundary of the cars highestX and lowestX.
	 * 
		*/
		if (isStill & currentTractorX < highestTractorX) {
			currentTractorX += 0.2f * getAnimationScale();
		} 
		else if (!isStill & currentTractorX > lowestTractorX) {
			currentTractorX -= 0.2f * getAnimationScale();
		}
		
		if (isStillWindow & currentTractorX < highestTractorX) {
			currentTractorWindowX += 0.2f * getAnimationScale();
		} 
		else if (!isStillWindow & currentTractorX > lowestTractorX) {
			currentTractorWindowX -= 0.2f * getAnimationScale();
		}

		
	}

}

