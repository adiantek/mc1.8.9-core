import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.main.Main;

public class Start
{
    public static void main(String[] args) throws LWJGLException
    {
        GLFW.glfwInit();
        Main.main(concat(new String[] {"--width", "1920", "--height", "1080", "--version", "mcp", "--accessToken", "0", "--assetsDir", System.getenv("APPDATA") + "\\.minecraft\\assets", "--assetIndex", "1.8", "--userProperties", "{}"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second)
    {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
