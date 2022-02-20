package net.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.shader.ShaderLoader;
import net.minecraft.client.shader.ShaderLoader.ShaderType;
import net.minecraft.client.util.JsonException;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

public class Program {
    private static final Logger logger = LogManager.getLogger();

    // pos = 0
    // tex = 1
    // color = 2
    public final int ModelViewMat;
    public final int ProjMat;
    public final int AlphaDiscard;
    public final int Sampler0;
    public final int ColorModulator;

    public final int program;

    public final int vao;
    public final int vbo;

    public Program(String name, VertexFormat format) {
        program = OpenGlHelper.glCreateProgram();
        try {
            int vertex = createShader(ShaderType.VERTEX, name);
            OpenGlHelper.glAttachShader(program, vertex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Minecraft.checkGLError("");
        try {
            int fragment = createShader(ShaderType.FRAGMENT, name);
            OpenGlHelper.glAttachShader(program, fragment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Minecraft.checkGLError("");
        OpenGlHelper.glLinkProgram(program);
        int i = OpenGlHelper.glGetProgrami(program, OpenGlHelper.GL_LINK_STATUS);

        if (i == 0) {
            logger.warn("Error encountered when linking program containing " + name + ". Log output:");
            logger.warn(OpenGlHelper.glGetProgramInfoLog(program, 32768));
        }
        Minecraft.checkGLError("");

        OpenGlHelper.glUseProgram(program);

        ModelViewMat = OpenGlHelper.glGetUniformLocation(program, "ModelViewMat");
        ProjMat = OpenGlHelper.glGetUniformLocation(program, "ProjMat");
        AlphaDiscard = OpenGlHelper.glGetUniformLocation(program, "AlphaDiscard");
        Sampler0 = OpenGlHelper.glGetUniformLocation(program, "Sampler0");
        ColorModulator = OpenGlHelper.glGetUniformLocation(program, "ColorModulator");

        Minecraft.checkGLError("");
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vbo = OpenGlHelper.glGenBuffers();
        OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, vbo);
        // OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, format.getNextOffset(), GL30.GL_STATIC_DRAW);
        this.loadAttrib(format);
        OpenGlHelper.glUseProgram(0);
    }

    public void loadAttrib(VertexFormat format) {
        Minecraft.checkGLError("");
        if (format.hasPosition()) {
            GL20.glEnableVertexAttribArray(0);
            Minecraft.checkGLError("");
            GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, format.getNextOffset(), format.getPositionOffset());
        }
        Minecraft.checkGLError("");
        if (format.hasUvOffset(0)) {
            GL20.glEnableVertexAttribArray(1);
            Minecraft.checkGLError("");
            GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, format.getNextOffset(), format.getUvOffsetById(0));
        }
        Minecraft.checkGLError("");
        if (format.hasColor()) {
            GL20.glEnableVertexAttribArray(2);
            Minecraft.checkGLError("");
            GL20.glVertexAttribPointer(2, 4, GL11.GL_UNSIGNED_BYTE, true, format.getNextOffset(), format.getColorOffset());
        }
        Minecraft.checkGLError("");
    }

    private static int createShader(ShaderLoader.ShaderType type, String filename) throws IOException {
        ResourceLocation res = new ResourceLocation(filename + type.getShaderExtension());
        BufferedInputStream bufferedinputstream = new BufferedInputStream(Config.getResourceStream(res));
        byte[] abyte = ShaderLoader.toByteArray(bufferedinputstream);
        ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
        bytebuffer.put(abyte);
        bytebuffer.position(0);
        int i = OpenGlHelper.glCreateShader(type.getShaderMode());
        OpenGlHelper.glShaderSource(i, bytebuffer);
        OpenGlHelper.glCompileShader(i);

        if (OpenGlHelper.glGetShaderi(i, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
            String s = StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(i, 32768));
            JsonException jsonexception = new JsonException(
                    "Couldn\'t compile " + type.getShaderName() + " program: " + s);
            jsonexception.func_151381_b(res.getResourcePath());
            throw jsonexception;
        }
        return i;
    }

    public static final Program POS = new Program("shaders/core/position", DefaultVertexFormats.POSITION);
    public static final Program POS_TEX_COLOR = new Program("shaders/core/position_tex_color", DefaultVertexFormats.POSITION_TEX_COLOR);
    public static final Program POS_TEX = new Program("shaders/core/position_tex", DefaultVertexFormats.POSITION_TEX);
    public static final Program POS_COLOR = new Program("shaders/core/position_color", DefaultVertexFormats.POSITION_COLOR);
    public static final Program POS_COLOR_NO_TEX = new Program("shaders/core/position_color", DefaultVertexFormats.POSITION_TEX_COLOR);
    public static final Program POS_SKY_VBO = new Program("shaders/core/position", DefaultVertexFormats.POSITION);
    public static final Program POS_SKY2_VBO = new Program("shaders/core/position", DefaultVertexFormats.POSITION);
    public static final Program POS_STARS_VBO = new Program("shaders/core/position", DefaultVertexFormats.POSITION);

    // TODO parse normal in lighting?
    public static final Program POS_TEX_NORMAL = new Program("shaders/core/position_tex", DefaultVertexFormats.POSITION_TEX_NORMAL);
    public static final Program POS_TEX_COLOR_NORMAL = new Program("shaders/core/position_tex_color", DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
    public static final Program ITEM = new Program("shaders/core/position_tex_color", DefaultVertexFormats.ITEM);
    public static final Program BLOCK = new Program("shaders/core/position_tex_color", DefaultVertexFormats.BLOCK);
    //POSITION_TEX_NORMAL
}
