package graphics;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import java.awt.Color;
import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Window in which the game will be played
 * Using lwjgl as a wrapper around opengl
 * The window contains all game elements render using opengl
 * @author Christopher VALLOT
 */
public class Window {
    private static long window;
    private static HashMap<Character, Integer> charWidth;

    /**
     * Initializes the window
     * @param title the window title (not visible in fullscreen)
     */
    public static void init(String title) {
        try {
            if (!glfwInit()) {
                System.exit(1);
            }

            window = glfwCreateWindow(getWidth(), getHeight(), title, glfwGetPrimaryMonitor(), 0);
            glfwShowWindow(window);
            glfwMakeContextCurrent(window);
            glfwSwapInterval(1);
            GL.createCapabilities();
            glEnable(GL_TEXTURE_2D);

        } catch (IllegalStateException e) {
            System.out.println("Failed to create window");
        }
    }

    /**
     * Draws a texture that will contain a sprite
     * @param texture the texture
     * @param posX the position x
     * @param posY the position y
     * @param rotate the degree of rotation
     * @param scale the scale parameter
     */
    public static void drawTexture(Texture texture, int posX, int posY, int width, int height, float rotate, float scale, int textureX, int textureY, int textureWidth, int textureHeight, int r, int g, int b) {
        glEnable(GL_TEXTURE_2D);
        glPushMatrix();

        glTranslatef(posX, posY, 0);
        glRotatef(rotate, 0, 0f, 1f);
        glScalef(scale, scale, 1);
        glColor3f(((float) r) / 255.0f, ((float) g) / 255.0f, ((float) b) / 255.0f);

        glBindTexture(GL_TEXTURE_2D, texture.getId());

        glBegin(GL_QUADS);
        glTexCoord2f((float) textureX / texture.getWidth(), (float) textureY / texture.getHeight());
        glVertex2f(-width, -height);
        glTexCoord2f((float) (textureX + textureWidth) / texture.getWidth(), (float) textureY / texture.getHeight());
        glVertex2f(width, -height);
        glTexCoord2f((float) (textureX + textureWidth) / texture.getWidth(), (float) (textureY + textureHeight) / texture.getHeight());
        glVertex2f(width, height);
        glTexCoord2f( (float) textureX / texture.getWidth(), (float) (textureY + textureHeight) / texture.getHeight());
        glVertex2f(-width, height);
        glEnd();

        glPopMatrix();
        glDisable(GL_TEXTURE_2D);
    }

    public static Texture getTexture(String fileName){
        return Texture.getTexture(System.getProperty("user.dir") + "/ressources/sprites/" + fileName);
    }

    /**
     * Draws a sprite in the window
     * @param fileName the name of the file
     * @param posX the position on the x axis
     * @param posY the position on the y axis
     * @param rotate the degree of rotation
     * @param scale the scale parameter
     */
    public static void drawSprite(String fileName, int posX, int posY, int width, int height, float rotate, float scale, int textureX, int textureY, int textureWidth, int textureHeight) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path);

        drawTexture(texture, posX, posY, width, height, rotate, scale, textureX, textureY, textureWidth, textureHeight, 255, 255, 255);
    }

    public static void drawSprite(String fileName, int posX, int posY, int width, int height, float rotate) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path);

        drawTexture(texture, posX, posY, width, height, rotate, 1f, 0, 0, texture.getWidth(), texture.getHeight(), 255, 255, 255);
    }

    public static void drawSprite(String fileName, int posX, int posY, float rotate, float scale) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path);

        drawTexture(texture, posX, posY, texture.getWidth(), texture.getHeight(), rotate, scale, 0, 0, texture.getWidth(), texture.getHeight(), 255, 255, 255);
    }

    public static void drawSprite(String fileName, int posX, int posY, int width, int height, float scale, int textureX, int textureY, int textureWidth, int textureHeight) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path);

        drawTexture(texture, posX, posY, width, height,0, scale, textureX, textureY, textureWidth, textureHeight, 255, 255, 255);
    }

    /*public static void drawSprite(String fileName, int posX, int posY, float rotate, float scale, Color tint) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path, tint);

        drawTexture(texture, posX, posY, texture.getWidth(), texture.getHeight(), rotate, scale, 0, 0, texture.getWidth(), texture.getHeight(), 255, 255, 255);
    }*/

    /**
     * Draws a line in the window
     * @param x1 the first position x
     * @param y1 the first position y
     * @param x2 the second position x
     * @param y2 the second position y
     * @param thickness the thickness of the line
     * @param R the proportion of red (0 to 255)
     * @param G the proportion of green (0 to 255)
     * @param B the proportion of blue (0 to 255)
     */
    public static void drawLine(int x1, int y1, int x2, int y2, float thickness, int R, int G, int B){
        int minX, minY;

        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);

        x1 -= minX;
        x2 -= minX;
        y1 -= minY;
        y2 -= minY;

        glPushMatrix();

        glBindTexture(0, 0);
        glTranslatef(minX, minY, 0);
        glColor3f(((float) R) / 255.0f, ((float) G) / 255.0f, ((float) B) / 255.0f);
        glLineWidth(thickness);

        glBegin(GL_LINES);
        glVertex2f(x1, y1);
        glVertex2f(x2, y2);
        glEnd();

        glPopMatrix();
    }

    /**
     * Draws a rectangle in the window
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param posX the position x
     * @param posY the position y
     * @param R the proportion of red
     * @param G the proportion of green
     * @param B the proportion of blue
     * @param rotate the degree of rotation
     */
    public static void drawRectangle(int width, int height, int posX, int posY, int R, int G, int B, float rotate){
        glPushMatrix();

        glBindTexture(0, 0);
        glTranslatef(posX, posY, 0);
        glColor3f(((float) R) / 255.0f, ((float) G) / 255.0f, ((float) B) / 255.0f);

        glBegin(GL_QUADS);
        glVertex2f(-width, -height);
        glVertex2f(-width, height);
        glVertex2f(width, height);
        glVertex2f(width, -height);
        glEnd();

        glPopMatrix();
    }

    /**
     * Draws a circle in the window
     * @param posX the position x
     * @param posY the position y
     * @param radius the radius parameter
     * @param R the proportion of red
     * @param G the proportion of green
     * @param B the proportion of blue
     */
    public static void drawCircle(int posX, int posY, int radius, int R, int G, int B) {
        double a = 1.0 / radius;

        glPushMatrix();

        glBindTexture(0, 0);
        glTranslatef(posX, posY, 0);
        glColor3f(((float) R) / 255.0f, ((float) G) / 255.0f, ((float) B) / 255.0f);

        glBegin(GL_POLYGON);

        for (double i = -Math.PI; i < Math.PI; i += a) {
            glVertex2f((float) (Math.cos(i) * radius), (float) (Math.sin(i) * radius));
        }

        glEnd();

        glPopMatrix();
    }

    private static void generateCharactersWidth() {
        charWidth = new HashMap<Character, Integer>();

        charWidth.put('!', 3);
        charWidth.put('"', 7);
        charWidth.put('#', 22);
    }

    public static void drawText(String text, int x, int y, int size, int r, int g, int b) {
        String path = System.getProperty("user.dir") + "/ressources/font/verdana.png";

        Texture texture = new Texture(path);

        char[] charArray = text.toCharArray();
        byte code;
        int lineOffset = 0, line = 0;

        for (int i = 0; i < charArray.length; i++) {
            code = (byte) charArray[i];

            if (code == 10) {
                lineOffset = 0;
                line++;
            }
            else if (code >= 32 && code < 128 || code >= 160) {
                drawTexture(texture, x + lineOffset * size, y + line * size, size, size, 0f, 1f, (code % 16) * 64, ((int) (code / 16)) * 64, 64, 64, r, g, b);
                lineOffset++;
            }
        }
    }

    /**
     * @return true if the window should close or false otherwise
     */
    public static boolean shouldClose() {
        return !glfwWindowShouldClose(window);
    }

    /**
     * swaps the front buffer of the window with the back buffer
     * it is necessary step to make render with OpenGL
     * use this method at the end of the main loop
     */
    public static void swapBuffers(){
        glfwSwapBuffers(window);
    }

    /**
     * @return the width of the window
     */
    public static int getWidth() {
        return videoMode().width();
    }


    /**
     * @return the height of the window
     */
    public static int getHeight() {
        return videoMode().height();
    }

    private static GLFWVidMode videoMode() {
        return glfwGetVideoMode(getMonitor());
    }

    private static long getMonitor() {
        return glfwGetMonitors().get(0);
    }


    /**
     * @return the window id
     */
    public static long getWindow() {
        return window;
    }

    /**
     * close the window
     */
    public static void exit(){
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}